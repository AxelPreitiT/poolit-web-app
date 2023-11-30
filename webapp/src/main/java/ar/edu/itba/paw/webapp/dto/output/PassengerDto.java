package ar.edu.itba.paw.webapp.dto.output;

import ar.edu.itba.paw.models.Passenger;
import ar.edu.itba.paw.webapp.controller.utils.UrlHolder;

import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.time.LocalDateTime;

public class PassengerDto {

    private URI userUri;

    private URI tripUri;

    LocalDateTime startDateTime;

    LocalDateTime endDateTime;

    Passenger.PassengerState passengerState;

    public static PassengerDto fromPassenger(final UriInfo uriInfo, final Passenger passenger){
        final PassengerDto ans = new PassengerDto();
        ans.passengerState = passenger.getPassengerState();
        ans.startDateTime = passenger.getStartDateTime();
        ans.endDateTime = passenger.getEndDateTime();
        ans.tripUri = uriInfo.getBaseUriBuilder().path(UrlHolder.TRIPS_BASE).path(String.valueOf(passenger.getTrip().getTripId())).build();
        ans.userUri = uriInfo.getBaseUriBuilder().path(UrlHolder.USER_BASE).path(String.valueOf(passenger.getUserId())).build();
        return ans;
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
}
