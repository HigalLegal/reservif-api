package com.reservif.services;

import com.reservif.dto.requests.ReserveRequest;
import com.reservif.dto.responses.ReserveResponse;
import com.reservif.entities.enuns.StatusReserve;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReserveService {

    List<ReserveResponse> listAll(Integer page, Integer pageSize);

    List<ReserveResponse> listByDateInterval(LocalDate beginning, LocalDate end);

    List<ReserveResponse> listByHoraryInterval(LocalTime beginning, LocalTime end);

    List<ReserveResponse> listByReservableName(String reservableName);

    List<ReserveResponse> listByStatus(StatusReserve statusReserve);

    List<ReserveResponse> listByUser(Integer userID);

    List<ReserveResponse> listByReservable(Integer reservableID);

    ReserveResponse byId(Integer id);

    void create(ReserveRequest reserveRequest);

    void updateById(ReserveRequest reserveRequest, Integer id);

    void approve(boolean approved, Integer id);

    void deleteById(Integer id);
}
