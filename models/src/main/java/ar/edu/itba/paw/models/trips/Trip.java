package ar.edu.itba.paw.models.trips;

import ar.edu.itba.paw.models.Car;
import ar.edu.itba.paw.models.City;
import ar.edu.itba.paw.models.User;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Trip {
    private final City originCity, destinationCity;
    private final String originAddress, destinationAddress;
    private final LocalDateTime startDateTime, endDateTime;
    private final Car car;
    private final User driver;
    private final int maxSeats;
    private final DayOfWeek dayOfWeek;
    private final boolean isRecurrent;
    private final long tripId;
    private final int occupiedSeats;
    public Trip(final long tripId, final City originCity, final String originAddress, final City destinationCity, final String destinationAddress, final LocalDateTime startDateTime, final LocalDateTime endDateTime, final int maxSeats, final User driver, final Car car, final int occupiedSeats) {
        this.originCity = originCity;
        this.originAddress = originAddress;
        this.destinationCity = destinationCity;
        this.destinationAddress = destinationAddress;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.dayOfWeek = startDateTime.getDayOfWeek();
        this.maxSeats = maxSeats;
        this.tripId = tripId;
        this.driver = driver;
        this.car = car;
        this.isRecurrent = !startDateTime.isEqual(endDateTime);
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

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }
    public LocalDateTime getEndDateTime(){return endDateTime;}
    public int getMaxSeats() {
        return maxSeats;
    }

    public long getTripId() {
        return tripId;
    }

    public int getOccupiedSeats() {
        return occupiedSeats;
    }

    public String getStartDateString(){
        return startDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }
    public String getEndDateString(){
        return endDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }
    public String getStartTimeString(){
        return String.format("%02d:%02d",startDateTime.getHour(),startDateTime.getMinute());
    }
    public String getEndTimeString(){
        return String.format("%02d:%02d",endDateTime.getHour(),endDateTime.getMinute());
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public boolean isRecurrent() {
        return isRecurrent;
    }
}
