package com.reservif.dto.mappers.impl;

import com.reservif.dto.mappers.ReservableMapper;
import com.reservif.dto.requests.ReservableRequest;
import com.reservif.dto.responses.ReservableResponse;
import com.reservif.entities.Reservable;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ReservableMapperImpl implements ReservableMapper {

    @Override
    public Reservable requestToEntitie(ReservableRequest request) {
        return Reservable
                .builder()
                .name(request.getName())
                .location(request.getLocation())
                .build();
    }

    @Override
    public ReservableResponse entitieToResponse(Reservable entitie) {
        return ReservableResponse
                .builder()
                .id(entitie.getId())
                .name(entitie.getName())
                .location(entitie.getLocation())
                .build();
    }


}
