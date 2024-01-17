package ar.edu.itba.paw.webapp.controller.utils.queryBeans;

import ar.edu.itba.paw.webapp.dto.validation.annotations.NotNullTogether;

import javax.ws.rs.QueryParam;
import java.time.LocalDateTime;

@NotNullTogether(fields = {"startDateTime","endDateTime"})
public class TripQuery {

    @QueryParam("startDateTime")
    private LocalDateTime startDateTime;

    @QueryParam("endDateTime")
    private LocalDateTime endDateTime;

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }
}
