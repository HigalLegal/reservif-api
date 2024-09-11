package com.reservif.resources;

import com.reservif.dto.requests.ReservableRequest;
import jakarta.ws.rs.core.Response;

public interface ReservableResource {

    Response listAll(Integer page, Integer pageSize);

    Response listByName(String name);

    Response byId(Integer id);

    Response create(ReservableRequest reservableRequest);

    Response updateById(Integer id, ReservableRequest reservableRequest);

    Response deleteById(Integer id);

}
