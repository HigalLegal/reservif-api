package com.reservif.repositories;

import com.reservif.entities.Reserve;
import com.reservif.entities.enuns.StatusReserve;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityNotFoundException;

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

    public List<Reserve> findByReservableName(String reservableName) {
        final String JPQL = "SELECT r FROM Reserve r where r.reservable.name = :reservableName";
        Parameters parameters = Parameters.with("reservableName", reservableName);

        return this.list(JPQL, parameters);
    }

    public List<Reserve> findByStatus(StatusReserve statusReserve) {
        final String JPQL = "SELECT r FROM Reserve r where r.statusReserve = :statusReserve";
        Parameters parameters = Parameters.with("statusReserve", statusReserve);

        return this.list(JPQL, parameters);
    }

    private String generateGenericSearchByInterval(String horaryOrDay) {
       StringBuilder jpql = new StringBuilder("SELECT r FROM Reserve r WHERE r.period.start");
       jpql.append(horaryOrDay + " >= :start" + horaryOrDay);
       jpql.append(" AND r.period.end" + horaryOrDay + " <= :endDate");

       return jpql.toString();
    }

    public void update(Reserve reserve) {
        final String JPQL = "UPDATE Reserve r " +
                "SET r.status = :status," +
                "    r.observation = :observation," +
                "    r.period.startDay = :startDay," +
                "    r.period.endDay = :endDay, " +
                "    r.period.startHorary = :startHorary," +
                "    r.period.endHorary = :endHorary," +
                "    r.period.daysOfWeek = :daysOfWeek," +
                "    r.reservable.id = :reservableId" +
                " WHERE r.id = :id";

        Parameters parameters = Parameters.with("status", reserve.getStatus())
                .and("observation", reserve.getObservation())
                .and("startDay", reserve.getPeriod().getStartDay())
                .and("endDay", reserve.getPeriod().getEndDay())
                .and("startHorary", reserve.getPeriod().getStartHorary())
                .and("endHorary", reserve.getPeriod().getEndHorary())
                .and("daysOfWeek", reserve.getPeriod().getDaysOfWeek())
                .and("reservableId", reserve.getReservable().getId())
                .and("id", reserve.getId());

        int affectedRows = this.update(JPQL, parameters);

        if(affectedRows <= 0) {
            throw new EntityNotFoundException("Registro de reserva inválida");
        }

    }

}
