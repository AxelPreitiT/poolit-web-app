package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.exceptions.*;
import ar.edu.itba.paw.interfaces.services.TripService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.FeatureCar;
import ar.edu.itba.paw.models.PagedContent;
import ar.edu.itba.paw.models.Passenger;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.trips.Trip;
import ar.edu.itba.paw.webapp.controller.utils.ControllerUtils;
import ar.edu.itba.paw.webapp.controller.utils.UrlHolder;
import ar.edu.itba.paw.webapp.dto.input.AddPassengerDto;
import ar.edu.itba.paw.webapp.dto.input.CreateTripDto;
import ar.edu.itba.paw.webapp.dto.input.PatchPassengerDto;
import ar.edu.itba.paw.webapp.dto.output.PassengerDto;
import ar.edu.itba.paw.webapp.dto.output.TripDto;
import ar.edu.itba.paw.webapp.dto.validation.annotations.CityId;
import ar.edu.itba.paw.webapp.dto.validation.annotations.MinField;
import ar.edu.itba.paw.webapp.dto.validation.annotations.Page;
import ar.edu.itba.paw.webapp.dto.validation.annotations.PageSize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


//TODO: agregar media Types
@Path(UrlHolder.TRIPS_BASE)
@Component
public class TripController {

    private static final String PAGE_SIZE = "10";
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
    @PreAuthorize("@authValidator.checkIfWantedIsSelf(#creatorUserId) and @authValidator.checkIfWantedIsSelf(#passengerUserId) and @authValidator.checkIfWantedIsSelf(#recommendedUserId)")
    public Response getTrips(@QueryParam("originCityId") @Valid @CityId final Integer originCityId,
                             @QueryParam("destinationCityId") @Valid @CityId final Integer destinationCityId,
                             @QueryParam("startDateTime") @Valid @NotNull() final LocalDateTime startDateTime,
                             @QueryParam("endDateTime") final LocalDateTime endDateTime,
                             @QueryParam("minPrice") @Valid @MinField(value = 0, fieldName = "minPrice") final BigDecimal minPrice,
                             @QueryParam("maxPrice") @Valid @MinField(value = 0, fieldName = "maxPrice") final BigDecimal maxPrice,
                             @QueryParam("carFeatures") final List<FeatureCar> carFeatures,
                             @QueryParam("sortType") @DefaultValue("PRICE") final Trip.SortType sortType,
                             @QueryParam("createdBy") final Integer creatorUserId,
                             @QueryParam("reservedBy") final Integer passengerUserId,
                             @QueryParam("recommendedFor") final Integer recommendedUserId,
                             @QueryParam("past") final boolean pastTrips,
                             @QueryParam("descending") final boolean descending,
                             @QueryParam(ControllerUtils.PAGE_QUERY_PARAM) @DefaultValue("0") @Valid @Page final int page,
                             @QueryParam(ControllerUtils.PAGE_SIZE_QUERY_PARAM) @DefaultValue(PAGE_SIZE) @Valid @PageSize final int pageSize
                             ){
        PagedContent<Trip> ans = null;
        if(creatorUserId!=null){
            LOGGER.debug("GET request to trips created by user {}",creatorUserId);
            ans = tripService.getTripsCreatedByUser(creatorUserId,pastTrips,page,pageSize);
        }else if(passengerUserId!=null){
            LOGGER.debug("GET request to trips reserved by user {}",passengerUserId);
            ans = tripService.getTripsWhereUserIsPassenger(passengerUserId,pastTrips,page,pageSize);
        }else if(recommendedUserId!=null) {
            //we use this instead of creating a URI in the userDTO with the fields to maintain the logic of recommendation in the trip service
            LOGGER.debug("GET request to trips recommended for user {}",recommendedUserId);
            ans = tripService.getRecommendedTripsForUser(recommendedUserId,page,pageSize);
        }else{
            LOGGER.debug("GET request to find trips");
            ans = tripService.findTrips(originCityId,destinationCityId,startDateTime,endDateTime,minPrice,maxPrice,sortType,descending,carFeatures,page,pageSize);
        }
        return ControllerUtils.getPaginatedResponse(uriInfo,ans,page,TripDto::fromTrip,TripDto.class);
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
    public Response getById(@PathParam("id") final long id,
                            @QueryParam("startDateTime") final LocalDateTime startDateTime,
                            @QueryParam("endDateTime") final LocalDateTime endDateTime) throws TripNotFoundException, UserNotFoundException {
        final Trip trip;
        if(startDateTime!=null || endDateTime!=null){
            if(startDateTime==null || endDateTime==null){
                throw new IllegalArgumentException();
            }
            LOGGER.debug("GET request for trip with id {} from {} to {}",id,startDateTime,endDateTime);
            trip = tripService.findById(id,startDateTime,endDateTime).orElseThrow(ControllerUtils.notFoundExceptionOf(TripNotFoundException::new));
        }else {
            LOGGER.debug("GET request for trip with id {}",id);
            trip = tripService.findById(id).orElseThrow(ControllerUtils.notFoundExceptionOf(TripNotFoundException::new));
        }
        final User user = userService.getCurrentUser().orElse(null);
        final Passenger currentUserPassenger = user!=null?tripService.getPassenger(id,user.getUserId()).orElse(null):null;
        return Response.ok(TripDto.fromTrip(uriInfo,trip,user,currentUserPassenger)).build();
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
    @PreAuthorize("@authValidator.checkIfUserCanSearchPassengers(#id,#startDateTime,#endDateTime,#passengerState)")
    public Response getPassengers(@PathParam("id") final long id,
                                  @QueryParam("startDateTime") final LocalDateTime startDateTime,
                                  @QueryParam("endDateTime") final LocalDateTime endDateTime,
                                  @QueryParam("passengerState") final Passenger.PassengerState passengerState,
                                  @QueryParam(ControllerUtils.PAGE_QUERY_PARAM) @DefaultValue("0") @Valid @Page final int page,
                                  @QueryParam(ControllerUtils.PAGE_SIZE_QUERY_PARAM) @DefaultValue(PAGE_SIZE) @Valid @PageSize final int pageSize) throws CustomException {
        LOGGER.debug("GET request for passengers from trip {}",id);
        if((startDateTime==null && endDateTime!=null)||(startDateTime!=null && endDateTime==null)){
            //TODO: revisar si esta bien instanciar esto aca!
            throw new CustomException("exceptions.startDateTime_with_enDateTime",Response.Status.BAD_REQUEST.getStatusCode());
        }
        PagedContent<Passenger> ans = tripService.getPassengers(id,startDateTime,endDateTime, passengerState,page,pageSize);
        return ControllerUtils.getPaginatedResponse(uriInfo,ans,page,PassengerDto::fromPassenger,PassengerDto.class);
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

    @GET
    @Path("/{id}"+UrlHolder.TRIPS_PASSENGERS+"/{userId}")
    //si no ver como limitar el estado para los otros
    public Response getPassenger(@PathParam("id") final long id, @PathParam("userId") final long userId) throws UserNotFoundException, PassengerNotFoundException {
        LOGGER.debug("GET request to get passenger {} from trip {}",userId,id);
        final Passenger passenger = tripService.getPassenger(id,userId).orElseThrow(ControllerUtils.notFoundExceptionOf(PassengerNotFoundException::new));
        return Response.ok(PassengerDto.fromPassenger(uriInfo,passenger)).build();
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


}
