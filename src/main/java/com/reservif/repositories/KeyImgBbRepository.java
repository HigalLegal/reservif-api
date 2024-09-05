package com.reservif.repositories;

import com.reservif.entities.KeyImgBB;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

@ApplicationScoped
public class KeyImgBbRepository implements PanacheRepositoryBase<KeyImgBB, String> {

    public String returnKey() {
        List<KeyImgBB> keys = findAll().list();

        int size = keys.size();

        if(size == 0) {
            throw new EntityNotFoundException("Não há nenhuma chave");
        }

        return keys.get(size - 1).getValue();

    }

}
