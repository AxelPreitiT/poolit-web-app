package ar.edu.itba.paw.models.trips;

import ar.edu.itba.paw.models.Car;
import ar.edu.itba.paw.models.City;
import ar.edu.itba.paw.models.User;

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
    @Enumerated(EnumType.ORDINAL)
    private DayOfWeek dayOfWeek;

    //TODO: test
//    @Basic(fetch = FetchType.LAZY) //No queremos que se busque siempre, solo cuando se muestra
//    @Formula(value = "SELECT max(passenger_count) FROM (SELECT count(passengers.user_id) as passenger_count" +
//            "FROM generate_series(queryStartDateTime,queryEndDateTime,'7 day'::interval) days LEFT OUTER JOIN passengers ON passengers.trip_id =tripId AND passengers.start_date<=days.days AND passengers.end_date>=days.days" +
//            "GROUP BY days.days) aux")
    private int occupiedSeats = 0;

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

    public void setQueryEndDateTime(LocalDateTime queryEndDateTime) {
        this.queryEndDateTime = queryEndDateTime;
    }

    public enum SortType{
        PRICE(),
        TIME(),
    }
}
