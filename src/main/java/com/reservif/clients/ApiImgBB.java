package com.reservif.clients;

import com.reservif.clients.model.Root;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.reactive.RestForm;

import java.io.File;

@RegisterRestClient
@ApplicationScoped
public interface ApiImgBB {

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("upload")
    Root uploadImage(@QueryParam("key") String key, @RestForm("image") File image);

}
