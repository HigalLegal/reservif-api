package com.reservif.dto.responses;

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

    private String urlPhoto;

    private String typeUser;
}
