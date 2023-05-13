package ar.edu.itba.paw.models.trips;

import ar.edu.itba.paw.models.Car;
import ar.edu.itba.paw.models.City;
import ar.edu.itba.paw.models.User;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

public class Trip {
    private final City originCity, destinationCity;
    private final String originAddress, destinationAddress;
    private final LocalDateTime startDateTime, endDateTime, queryStartDateTime, queryEndDateTime;
    private final Car car;
    private final User driver;
    private final int maxSeats;
    private final double price;
    private final DayOfWeek dayOfWeek;
    private final boolean isRecurrent;
    private final long tripId;
    private final int occupiedSeats;
    public Trip(final long tripId, final City originCity, final String originAddress, final City destinationCity, final String destinationAddress, final LocalDateTime startDateTime, final LocalDateTime endDateTime, final int maxSeats, final User driver, final Car car, final int occupiedSeats,final double price, final LocalDateTime queryStartDateTime, final LocalDateTime queryEndDateTime) {
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
        this.price = price;
        this.queryStartDateTime = queryStartDateTime;
        this.queryEndDateTime = queryEndDateTime;
    }
    public Trip(final long tripId, final City originCity, final String originAddress, final City destinationCity, final String destinationAddress, final LocalDateTime startDateTime, final LocalDateTime endDateTime, final int maxSeats, final User driver, final Car car, final int occupiedSeats,final double price) {
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
        this.price = price;
        this.queryStartDateTime = startDateTime;
        this.queryEndDateTime = endDateTime;
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

    public double getPrice() {
        return price;
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

    public int getFreeSeats() {
        return maxSeats - occupiedSeats;
    }

    public String getDayOfWeekString(){
        return dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault());
    }

    public int getTotalTrips(){
        return (Period.between(startDateTime.toLocalDate(),endDateTime.toLocalDate()).getDays())/7+1;
    }
    public double getTotalPrice(){
        return price*getTotalTrips();
    }

    public LocalDateTime getQueryStartDateTime() {
        return queryStartDateTime;
    }

    public LocalDateTime getQueryEndDateTime() {
        return queryEndDateTime;
    }
    public String getQueryStartDateString(){
        return queryStartDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }
    public String getQueryEndDateString(){
        return queryEndDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }
    public String getQueryStartTimeString(){
        return String.format("%02d:%02d",queryStartDateTime.getHour(),queryStartDateTime.getMinute());
    }
    public String getQueryEndTimeString(){
        return String.format("%02d:%02d",queryEndDateTime.getHour(),queryEndDateTime.getMinute());
    }
    public int getQueryTotalTrips(){
        return (Period.between(queryStartDateTime.toLocalDate(),queryEndDateTime.toLocalDate()).getDays())/7+1;
    }
    public double getQueryTotalPrice() {
        return price * getQueryTotalTrips();
    }

    public boolean getQueryIsRecurrent(){
        return !queryStartDateTime.equals(queryEndDateTime);
    }

    public enum SortType{
        PRICE(),
        TIME(),
    }
}
