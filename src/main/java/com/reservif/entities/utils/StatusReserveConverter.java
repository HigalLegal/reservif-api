package com.reservif.entities.utils;

import com.reservif.entities.enuns.StatusReserve;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class StatusReserveConverter implements AttributeConverter<StatusReserve, String> {

    @Override
    public String convertToDatabaseColumn(StatusReserve statusReserve) {
        return statusReserve != null ? statusReserve.getStatusReserve() : null;
    }

    @Override
    public StatusReserve convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }

        for (StatusReserve status : StatusReserve.values()) {
            if (status.getStatusReserve().equals(dbData)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Valor desconhecido para StatusReserve: " + dbData);
    }
}