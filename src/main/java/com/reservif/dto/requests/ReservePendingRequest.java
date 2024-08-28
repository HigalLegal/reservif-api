package com.reservif.dto.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class ReservePendingRequest {

    private boolean approved;

}