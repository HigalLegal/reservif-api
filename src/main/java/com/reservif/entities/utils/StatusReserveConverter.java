package com.reservif.entities.utils;

import com.reservif.entities.enuns.StatusReserve;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class StatusReserveConverter implements AttributeConverter<StatusReserve, String> {

    @Override
    public String convertToDatabaseColumn(StatusReserve statusReserve) {
        return statusReserve.getStatusReserve();
    }

    @Override
    public StatusReserve convertToEntityAttribute(String s) {
        return StatusReserve.valueOf(s);
    }
}
