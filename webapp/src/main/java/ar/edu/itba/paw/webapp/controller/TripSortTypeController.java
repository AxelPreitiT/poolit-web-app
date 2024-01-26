package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.TripSortTypeService;
import ar.edu.itba.paw.models.trips.Trip;
import ar.edu.itba.paw.webapp.controller.mediaType.VndType;
import ar.edu.itba.paw.webapp.controller.utils.ControllerUtils;
import ar.edu.itba.paw.webapp.controller.utils.UrlHolder;
import ar.edu.itba.paw.webapp.dto.output.TripSortTypeDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

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
import java.util.Optional;
import java.util.stream.Collectors;

@Path(UrlHolder.TRIP_SORT_TYPE_BASE)
@Component
public class TripSortTypeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TripSortTypeController.class);

    private final TripSortTypeService tripSortTypeService;

    private final MessageSource messageSource;

    @Context
    private UriInfo uriInfo;

    @Autowired
    @Inject
    public TripSortTypeController(final TripSortTypeService tripSortTypeService, final MessageSource messageSource) {
        this.tripSortTypeService = tripSortTypeService;
        this.messageSource = messageSource;
    }

    @GET
    @Produces(value = VndType.APPLICATION_TRIP_SORT_TYPE)
    public Response getTripSortTypes() {
        LOGGER.debug("GET request for all sort types");
        List<Trip.SortType> ans = tripSortTypeService.getSortTypes();
        Response.ResponseBuilder res = Response.ok(new GenericEntity<List<TripSortTypeDto>>(ans.stream().map(e -> TripSortTypeDto.fromTripSortType(uriInfo, e, messageSource)).collect(Collectors.toList())){});
        return ControllerUtils.getUnconditionalCacheResponseBuilder(res).build();
    }

    @GET
    @Path("{id}")
    @Produces(value = VndType.APPLICATION_TRIP_SORT_TYPE)
    public Response getTripSortType(@PathParam("id") final String id) {
        LOGGER.debug("GET request for sort type with id {}", id);
        final Optional<Trip.SortType> sortType = tripSortTypeService.findById(id);
        if (!sortType.isPresent()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        Response.ResponseBuilder res = Response.ok(TripSortTypeDto.fromTripSortType(uriInfo, sortType.get(), messageSource));
        return ControllerUtils.getUnconditionalCacheResponseBuilder(res).build();
    }
}

