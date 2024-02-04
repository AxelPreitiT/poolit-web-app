package ar.edu.itba.paw.webapp.controller.utils.queryBeans;

import ar.edu.itba.paw.webapp.dto.validation.annotations.NotAllNull;
import ar.edu.itba.paw.webapp.dto.validation.annotations.NotNullTogether;

import javax.ws.rs.QueryParam;

@NotAllNull
@NotNullTogether(fields = {"madeBy","forTrip"})
public class ReviewsQuery extends PagedQuery{
    @QueryParam("forUser")
    private Integer forUser;

    @QueryParam("madeBy")
    private Integer madeBy;

    @QueryParam("forTrip")
    private Integer forTrip;

    public Integer getForUser() {
        return forUser;
    }

    public Integer getMadeBy() {
        return madeBy;
    }

    public Integer getForTrip() {
        return forTrip;
    }
}
