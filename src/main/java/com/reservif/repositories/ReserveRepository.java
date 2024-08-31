package com.reservif.repositories;

import com.reservif.entities.Reserve;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.Temporal;
import java.util.List;

@ApplicationScoped
public class ReserveRepository implements PanacheRepositoryBase<Reserve, Integer> {

    public List<Reserve> findByDateInterval(LocalDate beginning, LocalDate end) {
        final String JPQL = generateGenericSearchByInterval("Day");
        Parameters parameters = Parameters
                .with("startDay", beginning)
                .and("endDay", end);
        return this.list(JPQL, parameters);
    }

    public List<Reserve> findByHoraryInterval(LocalTime beginning, LocalTime end) {
        final String JPQL = generateGenericSearchByInterval("Horary");
        Parameters parameters = Parameters
                .with("startHorary", beginning)
                .and("endHorary", end);
        return this.list(JPQL, parameters);
    }

    private String generateGenericSearchByInterval(String horaryOrDay) {
       StringBuilder jpql = new StringBuilder("SELECT r FROM Reserve r WHERE r.period.start");
       jpql.append(horaryOrDay + " >= :start" + horaryOrDay);
       jpql.append(" AND r.period.end" + horaryOrDay + " <= :endDate");

       return jpql.toString();
    }

}
