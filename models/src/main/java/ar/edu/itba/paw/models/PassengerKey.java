package ar.edu.itba.paw.models;

import ar.edu.itba.paw.models.trips.Trip;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class PassengerKey implements Serializable {
    protected Trip trip;
    protected User user;
    public PassengerKey(){
    }

    public PassengerKey(Trip trip, User user) {
        this.trip = trip;
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PassengerKey that = (PassengerKey) o;
        return trip.getTripId() == that.trip.getTripId() && user.getUserId() == that.user.getUserId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(trip.getTripId(), user.getUserId());
    }
}
