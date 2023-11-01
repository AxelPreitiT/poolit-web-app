package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.exceptions.CityNotFoundException;
import ar.edu.itba.paw.interfaces.services.CityService;
import ar.edu.itba.paw.models.City;
import ar.edu.itba.paw.webapp.controller.mediaType.VndType;
import ar.edu.itba.paw.webapp.dto.output.CityDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/api/cities")
public class CityController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CityController.class);

    private final CityService cityService;

    @Context
    private UriInfo uriInfo;

    @Inject
    @Autowired
    public CityController(final CityService cityService){
        this.cityService = cityService;
    }

    @GET
    @Path("/{id}")
    @Produces(VndType.APPLICATION_CITY)
    public Response getById(@PathParam("id") final int id) throws CityNotFoundException{
        LOGGER.debug("GET request for city with cityId {}",id);
        final City ans = cityService.findCityById(id).orElseThrow(CityNotFoundException::new);
        return Response.ok(CityDto.fromCity(uriInfo,ans)).build();
    }
}
