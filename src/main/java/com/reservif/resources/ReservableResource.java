package com.reservif.resources;

import com.reservif.dto.requests.ReservableRequest;
import jakarta.validation.Valid;
import jakarta.ws.rs.core.Response;

public interface ReservableResource {

    Response listAll(Integer page, Integer pageSize);

    Response listByName(String name);

    Response byId(Integer id);

    Response create(@Valid ReservableRequest reservableRequest);

    Response updateById(Integer id, @Valid ReservableRequest reservableRequest);

    Response deleteById(Integer id);

}
