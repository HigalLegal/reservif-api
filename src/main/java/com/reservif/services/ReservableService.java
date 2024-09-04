package com.reservif.services;

import com.reservif.dto.requests.ReservableRequest;
import com.reservif.dto.responses.ReservableResponse;

import java.util.List;

public interface ReservableService {

    List<ReservableResponse> listAll(int offset, int limit);

    List<ReservableResponse> listByName(String name);

    ReservableResponse byId(Integer id);

    void create(ReservableRequest reservableRequest);

    void updateById(ReservableRequest reservableRequest, Integer id);

    void deleteById(Integer id);

}
