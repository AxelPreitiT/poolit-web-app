package ar.edu.itba.paw.models.trips;

import java.time.LocalDateTime;

public class TripInstance {
    //Date and time when trip occurs
    private final LocalDateTime dateTime;
    //Parent Trip of this instance
    private final Trip trip;
    //passenger count for this occurrence of the trip
    private final int passengerCount;

    public TripInstance(LocalDateTime dateTime, Trip trip, int passengerCount) {
        this.dateTime = dateTime;
        this.trip = trip;
        this.passengerCount = passengerCount;
    }

    @Override
    public String toString() {
        return String.format("TripInstance { tripId: %d, dateTime: '%s', passengerCount: %d }",
                trip.getTripId(), dateTime, passengerCount);
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Trip getTrip() {
        return trip;
    }

    public int getPassengerCount() {
        return passengerCount;
    }
}
