package com.reservif.dto.mappers.impl;

import com.reservif.dto.PeriodReserveDTO;
import com.reservif.dto.mappers.Mapper;
import com.reservif.dto.mappers.ReserveMapper;
import com.reservif.dto.requests.ReserveRequest;
import com.reservif.dto.responses.ReserveResponse;
import com.reservif.entities.PeriodReserve;
import com.reservif.entities.Reservable;
import com.reservif.entities.Reserve;
import com.reservif.entities.enuns.StatusReserve;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import java.time.DayOfWeek;
import java.util.stream.Collectors;

@ApplicationScoped
@Named("Reserve")
public class ReserveMapperImpl implements ReserveMapper {

    @Override
    public Reserve requestToEntitie(ReserveRequest request) {
        return Reserve
                .builder()
                .observation(request.getObservation())
                .reservable(Reservable.builder().id(request.getReservableId()).build())
                .status(StatusReserve.PENDING)
                .period(periodDTOtoEntitie(request.getPeriodReserve()))
                .build();
    }

    @Override
    public ReserveResponse entitieToResponse(Reserve entitie) {
               return ReserveResponse.builder()
                .observation(entitie.getObservation())
                .reservable(entitie.getReservable().getName())
                .status(entitie.getStatus())
                .period(periodEntitieToDTO(entitie.getPeriod()))
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

}
