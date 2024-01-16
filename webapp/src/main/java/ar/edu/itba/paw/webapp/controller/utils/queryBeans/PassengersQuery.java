package ar.edu.itba.paw.webapp.controller.utils.queryBeans;

import ar.edu.itba.paw.models.Passenger;
import ar.edu.itba.paw.webapp.controller.utils.ControllerUtils;
import ar.edu.itba.paw.webapp.dto.validation.annotations.NotNullTogether;

import javax.validation.constraints.Min;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;
import java.time.LocalDateTime;

@NotNullTogether(fields = {"startDateTime","endDateTime","passengerState"})
public class PassengersQuery {
    @QueryParam("startDateTime")
    private LocalDateTime startDateTime;

    @QueryParam("endDateTime")
    private LocalDateTime endDateTime;

    @QueryParam("passengerState")
    private Passenger.PassengerState passengerState;

    @QueryParam(ControllerUtils.PAGE_QUERY_PARAM)
    @DefaultValue(ControllerUtils.DEFAULT_PAGE)
    @Min(0)
    private int page;

    @QueryParam(ControllerUtils.PAGE_SIZE_QUERY_PARAM)
    @Min(1)
    @DefaultValue(ControllerUtils.DEFAULT_PAGE_SIZE)
    private int pageSize;

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public Passenger.PassengerState getPassengerState() {
        return passengerState;
    }

    public int getPage() {
        return page;
    }

    public int getPageSize() {
        return pageSize;
    }
}
