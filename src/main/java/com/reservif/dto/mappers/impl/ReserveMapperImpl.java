package com.reservif.dto.mappers.impl;

import com.reservif.dto.PeriodReserveDTO;
import com.reservif.dto.mappers.ReserveMapper;
import com.reservif.dto.requests.ReserveRequest;
import com.reservif.dto.responses.ReservationUserResponse;
import com.reservif.dto.responses.ReserveResponse;
import com.reservif.entities.PeriodReserve;
import com.reservif.entities.Reservable;
import com.reservif.entities.Reserve;
import com.reservif.entities.User;
import com.reservif.entities.enuns.StatusReserve;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.DayOfWeek;
import java.util.stream.Collectors;

@ApplicationScoped
public class ReserveMapperImpl implements ReserveMapper {

    @Override
    public Reserve requestToEntitie(ReserveRequest request) {
        return Reserve
                .builder()
                .observation(request.getObservation())
                .reservable(Reservable.builder().id(request.getReservableId()).build())
                .status(StatusReserve.PENDING)
                .period(periodDTOtoEntitie(request.getPeriodReserve()))
                .user(User.builder().id(request.getUserId()).build())
                .build();
    }

    @Override
    public ReserveResponse entitieToResponse(Reserve entitie) {
               return ReserveResponse
                    .builder()
                    .id(entitie.getId())
                    .observation(entitie.getObservation())
                    .reservable(entitie.getReservable().getName())
                    .status(entitie.getStatus())
                    .period(periodEntitieToDTO(entitie.getPeriod()))
                    .user(userToReservationUser(entitie.getUser()))
                    .build();
    }

    private PeriodReserve periodDTOtoEntitie(PeriodReserveDTO periodReserveDTO) {
        return PeriodReserve
                .builder()
                .startDay(periodReserveDTO.getStartDay())
                .endDay(periodReserveDTO.getEndDay())
                .startHorary(periodReserveDTO.getStartHorary())
                .endHorary(periodReserveDTO.getEndHorary())
                .daysOfWeek(periodReserveDTO
                        .getDaysOfWeek()
                        .stream()
                        .map(dayOfWeek -> DayOfWeek.of(dayOfWeek))
                        .collect(Collectors.toList()))
                .build();
    }

    private PeriodReserveDTO periodEntitieToDTO(PeriodReserve periodReserve) {
        return PeriodReserveDTO
                .builder()
                .startDay(periodReserve.getStartDay())
                .endDay(periodReserve.getEndDay())
                .startHorary(periodReserve.getStartHorary())
                .endHorary(periodReserve.getEndHorary())
                .daysOfWeek(periodReserve
                        .getDaysOfWeek()
                        .stream()
                        .map(dayOfWeek -> dayOfWeek.getValue())
                        .collect(Collectors.toList()))
                .build();
    }

    private ReservationUserResponse userToReservationUser(User user) {
        return ReservationUserResponse
                .builder()
                .id(user.getId())
                .fullName(user.getFirstName().concat(" ").concat(user.getLastName()))
                .email(user.getEmail())
                .identificationCode(user.getIdentificationCode())
                .typeUser(user.getTypeUser())
                .build();
    }

}
