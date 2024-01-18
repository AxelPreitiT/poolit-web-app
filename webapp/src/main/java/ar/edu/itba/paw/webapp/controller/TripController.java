package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.exceptions.*;
import ar.edu.itba.paw.interfaces.services.TripService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.PagedContent;
import ar.edu.itba.paw.models.Passenger;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.trips.Trip;
import ar.edu.itba.paw.webapp.controller.mediaType.VndType;
import ar.edu.itba.paw.webapp.controller.utils.ControllerUtils;
import ar.edu.itba.paw.webapp.controller.utils.UrlHolder;
import ar.edu.itba.paw.webapp.controller.utils.queryBeans.PassengersQuery;
import ar.edu.itba.paw.webapp.controller.utils.queryBeans.TripQuery;
import ar.edu.itba.paw.webapp.controller.utils.queryBeans.TripsQuery;
import ar.edu.itba.paw.webapp.dto.input.AddPassengerDto;
import ar.edu.itba.paw.webapp.dto.input.CreateTripDto;
import ar.edu.itba.paw.webapp.dto.input.PatchPassengerDto;
import ar.edu.itba.paw.webapp.dto.output.PassengerDto;
import ar.edu.itba.paw.webapp.dto.output.TripDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;


@Path(UrlHolder.TRIPS_BASE)
@Component
public class TripController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TripController.class);

    private final TripService tripService;

    private final UserService userService;

    @Context
    private UriInfo uriInfo;

    @Inject
    @Autowired
    public TripController(final TripService tripService, final UserService userService){
        this.tripService = tripService;
        this.userService = userService;
    }


    @GET
    @PreAuthorize("@authValidator.checkIfWantedIsSelf(#query.createdBy) and @authValidator.checkIfWantedIsSelf(#query.reservedBy) and @authValidator.checkIfWantedIsSelf(#query.recommendedFor)")
    @Produces(value = VndType.APPLICATION_TRIP)
    public Response getTrips(@Valid @BeanParam final TripsQuery query){
        PagedContent<Trip> ans = null;
        if(query.getCreatedBy()!=null){
            LOGGER.debug("GET request to trips created by user {}",query.getCreatedBy());
            ans = tripService.getTripsCreatedByUser(query.getCreatedBy(),query.isPast(),query.getPage(),query.getPageSize());
        }else if(query.getReservedBy()!=null){
            LOGGER.debug("GET request to trips reserved by user {}",query.getReservedBy());
            ans = tripService.getTripsWhereUserIsPassenger(query.getReservedBy(),query.isPast(),query.getPage(),query.getPageSize());
        }else if(query.getRecommendedFor()!=null) {
            //we use this instead of creating a URI in the userDTO with the fields to maintain the logic of recommendation in the trip service
            LOGGER.debug("GET request to trips recommended for user {}",query.getRecommendedFor());
            ans = tripService.getRecommendedTripsForUser(query.getRecommendedFor(),query.getPage(),query.getPageSize());
        }else{
            LOGGER.debug("GET request to find trips");
            ans = tripService.findTrips(query.getOriginCityId(), query.getDestinationCityId(),query.getStartDateTime(),query.getEndDateTime(),query.getMinPrice(),query.getMaxPrice(),query.getSortType(),query.isDescending(),query.getCarFeatures(),query.getPage(),query.getPageSize());
        }
        return ControllerUtils.getPaginatedResponse(uriInfo,ans,query.getPage(),TripDto::fromTrip,TripDto.class);
    }
    @POST
    @Consumes(value = VndType.APPLICATION_TRIP)
    public Response createTrip(@Valid CreateTripDto dto) throws UserNotFoundException, CarNotFoundException, CityNotFoundException {
        LOGGER.debug("POST request to create trip");
        final Trip trip = tripService.createTrip(dto.getOriginCityId(),dto.getOriginAddress(),dto.getDestinationCityId(),dto.getDestinationAddress(),dto.getCarId(),dto.getDate(),dto.getTime(), dto.getPrice(),dto.getMaxSeats(),dto.getLastDate(),dto.getTime());
        final URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(trip.getTripId())).build();
        return Response.created(uri).build();
    }

    @GET
    @Path("/{id}")
    @Produces(value = VndType.APPLICATION_TRIP)
    public Response getById(@PathParam("id") final long id,
                            @Valid @BeanParam final TripQuery query) throws TripNotFoundException, UserNotFoundException {
        final Trip trip;
        if(query.getStartDateTime()!=null || query.getEndDateTime()!=null){
            LOGGER.debug("GET request for trip with id {} from {} to {}",id,query.getStartDateTime(),query.getEndDateTime());
            trip = tripService.findById(id,query.getStartDateTime(),query.getEndDateTime()).orElseThrow(ControllerUtils.notFoundExceptionOf(TripNotFoundException::new));
        }else {
            LOGGER.debug("GET request for trip with id {}",id);
            trip = tripService.findById(id).orElseThrow(ControllerUtils.notFoundExceptionOf(TripNotFoundException::new));
        }
        return Response.ok(TripDto.fromTrip(uriInfo,trip)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteTrip(@PathParam("id") final long id) throws TripNotFoundException {
        LOGGER.debug("DELETE request for trip with id {}",id);
        tripService.deleteTrip(id);
        return Response.ok().build();
    }

    @GET
    @Path("/{id}"+UrlHolder.TRIPS_PASSENGERS)
    @PreAuthorize("@authValidator.checkIfUserCanSearchPapssengers(#id,#passengersQuery.startDateTime,#passengersQuery.endDateTime,#passengersQuery.passengerState)")
    @Produces(value = VndType.APPLICATION_TRIP_PASSENGER)
    public Response getPassengers(@PathParam("id") final long id,
                                  @Valid @BeanParam final PassengersQuery passengersQuery) throws CustomException {
        LOGGER.debug("GET request for passengers from trip {}",id);
        PagedContent<Passenger> ans = tripService.getPassengers(id,passengersQuery.getStartDateTime(),passengersQuery.getEndDateTime(), passengersQuery.getPassengerState(),passengersQuery.getPage(),passengersQuery.getPageSize());
        return ControllerUtils.getPaginatedResponse(uriInfo,ans,passengersQuery.getPage(),PassengerDto::fromPassenger,PassengerDto.class);
    }

    @POST
    @Path("/{id}"+UrlHolder.TRIPS_PASSENGERS)
    @Consumes(value = VndType.APPLICATION_TRIP_PASSENGER)
    public Response addPassenger(@PathParam("id") final long id, @Valid AddPassengerDto dto) throws UserNotFoundException, TripAlreadyStartedException, TripNotFoundException {
        LOGGER.debug("POST request to add passenger for trip {}",id);
        Passenger ans = tripService.addCurrentUserAsPassenger(id,dto.getStartDate(),dto.getStartTime(),dto.getEndDate());
        final URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(ans.getUserId())).build();
        //Los pasajeros se acceden en /trips/{tripId}/passengers/{userId}
        return Response.created(uri).build();
    }

    //TODO: agregar permisos!
    @GET
    @Path("/{id}"+UrlHolder.TRIPS_PASSENGERS+"/{userId}")
    @Produces(value = VndType.APPLICATION_TRIP_PASSENGER)
    //si no ver como limitar el estado para los otros
    public Response getPassenger(@PathParam("id") final long id, @PathParam("userId") final long userId) throws UserNotFoundException, PassengerNotFoundException {
        LOGGER.debug("GET request to get passenger {} from trip {}",userId,id);
        final Passenger passenger = tripService.getPassenger(id,userId).orElseThrow(ControllerUtils.notFoundExceptionOf(PassengerNotFoundException::new));
        return Response.ok(PassengerDto.fromPassenger(uriInfo,passenger)).build();
    }

//    https://www.rfc-editor.org/rfc/rfc5789
    @PATCH
    @Path("/{id}"+UrlHolder.TRIPS_PASSENGERS+"/{userId}")
    @Consumes(value = VndType.APPLICATION_TRIP_PASSENGER_STATE)
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


}
