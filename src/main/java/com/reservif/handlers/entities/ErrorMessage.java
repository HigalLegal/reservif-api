package com.reservif.handlers.entities;

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
public class ErrorMessage {

    private String title;

    private Integer statusCode;

    private String message;

}
