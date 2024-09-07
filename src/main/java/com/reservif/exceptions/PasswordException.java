package com.reservif.exceptions;

public class PasswordException extends RuntimeException {
    public PasswordException(String mensagem) {
        super(mensagem);
    }
}
