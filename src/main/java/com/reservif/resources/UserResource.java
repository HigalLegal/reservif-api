package com.reservif.resources;

import com.reservif.dto.requests.UserLoginRequest;
import com.reservif.dto.requests.UserRequest;
import jakarta.validation.Valid;
import jakarta.ws.rs.core.Response;

import java.io.File;

public interface UserResource {

    Response listAll(Integer page, Integer pageSize);

    Response byId(Integer id);

    Response login(@Valid UserLoginRequest login);

    Response create(@Valid UserRequest userRequest, File image);

    Response update(Integer id, @Valid UserRequest userRequest, File image);

    Response deleteById(Integer id);

}
