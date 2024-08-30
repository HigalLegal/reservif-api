package com.reservif.dto.mappers.impl;

import com.reservif.dto.mappers.Mapper;
import com.reservif.dto.requests.ReservableRequest;
import com.reservif.dto.responses.ReservableResponse;
import com.reservif.entities.Reservable;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@ApplicationScoped
@Named("Reservable")
public class ReservableMapperImpl implements Mapper<Reservable, ReservableRequest, ReservableResponse> {

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
