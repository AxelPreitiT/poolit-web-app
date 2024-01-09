package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.TripReviewService;
import ar.edu.itba.paw.webapp.controller.utils.UrlHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path(UrlHolder.REVIEWS_BASE)
@Component
public class ReviewController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewController.class);

    private final TripReviewService tripReviewService;


    @Context
    private UriInfo uriInfo;

    //TODO: ver si las de los autos las dejamos acá también o las pongo con los autos directamente como subinstancias
    @Inject
    public ReviewController(final TripReviewService tripReviewService) {
        this.tripReviewService = tripReviewService;
    }

    @POST
    public Response createReview(){
        return Response.ok().build();
    }


}
