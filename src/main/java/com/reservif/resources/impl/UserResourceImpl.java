package com.reservif.resources.impl;

import com.reservif.dto.requests.UserLoginRequest;
import com.reservif.dto.requests.UserRequest;
import com.reservif.dto.responses.TokenResponse;
import com.reservif.dto.responses.UserResponse;
import com.reservif.resources.UserResource;
import com.reservif.services.UserService;
import io.quarkus.security.Authenticated;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.PartType;
import org.jboss.resteasy.reactive.RestForm;

import java.io.File;
import java.util.List;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class UserResourceImpl implements UserResource {

    @Inject
    private UserService userService;

    // ----------------------------------------------------------------------------------------------------

    @Override
    @GET
    @RolesAllowed("Administrador")
    public Response listAll(Integer page, Integer pageSize) {
        List<UserResponse> responses = userService.listAll(page, pageSize);
        return Response.ok(responses).build();
    }

    @Override
    @GET
    @Path("/{id}")
    @RolesAllowed("Administrador")
    public Response byId(@PathParam("id") Integer id) {
        UserResponse response = userService.byId(id);
        return Response.ok(response).build();
    }

    @Override
    @POST
    @Path("/login")
    @PermitAll
    public Response login(@Valid UserLoginRequest login) {
        TokenResponse tokenResponse = userService.login(login);
        return Response.ok(tokenResponse).build();
    }

    @Override
    @POST
    @RolesAllowed("Administrador")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response create(@RestForm @PartType(MediaType.APPLICATION_JSON) @Valid UserRequest userRequest,
                           @RestForm("image") @PartType(MediaType.MULTIPART_FORM_DATA) File image) {
        userService.create(userRequest, image);
        return Response.status(Response.Status.CREATED).build();
    }

    @Override
    @PUT
    @Path("/{id}")
    @Authenticated
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response update(@PathParam("id") Integer id,
                           @RestForm @PartType(MediaType.APPLICATION_JSON) @Valid UserRequest userRequest,
                           @RestForm("image") @PartType(MediaType.MULTIPART_FORM_DATA) File image) {
        userService.update(userRequest, image, id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @Override
    @DELETE
    @Path("/{id}")
    @RolesAllowed("Administrador")
    public Response deleteById(@PathParam("id") Integer id) {
        userService.deleteById(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
