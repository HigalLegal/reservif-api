package com.reservif.resources.impl;

import com.reservif.dto.requests.ApprovalRequest;
import com.reservif.dto.requests.ReserveRequest;
import com.reservif.dto.responses.ReserveResponse;
import com.reservif.entities.enuns.StatusReserve;
import com.reservif.exceptions.PermissionException;
import com.reservif.resources.ReserveResource;
import com.reservif.services.ReserveService;
import io.quarkus.security.Authenticated;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.annotation.Nullable;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@Path("reserves")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class ReserveResourceImpl implements ReserveResource {

    @Inject
    private ReserveService reserveService;

    @Inject
    private JsonWebToken jsonWebToken;

    // ---------------------------------------------------------------------

    @Override
    @GET
    @Authenticated
    public Response listAll(@QueryParam("page") @Nullable Integer page,
                            @QueryParam("pageSize") @Nullable Integer pageSize) {
        List<ReserveResponse> responses = reserveService.listAll(page, pageSize);
        return Response.ok(responses).build();
    }

    @Override
    @GET
    @Path("/by-date-interval")
    @Authenticated
    public Response listByDateInterval(@QueryParam("beginning") @Nullable LocalDate beginning,
                                       @QueryParam("end") @Nullable LocalDate end) {
        List<ReserveResponse> responses = reserveService.listByDateInterval(beginning, end);
        return Response.ok(responses).build();
    }

    @Override
    @GET
    @Path("/by-horary-interval")
    @Authenticated
    public Response listByHoraryInterval(@QueryParam("beginning") @Nullable LocalTime beginning,
                                         @QueryParam("end") @Nullable LocalTime end) {
        List<ReserveResponse> responses = reserveService.listByHoraryInterval(beginning, end);
        return Response.ok(responses).build();
    }

    @Override
    @GET
    @Path("/by-reservable-name")
    @Authenticated
    public Response listByReservableName(@QueryParam("reservableName") @DefaultValue("") String reservableName) {
        List<ReserveResponse> responses = reserveService.listByReservableName(reservableName);
        return Response.ok(responses).build();
    }

    @Override
    @GET
    @Path("/by-status")
    @RolesAllowed("Administrador")
    public Response listByStatus(@QueryParam("statusReserve") @Nullable StatusReserve statusReserve) {
        List<ReserveResponse> responses = reserveService.listByStatus(statusReserve);
        return Response.ok(responses).build();
    }

    @Override
    @GET
    @Path("/by-user")
    @RolesAllowed("Administrador")
    public Response listByUser(@QueryParam("userID") Integer userID) {
        List<ReserveResponse> responses = reserveService.listByUser(userID);
        return Response.ok(responses).build();
    }

    @Override
    @GET
    @Path("/by-reservable")
    @Authenticated
    public Response listByReservable(@QueryParam("reservableID") Integer reservableID) {
        List<ReserveResponse> responses = reserveService.listByReservable(reservableID);
        return Response.ok(responses).build();
    }

    @Override
    @GET
    @Path("/{id}")
    @Authenticated
    public Response byId(@PathParam("id") Integer id) {
        ReserveResponse response = reserveService.byId(id);
        return Response.ok(response).build();
    }

    @Override
    @POST
    @Authenticated
    public Response create(@Valid ReserveRequest reserveRequest) {
        reserveService.create(reserveRequest);
        return Response
                .status(Response.Status.CREATED)
                .build();
    }

    @Override
    @PUT
    @Path("/{id}")
    @Authenticated
    public Response updateById(@PathParam("id") Integer id, @Valid ReserveRequest reserveRequest) {
        Integer userID = Integer.parseInt(jsonWebToken.getClaim("userID").toString());
        Set<String> roles = jsonWebToken.getGroups();

        if(userID != reserveRequest.getUserId() && !roles.contains("Administrador")) {
            throw new PermissionException("Você não tem permissão para editar essa reserva");
        }

        reserveService.updateById(reserveRequest, id);

        return Response
                .status(Response.Status.NO_CONTENT)
                .build();
    }

    @Override
    @PATCH
    @Path("/{id}")
    @RolesAllowed("Administrador")
    public Response approve(@PathParam("id") Integer id, @Valid ApprovalRequest approved) {
        reserveService.approve(approved.getApproved(), id);
        return Response
                .status(Response.Status.OK)
                .build();
    }

    @Override
    @DELETE
    @Path("/{id}")
    @RolesAllowed("Administrador")
    public Response deleteById(Integer id) {
        reserveService.deleteById(id);
        return Response
                .status(Response.Status.OK)
                .build();
    }
}
