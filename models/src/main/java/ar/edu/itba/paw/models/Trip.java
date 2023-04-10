package ar.edu.itba.paw.models;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Trip {
    private final City originCity, destinationCity;
    private final String originAddress, destinationAddress;
    private final Date date;
    private final Time time;
    private final Car car;
    private final User driver;
    private final int maxSeats;
    private final long tripId;
    private int occupiedSeats;
    private final List<User> passengers;
    public Trip(final long tripId,final City originCity, final String originAddress, final City destinationCity, final String destinationAddress, final Date date, final Time time, final int maxSeats, User driver, Car car, List<User> passengers) {
        this.originCity = originCity;
        this.originAddress = originAddress;
        this.destinationCity = destinationCity;
        this.destinationAddress = destinationAddress;
        this.date = date;
        this.time = time;
        this.maxSeats = maxSeats;
        this.tripId = tripId;
        this.driver = driver;
        this.car = car;
        this.passengers = passengers;
        this.occupiedSeats = passengers.size();
    }
    public City getOriginCity() {
        return originCity;
    }

    public String getOriginAddress() {
        return originAddress;
    }

    public City getDestinationCity() {
        return destinationCity;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }
    public User getDriver() {
        return driver;
    }

    public Car getCar(){
        return car;
    }

    public Date getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }

    public int getMaxSeats() {
        return maxSeats;
    }

    public long getTripId() {
        return tripId;
    }

    public int getOccupiedSeats() {
        return occupiedSeats;
    }

    public List<User> getPassengers() {
        return passengers;
    }
}
