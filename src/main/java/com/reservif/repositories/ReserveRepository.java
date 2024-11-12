package com.reservif.repositories;

import com.reservif.entities.PeriodReserve;
import com.reservif.entities.Reserve;
import com.reservif.entities.enuns.StatusReserve;
import com.reservif.exceptions.DateException;
import com.reservif.exceptions.HoraryException;
import com.reservif.exceptions.ScheduleUnavailableException;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
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

        final String JPQL = "SELECT r FROM Reserve r where r.status = :statusReserve";
        Parameters parameters = Parameters.with("statusReserve", statusReserve);

        return this.list(JPQL, parameters);
    }

    public List<Reserve> findByUserId(Integer userID) {
        final String JPQL = "SELECT r FROM Reserve r where r.user.id = :userID";
        Parameters parameters = Parameters.with("userID", userID);

        return this.list(JPQL, parameters);
    }

    public List<Reserve> findByReservableId(Integer reservableID) {
        final String JPQL = "SELECT r FROM Reserve r where r.reservable.id = :reservableID";
        Parameters parameters = Parameters.with("reservableID", reservableID);

        return this.list(JPQL, parameters);
    }

    public void persistReserve(Reserve reserve) {

        if(checkReservationAvailability(reserve)) {
            throw new ScheduleUnavailableException("Essa reserva choca horário com outra reserva já feita.");
        }

        this.persist(reserve);
    }

    public void update(Reserve reserve) {
        
        Reserve newReserve = this
                .findByIdOptional(reserve.getId())
                .orElseThrow(() -> new EntityNotFoundException("Reserva inexistente"));

        newReserve.setObservation(reserve.getObservation());
        newReserve.updatePeriodReserve(reserve.getPeriod());

        this.getEntityManager().merge(newReserve);

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
        jpql.append(" AND r.period.end" + horaryOrDay + " <= :end" + horaryOrDay);

        return jpql.toString();
    }

    private boolean checkReservationAvailability(Reserve newReservation) {
        List<Reserve> allReserves = this.findAll(null, null);

        for (Reserve reserve : allReserves) {
            if (checkReservationAvailabilityAux(newReservation, reserve)) {
                return true;
            }
        }

        return false;
    }

    private boolean checkReservationAvailabilityAux(Reserve newReserve, Reserve oldReserve) {

        if(newReserve.getReservable().getId() != oldReserve.getReservable().getId()) {
            return false;
        }

        PeriodReserve newReservation = newReserve.getPeriod();
        PeriodReserve oldReservation = oldReserve.getPeriod();

        return dayWeekOverlap(newReservation, oldReservation) &&
                datesOverlap(newReservation.getStartDay(), newReservation.getEndDay(), oldReservation.getStartDay(), oldReservation.getEndDay()) &&
                timesOverlap(newReservation.getStartHorary(), newReservation.getEndHorary(), oldReservation.getStartHorary(), oldReservation.getEndHorary());
    }

    private boolean dayWeekOverlap(PeriodReserve newReservation, PeriodReserve oldReservation) {

        List<DayOfWeek> daysOfWeek1 = generateDaysOfWeek(newReservation.getDaysOfWeek(), newReservation);
        List<DayOfWeek> daysOfWeek2 = generateDaysOfWeek(oldReservation.getDaysOfWeek(), oldReservation);

        return !Collections.disjoint(daysOfWeek1, daysOfWeek2);
    }

    public static boolean datesOverlap(LocalDate startDate1, LocalDate endDate1,
                                          LocalDate startDate2, LocalDate endDate2) {
        return (startDate1.isBefore(endDate2) || startDate1.isEqual(endDate2)) &&
                (startDate2.isBefore(endDate1) || startDate2.isEqual(endDate1));
    }
    private boolean timesOverlap(LocalTime start1, LocalTime end1, LocalTime start2, LocalTime end2) {
        return (start1.isBefore(end2)) && (start2.isBefore(end1));
    }

    private List<DayOfWeek> generateDaysOfWeek(List<DayOfWeek> daysOfWeek, PeriodReserve periodReserve) {
        if(!daysOfWeek.isEmpty()) {
            return daysOfWeek;
        }

        List<DayOfWeek> daysOfTheWeekGenerated = new ArrayList<>();

        DayOfWeek dayOfWeek1 = periodReserve.getStartDay().getDayOfWeek();
        DayOfWeek dayOfWeek2 = periodReserve.getEndDay().getDayOfWeek();


        daysOfTheWeekGenerated.add(dayOfWeek1);
        daysOfTheWeekGenerated.add(dayOfWeek2);

        return daysOfTheWeekGenerated;
    }

    private boolean checkIntersection(List<DayOfWeek> daysOfWeek1, List<DayOfWeek> daysOfWeek2) {

        for(DayOfWeek dayOfWeek : daysOfWeek1) {
            if(daysOfWeek2.contains(dayOfWeek)) {
                return true;
            }
        }

        return false;
    }

}