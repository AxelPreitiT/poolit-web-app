package ar.edu.itba.paw.webapp.dto.output;

import ar.edu.itba.paw.models.Passenger;
import ar.edu.itba.paw.webapp.controller.utils.UrlHolder;

import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.time.LocalDateTime;

public class PassengerDto {

    private URI selfUri;

    private URI userUri;

    private URI tripUri;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    private Passenger.PassengerState passengerState;
    private String passengerReviewsForTripUriTemplate;

    private URI otherPassengersUri;

    public static PassengerDto fromPassenger(final UriInfo uriInfo, final Passenger passenger){
        final PassengerDto ans = new PassengerDto();
        ans.selfUri = uriInfo.getBaseUriBuilder().path(UrlHolder.TRIPS_BASE).path(String.valueOf(passenger.getTrip().getTripId())).path(UrlHolder.TRIPS_PASSENGERS).path(String.valueOf(passenger.getUserId())).build();
        ans.passengerState = passenger.getPassengerState();
        ans.startDateTime = passenger.getStartDateTime();
        ans.endDateTime = passenger.getEndDateTime();
        ans.tripUri = uriInfo.getBaseUriBuilder().path(UrlHolder.TRIPS_BASE).path(String.valueOf(passenger.getTrip().getTripId())).queryParam("startDateTime",passenger.getTrip().getQueryStartDateTime()).queryParam("endDateTime",passenger.getTrip().getQueryEndDateTime()).build();
        ans.userUri = uriInfo.getBaseUriBuilder().path(UrlHolder.USER_BASE).path(String.valueOf(passenger.getUserId())).build();
        ans.passengerReviewsForTripUriTemplate = uriInfo.getBaseUriBuilder().path(UrlHolder.PASSENGER_REVIEWS_BASE).queryParam("forTrip",passenger.getTrip().getTripId()).queryParam("forUser",passenger.getUserId()).queryParam("madeBy","{userId}").toTemplate();
        if(passenger.getPassengerState().equals(Passenger.PassengerState.ACCEPTED)){
            ans.otherPassengersUri = uriInfo.getBaseUriBuilder().path(UrlHolder.TRIPS_BASE).path(String.valueOf(passenger.getTrip().getTripId())).path(UrlHolder.TRIPS_PASSENGERS).queryParam("startDateTime",passenger.getStartDateTime()).queryParam("endDateTime",passenger.getEndDateTime()).queryParam("passengerState",Passenger.PassengerState.ACCEPTED).build();
        }
        return ans;
    }

    public URI getSelfUri() {
        return selfUri;
    }

    public void setSelfUri(URI selfUri) {
        this.selfUri = selfUri;
    }

    public URI getUserUri() {
        return userUri;
    }

    public void setUserUri(URI userUri) {
        this.userUri = userUri;
    }

    public URI getTripUri() {
        return tripUri;
    }

    public void setTripUri(URI tripUri) {
        this.tripUri = tripUri;
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

    public Passenger.PassengerState getPassengerState() {
        return passengerState;
    }

    public void setPassengerState(Passenger.PassengerState passengerState) {
        this.passengerState = passengerState;
    }

    public URI getOtherPassengersUri() {
        return otherPassengersUri;
    }

    public void setOtherPassengersUri(URI otherPassengersUri) {
        this.otherPassengersUri = otherPassengersUri;
    }

    public String getPassengerReviewsForTripUriTemplate() {
        return passengerReviewsForTripUriTemplate;
    }

    public void setPassengerReviewsForTripUriTemplate(String passengerReviewsForTripUriTemplate) {
        this.passengerReviewsForTripUriTemplate = passengerReviewsForTripUriTemplate;
    }
}
