package com.reservif.dto.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.reservif.dto.PeriodReserveDTO;
import com.reservif.entities.enuns.StatusReserve;
import io.quarkus.runtime.annotations.RegisterForReflection;
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
public class ReserveRequest {

    @NotBlank(message = "Insira alguma observação")
    private String observation;

    @NotNull(message = "Insira o local da reserva")
    private Integer reservableId;

    @NotNull(message = "Insira o usuário da reserva")
    private Integer userId;

    @NotNull(message = "Insira os dados de período da reserva")
    private PeriodReserveDTO periodReserve;
}
