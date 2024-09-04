package com.reservif.dto.mappers;

import com.reservif.dto.requests.ReserveRequest;
import com.reservif.dto.responses.ReserveResponse;
import com.reservif.entities.Reserve;

public interface ReserveMapper extends Mapper<Reserve, ReserveRequest, ReserveResponse> {
}
