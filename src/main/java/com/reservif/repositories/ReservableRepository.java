package com.reservif.repositories;

import com.reservif.entities.Reservable;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.List;

import static com.reservif.repositories.utils.PaginationUtils.treatNullInteger;

@ApplicationScoped
public class ReservableRepository implements PanacheRepositoryBase<Reservable, Integer> {

    @Inject
    @ConfigProperty(name = "pagination.defaultPage")
    private int defaultPage;

    @Inject
    @ConfigProperty(name = "pagination.defaultPageSize")
    private int defaultPageSize;

    public List<Reservable> findAll(Integer page, Integer pageSize) {

        int defaultPage = treatNullInteger(page, this.defaultPage);
        int defaultPageSize = treatNullInteger(page, this.defaultPageSize);

        if(page == null && pageSize == null) {
            return this.findAll().list();
        }

        return this
                .findAll()
                .page(defaultPage, defaultPageSize)
                .list();
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
