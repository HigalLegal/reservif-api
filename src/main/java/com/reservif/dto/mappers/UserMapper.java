package com.reservif.dto.mappers;

import com.reservif.dto.requests.UserRequest;
import com.reservif.dto.responses.UserResponse;
import com.reservif.entities.User;

public interface UserMapper extends Mapper<User, UserRequest, UserResponse> {
}
