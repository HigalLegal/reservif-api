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

    public static StatusReserve fromString(String status) {
        for (StatusReserve s : StatusReserve.values()) {
            if (s.getStatusReserve().charAt(0) == status.charAt(0)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Status inválido: " + status);
    }

}
