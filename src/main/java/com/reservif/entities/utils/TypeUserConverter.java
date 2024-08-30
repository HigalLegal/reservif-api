package com.reservif.entities.utils;

import com.reservif.entities.enuns.TypeUser;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TypeUserConverter implements AttributeConverter<TypeUser, String> {

    @Override
    public String convertToDatabaseColumn(TypeUser typeUser) {
        return typeUser.getTypeUser();
    }

    @Override
    public TypeUser convertToEntityAttribute(String s) {
        return TypeUser.valueOf(s);
    }
}