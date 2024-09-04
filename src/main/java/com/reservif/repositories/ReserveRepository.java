package com.reservif.repositories;

import com.reservif.entities.Reserve;
import com.reservif.entities.enuns.StatusReserve;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static com.reservif.repositories.utils.PaginationUtils.idealLimitReturn;

@ApplicationScoped
public class ReserveRepository implements PanacheRepositoryBase<Reserve, Integer> {

    @Inject
    private EntityManager entityManager;

    // ---------------------------------------------------------------------------------

    public List<Reserve> findAll(int offset, int limit) {

        limit = idealLimitReturn(limit, offset, (int) this.count());

        return entityManager
                .createQuery("SELECT r FROM Reserve r ", Reserve.class)
                .setFirstResult(Math.max(offset, 0))
                .setMaxResults(Math.max(limit, 0))
                .getResultList();
    }

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
        final String JPQL = "SELECT r FROM Reserve r where lower(r.reservable.name) LIKE " +
                "lower(concat('%', :reservableName, '%'))";
        Parameters parameters = Parameters.with("reservableName", "%" + reservableName + "%");

        return this.list(JPQL, parameters);
    }

    public List<Reserve> findByStatus(StatusReserve statusReserve) {
        final String JPQL = "SELECT r FROM Reserve r where r.statusReserve = :statusReserve";
        Parameters parameters = Parameters.with("statusReserve", statusReserve);

        return this.list(JPQL, parameters);
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

    public void approve(boolean approved, Integer id) {
        final String JPQL = "UPDATE Reserve r SET r.status = :status WHERE r.id = :id";

        StatusReserve statusReserve = approved ? StatusReserve.APPROVED : StatusReserve.DISAPPROVED;

        Parameters parameters = Parameters
                .with("status", statusReserve)
                .and("id", id);

        int affectedRows = this.update(JPQL, parameters);

        if(affectedRows <= 0) {
            throw new EntityNotFoundException("Registro de reserva inválida");
        }
    }

    private String generateGenericSearchByInterval(String horaryOrDay) {
        StringBuilder jpql = new StringBuilder("SELECT r FROM Reserve r WHERE r.period.start");
        jpql.append(horaryOrDay + " >= :start" + horaryOrDay);
        jpql.append(" AND r.period.end" + horaryOrDay + " <= :endDate");

        return jpql.toString();
    }

}