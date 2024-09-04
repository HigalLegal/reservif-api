package com.reservif.services;

import com.reservif.dto.requests.UserLoginRequest;
import com.reservif.dto.requests.UserRequest;
import com.reservif.dto.responses.TokenResponse;
import com.reservif.dto.responses.UserResponse;

import java.util.List;

public interface UserService {

    List<UserResponse> listAll(int offset, int limit);

    UserResponse byId(Integer id);

    TokenResponse listByIdentificationCodeOrEmail(UserLoginRequest login);

    void create(UserRequest userRequest);

    void update(UserRequest userRequest, Integer id);

    void deleteById(Integer id);

}
