package ar.edu.itba.paw.webapp.dto.output.trips;

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
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String passengersUriTemplate;
    private URI selfUri;
    private long tripId;
    private Trip.TripStatus tripStatus;
    private boolean isDeleted;
    private LocalDateTime lastOccurrence;
    private String driverReviewsUriTemplate;
    private String carReviewsUriTemplate;
    private String driverReportsUriTemplate;


    public static TripDto fromTrip(final UriInfo uriInfo, final Trip trip){
        final TripDto ans = new TripDto();
        ans.tripId = trip.getTripId();
        ans.originAddress = trip.getOriginAddress();
        ans.destinationAddress = trip.getDestinationAddress();
        ans.maxSeats = trip.getMaxSeats();
        ans.pricePerTrip = trip.getPrice();
        ans.startDateTime = trip.getStartDateTime();
        ans.endDateTime = trip.getEndDateTime();
        ans.tripStatus = trip.getTripStatus();
        ans.isDeleted = trip.isDeleted();
        ans.lastOccurrence = trip.getLastOccurrence();
        ans.driverReviewsUriTemplate = uriInfo.getBaseUriBuilder().path(UrlHolder.DRIVER_REVIEWS_BASE).queryParam("forTrip",trip.getTripId()).queryParam("madeBy","{userId}").toTemplate();
        ans.carReviewsUriTemplate = uriInfo.getBaseUriBuilder().path(UrlHolder.CAR_BASE).path(String.valueOf(trip.getCar().getCarId())).path(UrlHolder.REVIEWS_ENTITY).queryParam("forTrip",trip.getTripId()).queryParam("madeBy","{userId}").toTemplate();
        ans.driverReportsUriTemplate = uriInfo.getBaseUriBuilder().path(UrlHolder.REPORT_BASE).queryParam("forTrip",trip.getTripId()).queryParam("forUser",trip.getDriver().getUserId()).queryParam("madeBy","{userId}").toTemplate();
        ans.passengersUriTemplate = uriInfo.getBaseUriBuilder().path(UrlHolder.TRIPS_BASE).path(String.valueOf(trip.getTripId())).path(UrlHolder.TRIPS_PASSENGERS).toTemplate() + "{/userId}{?startDateTime,endDateTime,passengerState}";
        ans.driverUri = uriInfo.getBaseUriBuilder().path(UrlHolder.USER_BASE).path(String.valueOf(trip.getDriver().getUserId())).build();
        ans.carUri = uriInfo.getBaseUriBuilder().path(UrlHolder.CAR_BASE).path(String.valueOf(trip.getCar().getCarId())).build();
        ans.originCityUri = uriInfo.getBaseUriBuilder().path(UrlHolder.CITY_BASE).path(String.valueOf(trip.getOriginCity().getId())).build();
        ans.destinationCityUri = uriInfo.getBaseUriBuilder().path(UrlHolder.CITY_BASE).path(String.valueOf(trip.getDestinationCity().getId())).build();
        ans.selfUri = uriInfo.getBaseUriBuilder().path(UrlHolder.TRIPS_BASE).path(String.valueOf(trip.getTripId())).build();
//        ans.selfUri = uriInfo.getBaseUriBuilder().path(UrlHolder.TRIPS_BASE).path(String.valueOf(trip.getTripId())).queryParam("startDateTime",trip.getQueryStartDateTime()).queryParam("endDateTime",trip.getQueryEndDateTime()).build();

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

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public LocalDateTime getLastOccurrence() {
        return lastOccurrence;
    }

    public void setLastOccurrence(LocalDateTime lastOccurrence) {
        this.lastOccurrence = lastOccurrence;
    }

    public String getDriverReviewsUriTemplate() {
        return driverReviewsUriTemplate;
    }

    public void setDriverReviewsUriTemplate(String driverReviewsUriTemplate) {
        this.driverReviewsUriTemplate = driverReviewsUriTemplate;
    }

    public String getCarReviewsUriTemplate() {
        return carReviewsUriTemplate;
    }

    public void setCarReviewsUriTemplate(String carReviewsUriTemplate) {
        this.carReviewsUriTemplate = carReviewsUriTemplate;
    }

    public String getDriverReportsUriTemplate() {
        return driverReportsUriTemplate;
    }

    public void setDriverReportsUriTemplate(String driverReportsUriTemplate) {
        this.driverReportsUriTemplate = driverReportsUriTemplate;
    }
}
