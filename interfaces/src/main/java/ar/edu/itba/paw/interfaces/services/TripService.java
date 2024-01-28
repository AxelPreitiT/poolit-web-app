package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.interfaces.exceptions.*;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.trips.Trip;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface TripService {
    Trip createTrip(final long originCityId, final String originAddress, final long destinationCityId, final String destinationAddress, final long carId, final LocalDate startDate, final LocalTime startTime,final BigDecimal price, final int maxSeats, final LocalDate endDate, final LocalTime endTime)  throws UserNotFoundException, CityNotFoundException, CarNotFoundException;

    //TODO: test
    Passenger addCurrentUserAsPassenger(final long tripId, LocalDate startDate, LocalTime startTime, LocalDate endDate) throws TripAlreadyStartedException, TripNotFoundException, UserNotFoundException, NotAvailableSeatsException;

    //TODO: delete
//    boolean addCurrentUser(final long trip, String startDate, String startTime, String endDate) throws TripAlreadyStartedException, UserNotFoundException, TripNotFoundException;

//    boolean removeCurrentUserAsPassenger(final long tripId) throws UserNotFoundException, TripNotFoundException, PassengerNotFoundException;

    boolean removePassenger(final long tripId, final long userId) throws UserNotFoundException, TripNotFoundException, PassengerNotFoundException;

    Optional<Trip> findById(long id);

    Optional<Trip> findById(long id, LocalDateTime start, LocalDateTime end);

//    Optional<Trip> findById(long id, LocalDateTime dateTime);

//    Optional<Trip> findById(long id, String startDate, String startTime, String endDate);

    boolean userIsDriver(final long tripId, final User user);

    boolean userIsPassenger(final long tripId, final User user);

    double getTotalTripEarnings(final long tripId) throws TripNotFoundException;
    boolean userIsAcceptedPassengerOfDriver(final User user, final User driver);
    Optional<Passenger> getPassenger(final long tripId, final User user);

    Optional<Passenger> getPassenger(final long tripId, final long userId) throws UserNotFoundException;
    Optional<Passenger> getPassenger(final Trip trip, final User user);

//    List<Passenger> getPassengers(Trip trip, LocalDateTime dateTime);

    PagedContent<Passenger> getPassengers(final long tripId, final LocalDateTime startDateTime, final LocalDateTime endDateTime, final Passenger.PassengerState passengerState,final int page, final int pageSize) throws TripNotFoundException;

//    List<Passenger> getPassengers(TripInstance tripInstance);

//    List<Passenger> getPassengers(Trip trip);
//    List<Passenger> getAcceptedPassengers(Trip trip, LocalDateTime startDate, LocalDateTime endDate);

//    PagedContent<Passenger> getPassengersPaged(Trip trip, String passengerState, int page, int pageSize);

//    PagedContent<TripInstance> getTripInstances(final Trip trip, int page, int pageSize);

//    PagedContent<TripInstance> getTripInstances(final Trip trip, int page, int pageSize, LocalDateTime start, LocalDateTime end);

    PagedContent<Trip> getTripsWhereUserIsPassenger(final long userId, final boolean pastTrips, int page, int pageSize);
    PagedContent<Trip> getTripsCreatedByUser(final long userId, final boolean pastTrips, int page, int pageSize);

    PagedContent<Trip> getTripsCreatedByUserFuture(final User user, int page, int pageSize);
//    PagedContent<Trip> getTripsCreatedByCurrentUserFuture(int page, int pageSize) throws UserNotFoundException;

//    PagedContent<Trip> getTripsCreatedByCurrentUserPast(int page, int pageSize) throws UserNotFoundException;
//    PagedContent<Trip> getTripsCreatedByUserPast(User user,int page, int pageSize);

//    PagedContent<Trip> getTripsCreatedByCurrentUser(int page, int pageSize) throws UserNotFoundException;
//    PagedContent<Trip> getTripsCreatedByUser(User user, int page, int pageSize);

//    PagedContent<Trip> getTripsWhereCurrentUserIsPassengerFuture(int page, int pageSize) throws UserNotFoundException;

    PagedContent<Trip> getTripsWhereUserIsPassengerFuture(User user, int page, int pageSize);

//    PagedContent<Trip> getTripsWhereCurrentUserIsPassengerPast(int page, int pageSize) throws UserNotFoundException;

//    PagedContent<Trip> getTripsWhereUserIsPassengerPast(User user, int page, int pageSize);
    boolean deleteTrip(final long tripId) throws TripNotFoundException;

//    List<Passenger> getPassengersRecurrent(Trip trip, LocalDateTime startDate, LocalDateTime endDate);

    //TODO: delete
//    PagedContent<Trip> getRecommendedTripsForCurrentUser(int page, int pageSize);

    PagedContent<Trip> getRecommendedTripsForUser(final long userId, final int page, final int pageSize);

    //TODO: delete
//    PagedContent<Trip> getTripsByDateTimeAndOriginAndDestinationAndPrice(
//            long originCityId, long destinationCityId, final LocalDate startDate,
//            final LocalTime startTime, final LocalDate endDate, final LocalTime endTime,
//            final BigDecimal minPrice, final BigDecimal maxPrice, final String sortType, final boolean descending,
//            final List<FeatureCar> carFeatures, final int page, final int pageSize);

    PagedContent<Trip> findTrips(
            long originCityId, long destinationCityId, final LocalDateTime startDateTime,
            final LocalDateTime endDateTimeValue, final BigDecimal minPriceValue, final BigDecimal maxPriceValue,
            final Trip.SortType sortType, final boolean descending, final List<FeatureCar> carFeaturesValue,
            final int page, final int pageSize);

//    boolean acceptPassenger(final long tripId, final long userId) throws NotAvailableSeatsException;

//    boolean rejectPassenger(final long tripId, final long userId);

    boolean acceptOrRejectPassenger(final long tripId, final long userId, Passenger.PassengerState passengerState) throws UserNotFoundException, PassengerNotFoundException, PassengerAlreadyProcessedException, NotAvailableSeatsException;

    boolean checkIfUserCanGetPassengers(final long tripId, final User user, final LocalDateTime startDateTime, final LocalDateTime endDateTime, Passenger.PassengerState passengerState) throws TripNotFoundException;

}
