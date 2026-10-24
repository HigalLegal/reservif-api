# Reservif API

API REST para gerenciar reservas de espaços de um campus, como salas, laboratórios e auditórios. O sistema permite cadastrar usuários e espaços, solicitar reservas em períodos recorrentes e consultar ou aprovar solicitações.

## Tecnologias

- Java 21
- Quarkus 3.13.1
- Gradle
- PostgreSQL 15
- Hibernate ORM com Panache
- JWT para autenticação e autorização
- ImgBB para hospedagem opcional de imagens de perfil

## Requisitos

- JDK 21
- Docker e Docker Compose, para executar o banco e a aplicação em contêineres

## Executar com Docker Compose

O `docker-compose.yml` inicia o PostgreSQL e a imagem da API `higal-legal/reservif:1.0.0-SNAPSHOT`. A imagem precisa estar disponível localmente ou no registry configurado. Para criá-la localmente:

```bash
./gradlew clean build
docker build
docker compose up -d
```

A API ficará disponível em `http://localhost:8080`. Para acompanhar os logs:

```bash
docker compose logs -f app
```

O Compose cria o banco `reservifDB` e carrega `init.sql` na primeira inicialização do volume. O Hibernate está configurado para atualizar o esquema. Para parar os serviços sem apagar os dados:

```bash
docker compose down
```

> **Atenção:** as credenciais de banco definidas no Compose são valores de desenvolvimento. Altere-as antes de qualquer implantação compartilhada ou produção e não versione credenciais reais.

## Executar em modo de desenvolvimento

Com um PostgreSQL acessível, execute a aplicação apontando a URL e as credenciais do banco por variáveis de ambiente:

```bash
QUARKUS_DATASOURCE_JDBC_URL=jdbc:postgresql://localhost:5432/reservifDB \
QUARKUS_DATASOURCE_USERNAME=<usuario> \
QUARKUS_DATASOURCE_PASSWORD=<senha> \
./gradlew quarkusDev
```

O modo de desenvolvimento do Quarkus inicia a aplicação em `http://localhost:8080` e habilita recarga durante alterações no código. Para compilar e executar os testes:

```bash
./gradlew test
./gradlew build
```

## Autenticação e permissões

O login retorna um JWT no campo `jwt`. Envie-o nas rotas protegidas usando o cabeçalho:

```http
Authorization: Bearer <token>
```

Os tokens são configurados para expirar após três horas. Os grupos usados pela API são `Professor` e `Administrador`; usuários administradores também recebem o grupo `Professor`.

| Permissão | Operações |
| --- | --- |
| Pública | Login e cadastro de usuário |
| Autenticada | Consultas de reservas e espaços; criação, edição e exclusão de reservas; edição de usuário |
| Administrador | Consultar e excluir usuários; criar, editar e excluir espaços; aprovar ou reprovar reservas |

> **Atenção:** o cadastro de usuário é público e o corpo recebido inclui o tipo de usuário. Restrinja ou revise esse fluxo antes de expor a API publicamente; não confie nesse cadastro aberto para provisionar contas administrativas em produção.

## Endpoints

Todos os endpoints retornam JSON, exceto o cadastro e a edição de usuários, que recebem `multipart/form-data`.

### Usuários — `/users`

| Método e rota | Acesso | Descrição |
| --- | --- | --- |
| `POST /users/login` | Público | Autentica por e-mail ou código SUAP e retorna o JWT |
| `POST /users` | Público | Cadastra usuário; aceita imagem opcional |
| `GET /users?page=0&pageSize=10` | Administrador | Lista usuários paginados |
| `GET /users/{id}` | Administrador | Consulta usuário por ID |
| `PUT /users/{id}` | Autenticado | Atualiza usuário; aceita imagem opcional |
| `DELETE /users/{id}` | Administrador | Exclui usuário |

O formulário multipart de cadastro/edição contém a parte `userRequest` em JSON e, opcionalmente, uma parte `image`. Se uma imagem for enviada, a aplicação a encaminha ao ImgBB.

Exemplo de `userRequest`:

```json
{
  "firstName": "Ana",
  "lastName": "Silva",
  "email": "ana@example.com",
  "password": "senha-com-no-minimo-5-caracteres",
  "identificationCode": "123456",
  "typeUser": "Professor"
}
```

Os valores aceitos para `typeUser` são `Professor` e `Administrador`. As senhas são armazenadas com hash BCrypt.

Exemplo de login:

```bash
curl -X POST http://localhost:8080/users/login \
  -H 'Content-Type: application/json' \
  -d '{"emailOrCode":"ana@example.com","password":"sua-senha"}'
```

### Espaços reserváveis — `/reservables`

