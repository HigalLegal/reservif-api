package com.reservif.entities.enuns;

import com.fasterxml.jackson.annotation.JsonValue;

public enum StatusReserve {

    PENDING("Pendente"), APPROVED("Aprovado"), DISAPPROVED("Desaprovado");

    private final String statusReserve;

    StatusReserve(String statusReserve) {
        this.statusReserve = statusReserve;
    }

    @JsonValue
    public String getStatusReserve() {
        return statusReserve;
    }
}
