package com.reservif.exceptions;

public class ScheduleUnavailableException extends RuntimeException {
    public ScheduleUnavailableException(String message) {
        super(message);
    }
}
