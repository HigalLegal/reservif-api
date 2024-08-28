package com.reservif.dto.requests;

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
public class UserRequest {

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String identificationCode;

    private Integer typeUserId;

}
