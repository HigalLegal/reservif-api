package com.reservif.repositories;

import com.reservif.entities.Reserve;
import com.reservif.entities.enuns.StatusReserve;
import com.reservif.exceptions.DateException;
import com.reservif.exceptions.HoraryException;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static com.reservif.repositories.utils.PaginationUtils.treatNullInteger;

@ApplicationScoped
public class ReserveRepository implements PanacheRepositoryBase<Reserve, Integer> {

    @Inject
    @ConfigProperty(name = "pagination.defaultPage")
    private int defaultPage;

    @Inject
    @ConfigProperty(name = "pagination.defaultPageSize")
    private int defaultPageSize;

    public List<Reserve> findAll(Integer page, Integer pageSize) {

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

    public List<Reserve> findByDateInterval(LocalDate beginning, LocalDate end) {

        if(beginning == null) {
            throw new DateException("A data de início não pode ser nula!");
        }

        if(end == null) {
            throw new DateException("A data de fim não pode ser nula!");
        }

        final String JPQL = generateGenericSearchByInterval("Day");
        Parameters parameters = Parameters
                .with("startDay", beginning)
                .and("endDay", end);
        return this.list(JPQL, parameters);
    }

    public List<Reserve> findByHoraryInterval(LocalTime beginning, LocalTime end) {

        if(beginning == null) {
            throw new HoraryException("O horário de início não pode ser nulo!");
        }

        if(end == null) {
            throw new HoraryException("O horário de fim não pode ser nulo!");
        }

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

        if(statusReserve == null) {
            this.findAll().list();
        }

        final String JPQL = "SELECT r FROM Reserve r where r.statusReserve = :statusReserve";
        Parameters parameters = Parameters.with("statusReserve", statusReserve);

        return this.list(JPQL, parameters);
    }

    public List<Reserve> findByUserId(Integer userID) {
        final String JPQL = "SELECT r FROM Reserve r where r.user.id = :userID";
        Parameters parameters = Parameters.with("userID", userID);

        return this.list(JPQL, parameters);
    }

    public List<Reserve> findByReservableId(Integer reservableID) {
        final String JPQL = "SELECT r FROM Reserve r where r.reservable.id = reservableID";
        Parameters parameters = Parameters.with("reservableID", reservableID);

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

    public void approve(Boolean approved, Integer id) {
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