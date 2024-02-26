package ar.edu.itba.paw.webapp.dto.input;

import ar.edu.itba.paw.models.Passenger;

import javax.validation.constraints.NotNull;

public class PatchPassengerDto {
    @NotNull
    private Passenger.PassengerState passengerState;

    public Passenger.PassengerState getPassengerState() {
        return passengerState;
    }

    public void setPassengerState(Passenger.PassengerState passengerState) {
        this.passengerState = passengerState;
    }
}
