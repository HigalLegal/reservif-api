package com.reservif.resources;

import com.reservif.dto.requests.ApprovalRequest;
import com.reservif.dto.requests.ReserveRequest;
import com.reservif.entities.enuns.StatusReserve;
import jakarta.ws.rs.core.Response;

import java.time.LocalDate;
import java.time.LocalTime;

public interface ReserveResource {

    Response listAll(Integer page, Integer pageSize);

    Response listByDateInterval(LocalDate beginning, LocalDate end);

    Response listByHoraryInterval(LocalTime beginning, LocalTime end);

    Response listByReservableName(String reservableName);

    Response listByStatus(StatusReserve statusReserve);

    Response listByUser(Integer userID);

    Response listByReservable(Integer reservableID);

    Response byId(Integer id);

    Response create(ReserveRequest reserveRequest);

    Response updateById(Integer id, ReserveRequest reserveRequest);

    Response approve(Integer id, ApprovalRequest approved);

    Response deleteById(Integer id);

}
