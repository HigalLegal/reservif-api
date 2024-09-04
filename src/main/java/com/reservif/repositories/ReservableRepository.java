package com.reservif.repositories;

import com.reservif.entities.Reservable;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;

import static com.reservif.repositories.utils.PaginationUtils.idealLimitReturn;

@ApplicationScoped
public class ReservableRepository implements PanacheRepositoryBase<Reservable, Integer> {

    @Inject
    private EntityManager entityManager;

    // ---------------------------------------------------------------------------------

    public List<Reservable> findAll(int offset, int limit) {

        limit = idealLimitReturn(limit, offset, (int) this.count());

        return entityManager
                .createQuery("SELECT r FROM Reservable r ", Reservable.class)
                .setFirstResult(Math.max(offset, 0))
                .setMaxResults(Math.max(limit, 0))
                .getResultList();
    }

    public List<Reservable> findByName(String name) {
        return this.list("SELECT r FROM Reservable r WHERE lower(r.name) LIKE lower(concat('%', :name, '%'))",
                Parameters.with("name", "%" + name + "%"));
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
