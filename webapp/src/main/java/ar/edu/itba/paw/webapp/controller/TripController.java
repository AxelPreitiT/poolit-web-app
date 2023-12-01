package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.exceptions.*;
import ar.edu.itba.paw.interfaces.services.TripService;
import ar.edu.itba.paw.models.Passenger;
import ar.edu.itba.paw.models.trips.Trip;
import ar.edu.itba.paw.webapp.controller.utils.ControllerUtils;
import ar.edu.itba.paw.webapp.controller.utils.UrlHolder;
import ar.edu.itba.paw.webapp.dto.input.AddPassengerDto;
import ar.edu.itba.paw.webapp.dto.input.CreateTripDto;
import ar.edu.itba.paw.webapp.dto.input.PatchPassengerDto;
import ar.edu.itba.paw.webapp.dto.output.PassengerDto;
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
import java.util.Optional;


//TODO: agregar media Types
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
    public Response deleteTrip(@PathParam("id") final long id) throws TripNotFoundException {
        LOGGER.debug("DELETE request for trip with id {}",id);
        tripService.deleteTrip(id);
        return Response.ok().build();
    }

    @POST
    @Path("/{id}"+UrlHolder.TRIPS_PASSENGERS)
    public Response addPassenger(@PathParam("id") final long id, @Valid AddPassengerDto dto) throws UserNotFoundException, TripAlreadyStartedException, TripNotFoundException {
        LOGGER.debug("POST request to add passenger for trip {}",id);
        //TODO: preguntar si está bien tomar el contexto de auth acá
        Passenger ans = tripService.addCurrentUserAsPassenger(id,dto.getStartDate(),dto.getStartTime(),dto.getEndDate());
        final URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(ans.getUserId())).build();
        //Los pasajeros se acceden en /trips/{tripId}/passengers/{userId}
        return Response.created(uri).build();
    }

    //TODO: preguntar si esta bien que el userId se use como identificador del pasajero en el viaje
    //Es como un id de la instancia de pasajero, ya que el usuario solo puede aparecer una vez como pasajero
    @GET
    @Path("/{id}"+UrlHolder.TRIPS_PASSENGERS+"/{userId}")
//    TODO: que solo sea el usuario o el creador
    //si no ver como limitar el estado para los otros
    public Response getPassenger(@PathParam("id") final long id, @PathParam("userId") final long userId) throws UserNotFoundException {
        LOGGER.debug("GET request to get passenger {} from trip {}",userId,id);
        final Optional<Passenger> passenger = tripService.getPassenger(id,userId);
        if(!passenger.isPresent()){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(PassengerDto.fromPassenger(uriInfo,passenger.get())).build();
    }

//    https://www.rfc-editor.org/rfc/rfc5789
    @PATCH
    @Path("/{id}"+UrlHolder.TRIPS_PASSENGERS+"/{userId}")
    public Response acceptOrRejectPassenger(@PathParam("id") final long id, @PathParam("userId") final long userId, @Valid PatchPassengerDto dto) throws UserNotFoundException, PassengerAlreadyProcessedException, PassengerNotFoundException, NotAvailableSeatsException {
        LOGGER.debug("PATCH request to passenger {} from trip {}",userId,id);
        tripService.acceptOrRejectPassenger(id,userId,dto.getPassengerState());
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}"+UrlHolder.TRIPS_PASSENGERS+"/{userId}")
    public Response cancelTrip(@PathParam("id") final long id,@PathParam("userId") final long userId) throws UserNotFoundException, TripNotFoundException, PassengerNotFoundException {
        LOGGER.debug("DELETE request to passenger {} from trip {}",userId,id);
        tripService.removePassenger(id,userId);
        return Response.noContent().build();
    }


//    @GET
////    Usar los parámetros de esto para el url de las recomendadas
////    Tambien para las creadas por un usuario o donde un usuario es participante
//    public Response getTrips(){
//
//    }
//
//
//    @GET
//    @Path("/{id}/passengers")
////    TODO: ver cómo limitamos los pasajeros por cada usuario (en el url del trip se pasan los query params, pero está bien que eso cambie dependiendo del usuario?)
//    public Response getPassengers(@PathParam("id") final long id){
//
//    }


}
