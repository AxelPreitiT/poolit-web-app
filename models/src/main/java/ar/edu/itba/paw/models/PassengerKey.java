package ar.edu.itba.paw.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PassengerKey implements Serializable {
    private long tripId;
    private long userId;
    public PassengerKey(){
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PassengerKey that = (PassengerKey) o;
        return tripId == that.tripId && userId == that.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(tripId, userId);
    }
}