| Método e rota | Acesso | Descrição |
| --- | --- | --- |
| `GET /reservables?page=0&pageSize=10` | Autenticado | Lista espaços paginados |
| `GET /reservables/by-name?name=Laboratório` | Autenticado | Pesquisa espaços por nome |
| `GET /reservables/{id}` | Autenticado | Consulta espaço por ID |
| `POST /reservables` | Administrador | Cadastra espaço |
| `PUT /reservables/{id}` | Administrador | Atualiza espaço |
| `DELETE /reservables/{id}` | Administrador | Exclui espaço |

Corpo para criar ou atualizar um espaço:

```json
{
  "name": "Laboratório de Informática",
  "location": "Bloco A, sala 12"
}
```

### Reservas — `/reserves`

| Método e rota | Acesso | Descrição |
| --- | --- | --- |
| `GET /reserves?page=0&pageSize=10` | Autenticado | Lista reservas paginadas |
| `GET /reserves/{id}` | Autenticado | Consulta reserva por ID |
| `GET /reserves/by-date-interval?beginning=01/03/2026&end=31/03/2026` | Autenticado | Filtra pelo intervalo de datas |
| `GET /reserves/by-horary-interval?beginning=08:00&end=12:00` | Autenticado | Filtra pelo intervalo de horários |
| `GET /reserves/by-reservable-name?reservableName=Laboratório` | Autenticado | Filtra pelo nome do espaço |
| `GET /reserves/by-status?statusReserve=Pendente` | Autenticado | Filtra pelo status |
| `GET /reserves/by-user?userID=1` | Autenticado | Filtra pelo ID do usuário |
| `GET /reserves/by-reservable?reservableID=1` | Autenticado | Filtra pelo ID do espaço |
| `POST /reserves` | Autenticado | Cria reserva com status inicial `Pendente` |
| `PUT /reserves/{id}` | Autenticado | Atualiza reserva |
| `PATCH /reserves/{id}` | Administrador | Aprova ou reprova reserva |
| `DELETE /reserves/{id}` | Autenticado | Exclui reserva |

Corpo para criar ou atualizar uma reserva:

```json
{
  "observation": "Aula prática da disciplina",
  "reservableId": 1,
  "userId": 1,
  "periodReserve": {
    "startDay": "01/03/2026",
    "endDay": "31/03/2026",
    "startHorary": "08:00",
    "endHorary": "10:00",
    "daysOfWeek": [1, 3, 5]
  }
}
```

As datas usam `dd/MM/yyyy` e os horários, `HH:mm`. `endDay` é opcional. `daysOfWeek` usa os números ISO: segunda-feira `1` até domingo `7`. O status é atribuído como `Pendente` na criação.

Para aprovar ou reprovar, envie `true` ou `false` no campo `approved`:

```bash
curl -X PATCH http://localhost:8080/reserves/1 \
  -H 'Authorization: Bearer <token>' \
  -H 'Content-Type: application/json' \
  -d '{"approved":true}'
```

Os status retornados são `Pendente`, `Aprovado` e `Desaprovado`.

## Configuração

As configurações principais ficam em `src/main/resources/application.properties` e podem ser sobrescritas por variáveis de ambiente do Quarkus.

| Configuração | Finalidade |
| --- | --- |
| `QUARKUS_DATASOURCE_JDBC_URL` | URL JDBC do PostgreSQL |
| `QUARKUS_DATASOURCE_USERNAME` | Usuário do banco |
| `QUARKUS_DATASOURCE_PASSWORD` | Senha do banco |
| `mp.jwt.verify.publickey.location` | Chave pública usada para validar JWT |
| `smallrye.jwt.sign.key.location` | Chave privada usada para assinar JWT |
| `quarkus.rest-client."com.reservif.clients.ApiImgBB".url` | URL do serviço ImgBB |

As chaves JWT estão no projeto para uso da aplicação. Em produção, use chaves próprias protegidas e faça a configuração por um mecanismo seguro de secrets. A integração com ImgBB também depende de uma chave de API; não publique chaves reais no repositório.

## Estrutura do código

O código-fonte principal está em `src/main/java/com/reservif`:

- `resources`: endpoints REST.
- `services`: regras e operações da aplicação.
- `repositories`: acesso a dados.
- `entities`: entidades persistidas.
- `dto`: contratos de entrada e saída da API.
- `configs/jwt`: geração de tokens JWT.
- `clients`: cliente REST para ImgBB.
- `handlers` e `exceptions`: tratamento de erros da API.

## Observações

- A paginação aceita os parâmetros `page` e `pageSize`; o padrão configurado é página `0` e tamanho `10`.
- O CORS está habilitado globalmente. Revise as origens permitidas antes de disponibilizar o serviço publicamente.
- O arquivo `init.sql` é executado pelo PostgreSQL somente ao inicializar um volume vazio. Alterações posteriores nele não são reaplicadas automaticamente em volumes existentes.
