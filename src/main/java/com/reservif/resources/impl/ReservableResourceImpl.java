package com.reservif.resources.impl;

import com.reservif.dto.requests.ReservableRequest;
import com.reservif.dto.responses.ReservableResponse;
import com.reservif.resources.ReservableResource;
import com.reservif.services.ReservableService;
import io.quarkus.security.Authenticated;
import jakarta.annotation.Nullable;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("reservables")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class ReservableResourceImpl implements ReservableResource {

    @Inject
    private ReservableService reservableService;

    // ------------------------------------------------------------------------

    @Override
    @GET
    @Authenticated
    public Response listAll(@QueryParam("page") @Nullable Integer page,
                            @QueryParam("pageSize") @Nullable Integer pageSize) {
        List<ReservableResponse> responses = reservableService.listAll(page, pageSize);
        return Response.ok(responses).build();
    }

    @Override
    @GET
    @Path("/by-name")
    @Authenticated
    public Response listByName(@QueryParam("name") @DefaultValue("") String name) {
        List<ReservableResponse> responses = reservableService.listByName(name);
        return Response.ok(responses).build();
    }

    @Override
    @GET
    @Path("/{id}")
    @Authenticated
    public Response byId(@PathParam("id") Integer id) {
        ReservableResponse response = reservableService.byId(id);
        return Response.ok(response).build();
    }

    @Override
    @POST
    @RolesAllowed("Administrador")
    public Response create(@Valid ReservableRequest reservableRequest) {
        reservableService.create(reservableRequest);
        return Response.status(Response.Status.CREATED).build();
    }

    @Override
    @PUT
    @Path("/{id}")
    @RolesAllowed("Administrador")
    public Response updateById(@PathParam("id") Integer id, ReservableRequest reservableRequest) {
        reservableService.updateById(reservableRequest, id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @Override
    @DELETE
    @Path("/{id}")
    @RolesAllowed("Administrador")
    public Response deleteById(@PathParam("id") Integer id) {
        reservableService.deleteById(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
