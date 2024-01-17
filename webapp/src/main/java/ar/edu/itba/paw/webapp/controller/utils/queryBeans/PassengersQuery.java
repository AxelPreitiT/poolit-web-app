package ar.edu.itba.paw.webapp.controller.utils.queryBeans;

import ar.edu.itba.paw.models.Passenger;
import ar.edu.itba.paw.webapp.controller.utils.ControllerUtils;
import ar.edu.itba.paw.webapp.dto.validation.annotations.NotNullTogether;

import javax.validation.constraints.Min;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;
import java.time.LocalDateTime;

@NotNullTogether(fields = {"startDateTime","endDateTime"})
public class PassengersQuery extends PagedQuery{
    @QueryParam("startDateTime")
    private LocalDateTime startDateTime;

    @QueryParam("endDateTime")
    private LocalDateTime endDateTime;

    @QueryParam("passengerState")
    private Passenger.PassengerState passengerState;

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public Passenger.PassengerState getPassengerState() {
        return passengerState;
    }


}
