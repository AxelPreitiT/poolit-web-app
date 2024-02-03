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

    Passenger addCurrentUserAsPassenger(final long tripId, LocalDate startDate,LocalDate endDate) throws TripAlreadyStartedException, TripNotFoundException, UserNotFoundException, NotAvailableSeatsException;

    int getTripSeatCount(final long tripId, LocalDateTime startDateTime, LocalDateTime endDateTime) throws TripNotFoundException;

    boolean removePassenger(final long tripId, final long userId) throws UserNotFoundException, TripNotFoundException, PassengerNotFoundException;

    Optional<Trip> findById(long id);

    Optional<Trip> findById(long id, LocalDateTime start, LocalDateTime end);

    boolean userIsDriver(final long tripId, final User user);

    boolean userIsPassenger(final long tripId, final User user);

    double getTotalTripEarnings(final long tripId) throws TripNotFoundException;
    boolean userIsAcceptedPassengerOfDriver(final User user, final User driver);
    Optional<Passenger> getPassenger(final long tripId, final User user);

    Optional<Passenger> getPassenger(final long tripId, final long userId) /*throws UserNotFoundException*/;
    Optional<Passenger> getPassenger(final Trip trip, final User user);

    PagedContent<Passenger> getPassengers(final long tripId, final LocalDateTime startDateTime, final LocalDateTime endDateTime, final Passenger.PassengerState passengerState,final List<Integer> excludedList,final int page, final int pageSize) throws TripNotFoundException;

    PagedContent<Trip> getTripsWhereUserIsPassenger(final long userId, final boolean pastTrips, int page, int pageSize);
    PagedContent<Trip> getTripsCreatedByUser(final long userId, final boolean pastTrips, int page, int pageSize);

    PagedContent<Trip> getTripsCreatedByUserFuture(final User user, int page, int pageSize);
    PagedContent<Trip> getTripsWhereUserIsPassengerFuture(User user, int page, int pageSize);

    boolean deleteTrip(final long tripId) throws TripNotFoundException;

    PagedContent<Trip> getRecommendedTripsForUser(final long userId, final int page, final int pageSize);

    PagedContent<Trip> findTrips(
            long originCityId, long destinationCityId, final LocalDateTime startDateTime,
            final LocalDateTime endDateTimeValue, final BigDecimal minPriceValue, final BigDecimal maxPriceValue,
            final Trip.SortType sortType, final boolean descending, final List<FeatureCar> carFeaturesValue,
            final int page, final int pageSize);

    boolean acceptOrRejectPassenger(final long tripId, final long userId, Passenger.PassengerState passengerState) throws UserNotFoundException, PassengerNotFoundException, PassengerAlreadyProcessedException, NotAvailableSeatsException;

    boolean checkIfUserCanGetPassengers(final long tripId, final User user, final LocalDateTime startDateTime, final LocalDateTime endDateTime, Passenger.PassengerState passengerState) throws TripNotFoundException;

}
