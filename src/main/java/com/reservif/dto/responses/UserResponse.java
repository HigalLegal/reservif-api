package com.reservif.dto.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.reservif.dto.ImageUserDTO;
import com.reservif.entities.enuns.TypeUser;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@RegisterForReflection
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

    private Integer id;

    private String firstName;

    private String lastName;

    private String email;

    private String identificationCode;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private TypeUser typeUser;

    private ImageUserDTO imageUser;
}
