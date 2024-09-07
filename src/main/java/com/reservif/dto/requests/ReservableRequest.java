package com.reservif.dto.requests;

import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@RegisterForReflection
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservableRequest {

    @NotBlank(message = "Insira o nome do local")
    private String name;

    @NotBlank(message = "Insira alguma referência")
    private String location;

}
