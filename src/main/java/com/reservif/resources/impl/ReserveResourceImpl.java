package com.reservif.resources.impl;

import com.reservif.dto.requests.ApprovalRequest;
import com.reservif.dto.requests.ReserveRequest;
import com.reservif.entities.enuns.StatusReserve;
import com.reservif.resources.ReserveResource;
import io.quarkus.security.Authenticated;
import jakarta.annotation.Nullable;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDate;
import java.time.LocalTime;

@Path("reserves")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class ReserveResourceImpl implements ReserveResource {

    @Override
    @GET
    @Authenticated
    public Response listAll(@QueryParam("page") @Nullable Integer page,
                            @QueryParam("pageSize") @Nullable Integer pageSize) {
        return null;
    }

    @Override
    @GET
    @Path("/by-date-interval")
    @Authenticated
    public Response listByDateInterval(@QueryParam("beginning") @Nullable LocalDate beginning,
                                       @QueryParam("end") @Nullable LocalDate end) {
        return null;
    }

    @Override
    @GET
    @Path("/by-horary-interval")
    @Authenticated
    public Response listByHoraryInterval(@QueryParam("beginning") @Nullable LocalTime beginning,
                                         @QueryParam("end") @Nullable LocalTime end) {
        return null;
    }

    @Override
    @GET
    @Path("/by-reservable-name")
    @Authenticated
    public Response listByReservableName(@QueryParam("reservableName") @DefaultValue("") String reservableName) {
        return null;
    }

    @Override
    @GET
    @Path("/by-status")
    @RolesAllowed("Administrador")
    public Response listByStatus(@QueryParam("statusReserve") @Nullable StatusReserve statusReserve) {
        return null;
    }

    @Override
    @GET
    @Path("/by-user")
    @RolesAllowed("Administrador")
    public Response listByUser(@QueryParam("userID") Integer userID) {
        return null;
    }

    @Override
    @GET
    @Path("/by-reservable")
    @Authenticated
    public Response listByReservable(@QueryParam("reservableID") Integer reservableID) {
        return null;
    }

    @Override
    @GET
    @Path("/{id}")
    @Authenticated
    public Response byId(@PathParam("id") Integer id) {
        return null;
    }

    @Override
    @POST
    @Authenticated
    public Response create(@Valid ReserveRequest reserveRequest) {
        return null;
    }

    @Override
    @PUT
    @Path("/{id}")
    @Authenticated
    public Response updateById(@PathParam("id") Integer id, @Valid ReserveRequest reserveRequest) {
        return null;
    }

    @Override
    @PATCH
    @Path("/{id}")
    @RolesAllowed("Administrador")
    public Response approve(@PathParam("id") Integer id, @Valid ApprovalRequest approved) {
        return null;
    }

    @Override
    @DELETE
    @Path("/{id}")
    @RolesAllowed("Administrador")
    public Response deleteById(Integer id) {
        return null;
    }
}
