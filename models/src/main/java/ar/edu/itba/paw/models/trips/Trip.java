package ar.edu.itba.paw.models.trips;

import ar.edu.itba.paw.models.Car;
import ar.edu.itba.paw.models.City;
import ar.edu.itba.paw.models.Format;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.converters.DayOfWeekConverter;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

@Entity
@Table(name = "trips")
public class Trip {
    @Id
    @GeneratedValue(strategy =  GenerationType.SEQUENCE, generator ="trips_trip_id_seq" )
    @SequenceGenerator(sequenceName = "trips_trip_id_seq" , name = "trips_trip_id_seq", allocationSize = 1)
    @Column(name = "trip_id")
    private Long tripId;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "origin_city_id")
    private City originCity;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "destination_city_id")
    private City destinationCity;
    @Column(name = "origin_address")
    private String originAddress;
    @Column(name = "destination_address")
    private String destinationAddress;

    @Column(name = "start_date_time")
    private LocalDateTime startDateTime;
    @Column(name = "end_date_time")
    private LocalDateTime endDateTime;

    @Column(name = "start_date_time", insertable = false, updatable = false)
    private LocalDateTime queryStartDateTime;
    @Column(name = "end_date_time", insertable = false, updatable = false)
    private LocalDateTime queryEndDateTime;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "car_id")
    private Car car;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "driver_id")
    private User driver;
    @Column(name = "max_passengers")
    private int maxSeats;
    @Column(name = "price")
    private double price;
    @Convert(converter = DayOfWeekConverter.class)
    @Column(name = "day_of_week")
    private DayOfWeek dayOfWeek;

    @Column(name = "deleted", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean deleted;

    @Column(name = "last_occurrence", nullable = true)
    private LocalDateTime lastOccurrence;

    private transient int occupiedSeats = 0;

    protected Trip(){

    }
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
        this.occupiedSeats = occupiedSeats;
        this.price = price;
        this.queryStartDateTime = startDateTime;
        this.queryEndDateTime = endDateTime;
    }
    public Trip(City originCity, String originAddress, City destinationCity, String destinationAddress, LocalDateTime startDateTime, LocalDateTime endDateTime, int maxPassengers, User driver, Car car, double price) {
        this.originCity = originCity;
        this.originAddress = originAddress;
        this.destinationCity = destinationCity;
        this.destinationAddress = destinationAddress;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.dayOfWeek = startDateTime.getDayOfWeek();
        this.maxSeats = maxPassengers;
        this.driver = driver;
        this.car = car;
        this.price = price;
        this.queryStartDateTime = startDateTime;
        this.queryEndDateTime = endDateTime;
    }

    @Override
    public String toString() {
        return String.format("Trip { id: %d, originCity: '%s', originAddress: '%s', destinationCity: '%s', destinationAddress: '%s', isRecurrent: %b, dayOfWeek: '%s', startDateTime: '%s', endDateTime: '%s', queryStartDateTime: '%s', queryEndDateTime: '%s', maxSeats: %d, occupiedSeats: %d, price: $%f, carId: %d, driverId: %d }",
                tripId, originCity, originAddress, destinationCity, destinationAddress, isRecurrent(), dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH), startDateTime, endDateTime, queryStartDateTime, queryEndDateTime, maxSeats, occupiedSeats, price, car.getCarId(), driver.getUserId());
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
        return startDateTime.format(Format.getDateFormatter());
    }
    public String getEndDateString(){
        return endDateTime.format(Format.getDateFormatter());
    }
    public String getStartTimeString(){
        return startDateTime.format(Format.getTimeFormatter());
    }
    public String getEndTimeString(){
        return endDateTime.format(Format.getTimeFormatter());
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public boolean isRecurrent() {
        return !startDateTime.isEqual(endDateTime);
    }

    public int getFreeSeats() {
        return maxSeats - occupiedSeats;
    }

    public String getDayOfWeekString(){
        final String[] messageCodes = new String[]{
                "monday","tuesday","wednesday","thursday","friday","saturday","sunday"
        };
        return messageCodes[dayOfWeek.getValue()-1];
    }

    public int getTotalTrips(){
        return (Period.between(startDateTime.toLocalDate(),endDateTime.toLocalDate()).getDays())/7+1;
    }
    public double getTotalPrice(){
        return price*getTotalTrips();
    }

    public boolean getTripHasEnded(){
        return endDateTime.isBefore(LocalDateTime.now());
    }

    public LocalDateTime getQueryStartDateTime() {
        return queryStartDateTime;
    }

    public LocalDateTime getQueryEndDateTime() {
        return queryEndDateTime;
    }
    public String getQueryStartDateString(){
        return queryStartDateTime.format(Format.getDateFormatter());
    }
    public String getQueryEndDateString(){
        return queryEndDateTime.format(Format.getDateFormatter());
    }
    public String getQueryStartTimeString(){
        return queryStartDateTime.format(Format.getTimeFormatter());
    }
    public String getQueryEndTimeString(){
        return queryEndDateTime.format(Format.getTimeFormatter());
    }
    public int getQueryTotalTrips(){
        return (Period.between(queryStartDateTime.toLocalDate(),queryEndDateTime.toLocalDate()).getDays())/7+1;
    }

    public String getQueryTotalPrice() {
        return String.format("%.2f",price * getQueryTotalTrips());
    }

    public int getDecimalQueryTotalPrice() {
        return (int) Math.round(((price * getQueryTotalTrips())-getIntegerQueryTotalPrice())*100);
    }

    public int getIntegerQueryTotalPrice(){
        return Double.valueOf(price * getQueryTotalTrips()).intValue();
    }

    public boolean getQueryIsRecurrent(){
        return !queryStartDateTime.equals(queryEndDateTime);
    }

    public void setQueryStartDateTime(LocalDateTime queryStartDateTime) {
        this.queryStartDateTime = queryStartDateTime;
    }

    public LocalDateTime getLastOccurrence() {
        return lastOccurrence;
    }

    public void setLastOccurrence(LocalDateTime lastOccurrence) {
        this.lastOccurrence = lastOccurrence;
    }

    public boolean getDeleted(){
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public void setQueryEndDateTime(LocalDateTime queryEndDateTime) {
        this.queryEndDateTime = queryEndDateTime;
    }

    public void setOccupiedSeats(int occupiedSeats) {
        this.occupiedSeats = occupiedSeats;
    }

    public enum SortType{
        PRICE(),
        TIME(),
    }
}
