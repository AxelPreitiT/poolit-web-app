package ar.edu.itba.paw.webapp.dto.output;

import ar.edu.itba.paw.models.Passenger;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.trips.Trip;
import ar.edu.itba.paw.webapp.controller.utils.UrlHolder;

import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.time.LocalDateTime;

public class TripDto {

    private URI originCityUri;
    private String originAddress;
    private URI destinationCityUri;
    private String destinationAddress;
    private URI carUri;
    private URI driverUri;
    private long maxSeats;
    private double pricePerTrip;
    private double totalPrice;
    private int totalTrips;
    private int occupiedSeats;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String passengersUriTemplate;
    private LocalDateTime queryStartDateTime;
    private LocalDateTime queryEndDateTime;
    private URI selfUri;
    private long tripId;
    private Trip.TripStatus tripStatus;


    public static TripDto fromTrip(final UriInfo uriInfo, final Trip trip){
        final TripDto ans = new TripDto();
        ans.tripId = trip.getTripId();
        ans.originAddress = trip.getOriginAddress();
        ans.destinationAddress = trip.getDestinationAddress();
        ans.maxSeats = trip.getMaxSeats();
        ans.pricePerTrip = trip.getPrice();
        ans.totalPrice = trip.getQueryTotalPrice();
        ans.totalTrips = trip.getQueryTotalTrips();
        ans.occupiedSeats = trip.getOccupiedSeats();
        ans.startDateTime = trip.getStartDateTime();
        ans.endDateTime = trip.getEndDateTime();
        ans.queryStartDateTime = trip.getQueryStartDateTime();
        ans.queryEndDateTime = trip.getQueryEndDateTime();
        ans.tripStatus = trip.getQueryTripStatus();
        ans.passengersUriTemplate = uriInfo.getBaseUriBuilder().path(UrlHolder.TRIPS_BASE).path(String.valueOf(trip.getTripId())).path(UrlHolder.TRIPS_PASSENGERS).toTemplate() + "{/userId}";
        ans.driverUri = uriInfo.getBaseUriBuilder().path(UrlHolder.USER_BASE).path(String.valueOf(trip.getDriver().getUserId())).build();
        ans.carUri = uriInfo.getBaseUriBuilder().path(UrlHolder.CAR_BASE).path(String.valueOf(trip.getCar().getCarId())).build();
        ans.originCityUri = uriInfo.getBaseUriBuilder().path(UrlHolder.CITY_BASE).path(String.valueOf(trip.getOriginCity().getId())).build();
        ans.destinationCityUri = uriInfo.getBaseUriBuilder().path(UrlHolder.CITY_BASE).path(String.valueOf(trip.getDestinationCity().getId())).build();
        ans.selfUri = uriInfo.getBaseUriBuilder().path(UrlHolder.TRIPS_BASE).path(String.valueOf(trip.getTripId())).queryParam("startDateTime",trip.getQueryStartDateTime()).queryParam("endDateTime",trip.getQueryEndDateTime()).build();
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

    public double getPricePerTrip() {
        return pricePerTrip;
    }

    public void setPricePerTrip(double pricePerTrip) {
        this.pricePerTrip = pricePerTrip;
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

    public String getPassengersUriTemplate() {
        return passengersUriTemplate;
    }

    public void setPassengersUriTemplate(String passengersUriTemplate) {
        this.passengersUriTemplate = passengersUriTemplate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getTotalTrips() {
        return totalTrips;
    }

    public void setTotalTrips(int totalTrips) {
        this.totalTrips = totalTrips;
    }

    public int getOccupiedSeats() {
        return occupiedSeats;
    }

    public void setOccupiedSeats(int occupiedSeats) {
        this.occupiedSeats = occupiedSeats;
    }

    public long getTripId() {
        return tripId;
    }

    public void setTripId(long tripId) {
        this.tripId = tripId;
    }

    public Trip.TripStatus getTripStatus() {
        return tripStatus;
    }

    public void setTripStatus(Trip.TripStatus tripStatus) {
        this.tripStatus = tripStatus;
    }
}
