package ar.edu.itba.paw.models;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Trip {
    private final City originCity, destinationCity;
    private final String originAddress, destinationAddress;
    private final LocalDateTime dateTime;
    private final Car car;
    private final User driver;
    private final int maxSeats;
    private final long tripId;
    private final int occupiedSeats;
    public Trip(final long tripId,final City originCity, final String originAddress, final City destinationCity, final String destinationAddress, final LocalDateTime dateTime, final int maxSeats, final User driver, final Car car, final int occupiedSeats) {
        this.originCity = originCity;
        this.originAddress = originAddress;
        this.destinationCity = destinationCity;
        this.destinationAddress = destinationAddress;
        this.dateTime = dateTime;
        this.maxSeats = maxSeats;
        this.tripId = tripId;
        this.driver = driver;
        this.car = car;
        this.occupiedSeats = occupiedSeats;
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

    public LocalDateTime getDateTime() {
        return dateTime;
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

}
