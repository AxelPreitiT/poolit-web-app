package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Car;
import ar.edu.itba.paw.models.City;
import ar.edu.itba.paw.models.PagedContent;
import ar.edu.itba.paw.models.trips.Trip;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.trips.TripInstance;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TripService {
    //
    Trip createTrip(final City originCity, final String originAddress, final City destinationCity, final String destinationAddress, final Car car, final String startDate, final String startTime,final double price, final int maxSeats, User driver, final String endDate, final String endTime);
    //
    Trip createTrip(final City originCity, final String originAddress, final City destinationCity, final String destinationAddress, final Car car, final String date, final String time,final double price, final int maxSeats, User driver);
    //
    boolean addPassenger(Trip trip, User passenger, LocalDateTime dateTime);
    //
    boolean addPassenger(Trip trip, User passenger, LocalDateTime startDateTime, LocalDateTime endDateTime);
    //
    boolean addPassenger(long tripId, User passenger, LocalDateTime startDateTime, LocalDateTime endDateTime);
    //
    boolean addPassenger(long tripId, User passenger, LocalDateTime dateTime);
    //
    Optional<Trip> findById(long id);
    //
    Optional<Trip> findById(long id, LocalDateTime start, LocalDateTime end);
    //
    Optional<Trip> findById(long id, LocalDateTime dateTime);
    //
    List<User> getPassengers(Trip trip, LocalDateTime dateTime);
    //
    List<User> getPassengers(TripInstance tripInstance);
    //
    PagedContent<TripInstance> getTripInstances(final Trip trip, int page, int pageSize);
    //
    PagedContent<TripInstance> getTripInstances(final Trip trip, int page, int pageSize, LocalDateTime start, LocalDateTime end);
    //
    PagedContent<Trip> getTripsCreatedByUser(final User user, int page, int pageSize);
    //
    PagedContent<Trip> getTripsWhereUserIsPassenger(final User user, int page, int pageSize);
    //
    PagedContent<Trip> getIncomingTrips(int page, int pageSize);
    boolean deleteTrip(final Trip trip);
    boolean addPassenger(Trip trip,User passenger, String startDate,String startTime, String endDate);
    boolean deleteTrip(int tripId);
    //
    PagedContent<Trip> getTripsByDateTimeAndOriginAndDestination(
            long origin_city_id, long destination_city_id, final String startDate,
            final String startTime, final String endDate, final String endTime,
            final int page, final int pageSize);
    //
    PagedContent<Trip> getTripsByDateTimeAndOriginAndDestinationAndPrice(
            long origin_city_id, long destination_city_id, final String startDate,
            final String startTime, final String endDate, final String endTime,
            double minPrice, double maxPrice,
            final int page, final int pageSize);
}
