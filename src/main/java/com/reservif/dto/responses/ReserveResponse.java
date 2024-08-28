package com.reservif.dto.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.reservif.dto.PeriodReserveDTO;
import com.reservif.entities.enuns.StatusReserve;
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
public class ReserveResponse {

    private Integer id;

    private String observation;

    private String reservable;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private StatusReserve status;

    private PeriodReserveDTO period;

}
