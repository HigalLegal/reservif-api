package com.reservif.dto.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.reservif.entities.enuns.TypeUser;
import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@RegisterForReflection
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest {

    @Min(value = 2, message = "Deve ter ao menos 2 caracteres")
    private String firstName;

    @Min(value = 2, message = "Deve ter ao menos 2 caracteres")
    private String lastName;

    @Email(message = "Email inválido")
    private String email;

    @Min(value = 5, message = "Senha deve ter ao menos 5 caracteres")
    private String password;

    @NotBlank(message = "Digite o código SUAP")
    private String identificationCode;

    @NotNull(message = "Informe se o usuário é professor ou administrador")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private TypeUser typeUser;

}
