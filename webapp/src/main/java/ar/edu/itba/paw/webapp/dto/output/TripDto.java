package ar.edu.itba.paw.webapp.dto.output;

import ar.edu.itba.paw.models.Passenger;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.trips.Trip;
import ar.edu.itba.paw.webapp.controller.utils.UrlHolder;

import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.time.LocalDateTime;

public class TripDto {

//    private long originCityId;
    private URI originCityUri;
    private String originAddress;
//    private long destinationCityId;
    private URI destinationCityUri;
    private String destinationAddress;
//    private long carId;
    private URI carUri;
    private URI driverUri;
    private long maxSeats;
    private double price;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    //URI for driver
    private URI passengersUri;
    //URI for passenger
    private URI passengerUri;

//    TODO: ver donde los pongo!
//    private double driverRating;
//    private double carRating;
    private LocalDateTime queryStartDateTime;
    private LocalDateTime queryEndDateTime;
    private URI selfUri;



    public static TripDto fromTrip(final UriInfo uriInfo, final Trip trip, final User currentUser, final Passenger currentUserPassenger){
        //TODO: revisar esto para mandar algo distinto cuando está el usuario logueado
        final TripDto ans = TripDto.fromTrip(uriInfo,trip);
        if(currentUser != null && currentUser.getUserId() == trip.getDriver().getUserId()){
            //currentUser is driver, it can access all the passengers
            ans.passengersUri = uriInfo.getBaseUriBuilder().path(UrlHolder.TRIPS_BASE).path(String.valueOf(trip.getTripId())).path(UrlHolder.TRIPS_PASSENGERS).build();
        }else if(currentUserPassenger!=null){
            ans.passengerUri = uriInfo.getBaseUriBuilder().path(UrlHolder.TRIPS_BASE).path(String.valueOf(trip.getTripId())).path(UrlHolder.TRIPS_PASSENGERS).path(String.valueOf(currentUserPassenger.getUser().getUserId())).build();
        }
        return ans;
    }
    public static TripDto fromTrip(final UriInfo uriInfo, final Trip trip){
        final TripDto ans = new TripDto();
        ans.originAddress = trip.getOriginAddress();
        ans.destinationAddress = trip.getDestinationAddress();
        ans.maxSeats = trip.getMaxSeats();
        ans.price = trip.getPrice();
        ans.startDateTime = trip.getStartDateTime();
        ans.endDateTime = trip.getEndDateTime();
        ans.queryEndDateTime = trip.getQueryStartDateTime();
        ans.queryEndDateTime = trip.getQueryEndDateTime();
        ans.driverUri = uriInfo.getBaseUriBuilder().path(UrlHolder.USER_BASE).path(String.valueOf(trip.getDriver().getUserId())).build();
        ans.carUri = uriInfo.getBaseUriBuilder().path(UrlHolder.CAR_BASE).path(String.valueOf(trip.getCar().getCarId())).build();
        ans.originCityUri = uriInfo.getBaseUriBuilder().path(UrlHolder.CITY_BASE).path(String.valueOf(trip.getOriginCity().getId())).build();
        ans.destinationCityUri = uriInfo.getBaseUriBuilder().path(UrlHolder.CITY_BASE).path(String.valueOf(trip.getDestinationCity().getId())).build();
        //TODO: ver si pongo los límites de búsqueda en la URI
        ans.selfUri = uriInfo.getBaseUriBuilder().path(UrlHolder.TRIPS_BASE).path(String.valueOf(trip.getTripId())).build();
        return ans;
    }

    public URI getOriginCityUri() {
        return originCityUri;
    }

    public void setOriginCityUri(URI originCityUri) {
        this.originCityUri = originCityUri;
    }

    public String getOriginAddress() {
        return originAddress;
    }

    public void setOriginAddress(String originAddress) {
        this.originAddress = originAddress;
    }

    public URI getDestinationCityUri() {
        return destinationCityUri;
    }

    public void setDestinationCityUri(URI destinationCityUri) {
        this.destinationCityUri = destinationCityUri;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public URI getCarUri() {
        return carUri;
    }

    public void setCarUri(URI carUri) {
        this.carUri = carUri;
    }

    public URI getDriverUri() {
        return driverUri;
    }

    public void setDriverUri(URI driverUri) {
        this.driverUri = driverUri;
    }

    public long getMaxSeats() {
        return maxSeats;
    }

    public void setMaxSeats(long maxSeats) {
        this.maxSeats = maxSeats;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public LocalDateTime getQueryStartDateTime() {
        return queryStartDateTime;
    }

    public void setQueryStartDateTime(LocalDateTime queryStartDateTime) {
        this.queryStartDateTime = queryStartDateTime;
    }

    public LocalDateTime getQueryEndDateTime() {
        return queryEndDateTime;
    }

    public void setQueryEndDateTime(LocalDateTime queryEndDateTime) {
        this.queryEndDateTime = queryEndDateTime;
    }

    public URI getSelfUri() {
        return selfUri;
    }

    public void setSelfUri(URI selfUri) {
        this.selfUri = selfUri;
    }

    public URI getPassengersUri() {
        return passengersUri;
    }

    public void setPassengersUri(URI passengersUri) {
        this.passengersUri = passengersUri;
    }

    public URI getPassengerUri() {
        return passengerUri;
    }

    public void setPassengerUri(URI passengerUri) {
        this.passengerUri = passengerUri;
    }
}
