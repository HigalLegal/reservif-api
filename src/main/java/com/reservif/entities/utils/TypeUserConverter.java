package com.reservif.entities.utils;

import com.reservif.entities.enuns.TypeUser;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TypeUserConverter implements AttributeConverter<TypeUser, String> {

    @Override
    public String convertToDatabaseColumn(TypeUser typeUser) {
        if (typeUser == null) {
            return null;
        }
        return typeUser.getTypeUser(); // Armazena "Professor" ou "Administrador" no banco de dados
    }

    @Override
    public TypeUser convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return null;
        }

        // Percorre os valores do enum e encontra o correspondente
        for (TypeUser type : TypeUser.values()) {
            if (type.getTypeUser().equals(dbData)) {
                return type;
            }
        }

        throw new IllegalArgumentException("Valor desconhecido: " + dbData);
    }
}
