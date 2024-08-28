package com.reservif.entities.enuns;

public enum StatusReserve {

    PENDING("Pendente"), APPROVED("Aprovado"), DISAPPROVED("Desaprovado");

    private final String statusReserve;

    StatusReserve(String statusReserve) {
        this.statusReserve = statusReserve;
    }

    public String getStatusReserve() {
        return statusReserve;
    }
}
