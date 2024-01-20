package ar.edu.itba.paw.webapp.controller.utils.queryBeans;

import ar.edu.itba.paw.webapp.dto.validation.annotations.NotNullTogether;

import javax.ws.rs.QueryParam;

@NotNullTogether(fields = {"madeBy","forTrip"})
public class CarReviewsQuery extends PagedQuery{
    @QueryParam("madeBy")
    private Integer madeBy;

    @QueryParam("forTrip")
    private Integer forTrip;

    public Integer getMadeBy() {
        return madeBy;
    }

    public Integer getForTrip() {
        return forTrip;
    }
}
