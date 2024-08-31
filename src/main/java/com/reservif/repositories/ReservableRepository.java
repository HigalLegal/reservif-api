package com.reservif.repositories;

import com.reservif.entities.Reservable;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

@ApplicationScoped
public class ReservableRepository implements PanacheRepositoryBase<Reservable, Integer> {

    public List<Reservable> findByName(String name) {
        return this.list("SELECT r FROM Reservable r WHERE r.name = :name",
                Parameters.with("name", name));
    }

    public void update(Reservable reservable) {
        final String JPQL = "UPDATE Reservable r SET r.name = :name, r.location = :location WHERE r.id = :id";

        Parameters parameters = Parameters
                .with("id", reservable.getId())
                .and("name", reservable.getName())
                .and("location", reservable.getLocation());

        int affectedRows = this.update(JPQL, parameters);

        if(affectedRows <= 0) {
            throw new EntityNotFoundException("Registro de local inválido");
        }

    }

}
