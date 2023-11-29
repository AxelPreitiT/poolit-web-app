package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.exceptions.CarNotFoundException;
import ar.edu.itba.paw.interfaces.exceptions.CityNotFoundException;
import ar.edu.itba.paw.interfaces.exceptions.TripNotFoundException;
import ar.edu.itba.paw.interfaces.exceptions.UserNotFoundException;
import ar.edu.itba.paw.interfaces.services.TripService;
import ar.edu.itba.paw.models.trips.Trip;
import ar.edu.itba.paw.webapp.controller.utils.ControllerUtils;
import ar.edu.itba.paw.webapp.controller.utils.UrlHolder;
import ar.edu.itba.paw.webapp.dto.input.CreateTripDto;
import ar.edu.itba.paw.webapp.dto.output.TripDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

@Path(UrlHolder.TRIPS_BASE)
public class TripController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TripController.class);

    private final TripService tripService;

    @Context
    private UriInfo uriInfo;

    @Inject
    @Autowired
    public TripController(final TripService tripService){
        this.tripService = tripService;
    }
    @POST
    public Response createTrip(@Valid CreateTripDto dto) throws UserNotFoundException, CarNotFoundException, CityNotFoundException {
        LOGGER.debug("POST request to create trip");
        final Trip trip = tripService.createTrip(dto.getOriginCityId(),dto.getOriginAddress(),dto.getDestinationCityId(),dto.getDestinationAddress(),dto.getCarId(),dto.getDate(),dto.getTime(), dto.getPrice(),dto.getMaxSeats(),dto.getLastDate(),dto.getTime());
        final URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(trip.getTripId())).build();
        return Response.created(uri).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") final long id) throws TripNotFoundException {
        LOGGER.debug("GET request for trip with id {}",id);
        final Trip trip = tripService.findById(id).orElseThrow(ControllerUtils.notFoundExceptionOf(TripNotFoundException::new));
        return Response.ok(TripDto.fromTrip(uriInfo,trip)).build();
    }

    @DELETE
    @Path("/{id}")
    //TODO: check creator of trip
    public Response deleteTrip(@PathParam("id") final long id) throws TripNotFoundException {
        LOGGER.debug("DELETE request for trip with id {}",id);
        tripService.deleteTrip(id);
        return Response.ok().build();
    }

//    @GET
////    Usar los parámetros de esto para el url de las recomendadas
////    Tambien para las creadas por un usuario o donde un usuario es participante
//    public Response getTrips(){
//
//    }
//


//
//
//
//    @POST
//    @Path("/{id}/passengers")
//    public Response addPassenger(@PathParam("id") final long id){
//        //TODO: preguntar si está bien tomar el contexto de auth acá
//    }
//
////    @Patch
//    @Path("/{id}/passengers")
////    TODO: check creator of trip
//    public Response acceptOrRejectPassenger(@PathParam("id") final long id){
//
//    }
//
//    @GET
//    @Path("/{id}/passengers")
////    TODO: ver cómo limitamos los pasajeros por cada usuario (en el url del trip se pasan los query params, pero está bien que eso cambie dependiendo del usuario?)
//    public Response getPassengers(@PathParam("id") final long id){
//
//    }


}
