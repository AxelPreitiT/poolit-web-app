package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.exceptions.CityNotFoundException;
import ar.edu.itba.paw.interfaces.services.CityService;
import ar.edu.itba.paw.models.City;
import ar.edu.itba.paw.webapp.controller.mediaType.VndType;
import ar.edu.itba.paw.webapp.controller.utils.ControllerUtils;
import ar.edu.itba.paw.webapp.controller.utils.UrlHolder;
import ar.edu.itba.paw.webapp.dto.output.CityDto;
import ar.edu.itba.paw.webapp.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Path(UrlHolder.CITY_BASE)
public class CityController {

    private final static long DEFAULT_PROVINCE_ID = 1;

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
    @Path("/")
    @Produces(VndType.APPLICATION_CITY_LIST)
    public Response getAllCities(){
        LOGGER.debug("GET request for all cities");
        final List<CityDto> cities = cityService.getCitiesByProvinceId(DEFAULT_PROVINCE_ID).stream().map(city -> CityDto.fromCity(uriInfo,city)).collect(Collectors.toList());
        Response.ResponseBuilder res =  Response.ok(new GenericEntity<List<CityDto>>(cities){});
        return ControllerUtils.getUnconditionalCacheResponseBuilder(res).build();
    }

    @GET
    @Path("/{id:\\d+}")
    @Produces(VndType.APPLICATION_CITY)
    public Response getById(@PathParam("id") final int id){
        LOGGER.debug("GET request for city with cityId {}",id);
        final City ans = cityService.findCityById(id).orElseThrow(ResourceNotFoundException::new);
        Response.ResponseBuilder res = Response.ok(CityDto.fromCity(uriInfo,ans));
        return ControllerUtils.getUnconditionalCacheResponseBuilder(res).build();
    }
}
