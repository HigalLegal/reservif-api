package com.reservif.entities.enuns;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TypeUser {

    TEACHER("Professor"), ADMIN("Administrador");

    private final String typeUser;

    TypeUser(String typeUser) {
        this.typeUser = typeUser;
    }

    @JsonValue
    public String getTypeUser() {
        return typeUser;
    }
}
