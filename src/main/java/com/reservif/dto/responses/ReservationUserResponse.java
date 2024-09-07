package com.reservif.dto.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class ReservationUserResponse {

    private Integer id;

    private String fullName;

    private String email;

    private String identificationCode;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private TypeUser typeUser;

}
