package com.reservif.dto.mappers;

import com.reservif.dto.requests.ReservableRequest;
import com.reservif.dto.responses.ReservableResponse;
import com.reservif.entities.Reservable;

public interface ReservableMapper extends Mapper<Reservable, ReservableRequest, ReservableResponse> {
}