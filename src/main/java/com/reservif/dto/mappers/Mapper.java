package com.reservif.dto.mappers;

public interface Mapper<E, I, O> {

    E requestToEntitie(I request);

    O entitieToResponse(E entitie);

}
