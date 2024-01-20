package ar.edu.itba.paw.webapp.controller;


import ar.edu.itba.paw.webapp.controller.utils.UrlHolder;
import ar.edu.itba.paw.webapp.dto.output.BaseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path(UrlHolder.BASE)
@Component
public class BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseController.class);

    @Context
    private UriInfo uriInfo;

    @GET
    public Response getDiscovery(){
        return Response.ok(BaseDto.fromUriInfo(uriInfo)).build();
    }
}
