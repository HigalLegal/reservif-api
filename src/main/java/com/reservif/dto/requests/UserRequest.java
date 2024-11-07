package com.reservif.dto.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.reservif.entities.enuns.TypeUser;
import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.validation.constraints.*;
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

    @Size(min = 2, message = "Deve ter ao menos 2 caracteres")
    private String firstName;

    @Size(min = 2, message = "Deve ter ao menos 2 caracteres")
    private String lastName;

    @Email(message = "Email inválido")
    private String email;

    @Size(min = 5, message = "Deve ter ao menos 2 caracteres")
    private String password;

    private String identificationCode;

    @NotNull(message = "Informe se o usuário é professor ou administrador")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private TypeUser typeUser;

}
