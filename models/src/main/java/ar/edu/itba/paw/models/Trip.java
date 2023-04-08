package ar.edu.itba.paw.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Trip {
    private static int nextId = 0;
    private final City originCity, destinationCity;
    private final String originAddress, destinationAddress, date, time, carInfo;
    private final User driver;
    private final int seats;
    private final long id;
    private int seatsOccupied=0;
    private final List<User> passengers = new ArrayList<>();
    public Trip(final City originCity, final String originAddress, final City destinationCity, final String destinationAddress,final String carInfo, final String date, final String time, final int seats, User driver) {
        this.originCity = originCity;
        this.originAddress = originAddress;
        this.destinationCity = destinationCity;
        this.destinationAddress = destinationAddress;
        this.date = date;
        this.time = time;
        this.carInfo = carInfo;
        this.seats = seats;
        this.driver = driver;
        this.id = nextId++;
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

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public long getId() {
        return id;
    }

    public User getDriver() {
        return driver;
    }

    public List<User> getPassengers() {
        return passengers;
    }

    public boolean addPassenger(User passenger){
        if(seatsOccupied>=seats){return false;}
        seatsOccupied++;
        return passengers.add(passenger);
    }
    public int getSeats() {
        return seats;
    }

    public int getFreeSeats() {
        return seats - seatsOccupied;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trip trip = (Trip) o;
        return id == trip.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String getCarInfo() {
        return carInfo;
    }
}
