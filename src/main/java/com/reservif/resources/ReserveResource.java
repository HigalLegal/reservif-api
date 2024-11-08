package com.reservif.resources;

import com.reservif.dto.requests.ApprovalRequest;
import com.reservif.dto.requests.ReserveRequest;
import com.reservif.entities.enuns.StatusReserve;
import jakarta.validation.Valid;
import jakarta.ws.rs.core.Response;

import java.time.LocalDate;
import java.time.LocalTime;

public interface ReserveResource {

    Response listAll(Integer page, Integer pageSize);

    Response listByDateInterval(String beginning, String end);

    Response listByHoraryInterval(LocalTime beginning, LocalTime end);

    Response listByReservableName(String reservableName);

    Response listByStatus(String statusReserve);

    Response listByUser(Integer userID);

    Response listByReservable(Integer reservableID);

    Response byId(Integer id);

    Response create(@Valid ReserveRequest reserveRequest);

    Response updateById(Integer id, @Valid ReserveRequest reserveRequest);

    Response approve(Integer id, @Valid ApprovalRequest approved);

    Response deleteById(Integer id);

}
