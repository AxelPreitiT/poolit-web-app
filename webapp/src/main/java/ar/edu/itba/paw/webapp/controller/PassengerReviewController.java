package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.exceptions.*;
import ar.edu.itba.paw.interfaces.services.PassengerReviewService;
import ar.edu.itba.paw.models.PagedContent;
import ar.edu.itba.paw.models.reviews.PassengerReview;
import ar.edu.itba.paw.webapp.controller.mediaType.VndType;
import ar.edu.itba.paw.webapp.controller.utils.ControllerUtils;
import ar.edu.itba.paw.webapp.controller.utils.UrlHolder;
import ar.edu.itba.paw.webapp.controller.utils.queryBeans.ReviewsQuery;
import ar.edu.itba.paw.webapp.dto.input.reviews.CreatePassengerReviewDto;
import ar.edu.itba.paw.webapp.dto.output.reviews.PassengerReviewDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path(UrlHolder.PASSENGER_REVIEWS_BASE)
@Component
public class PassengerReviewController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PassengerReviewController.class);

    private final PassengerReviewService passengerReviewService;

    private static final String PAGE_SIZE = "10";

    @Context
    private UriInfo uriInfo;

    @Inject
    @Autowired
    public PassengerReviewController(final PassengerReviewService passengerReviewService ){
        this.passengerReviewService = passengerReviewService;
    }

    @POST
    @Consumes(value = VndType.APPLICATION_REVIEW_PASSENGER)
    public Response createReview(@Valid final CreatePassengerReviewDto createPassengerReviewDto) throws UserNotFoundException, PassengerNotFoundException, UserNotLoggedInException, TripNotFoundException {
        LOGGER.debug("POST request to add a passenger review for trip {} to passenger {}",createPassengerReviewDto.getTripId(),createPassengerReviewDto.getPassengerId());
        final PassengerReview passengerReview = passengerReviewService.createPassengerReview(createPassengerReviewDto.getTripId(),createPassengerReviewDto.getPassengerId(),createPassengerReviewDto.getRating(),createPassengerReviewDto.getComment(),createPassengerReviewDto.getOption());
        return Response.created(uriInfo.getBaseUriBuilder().path(String.valueOf(passengerReview.getReviewId())).build()).build();
    }

    @GET
    @Path("/{id}")
    @Produces(value = VndType.APPLICATION_REVIEW_PASSENGER)
    public Response getReview(@PathParam("id") final long id) throws ReviewNotFoundException {
        LOGGER.debug("GET request for passenger review with id {}",id);
        final PassengerReview passengerReview = passengerReviewService.findById(id).orElseThrow(ControllerUtils.notFoundExceptionOf(ReviewNotFoundException::new));
        return Response.ok(PassengerReviewDto.fromPassengerReview(uriInfo,passengerReview)).build();
    }


    @GET
    @PreAuthorize("@authValidator.checkIfWantedIsSelf(#reviewerUserId)")
    @Produces(value = VndType.APPLICATION_REVIEW_PASSENGER)
    public Response getReviews(@Valid @BeanParam final ReviewsQuery query) throws UserNotFoundException, TripNotFoundException {
        if(query.getMadeBy()!=null || query.getForTrip()!=null){
            LOGGER.debug("GET request for passenger reviews made by user {} for passengers on trip {}",query.getMadeBy(),query.getForTrip());
            final PagedContent<PassengerReview> ans = passengerReviewService.getPassengerReviewsMadeByUserOnTrip(query.getMadeBy(),query.getForTrip(),query.getPage(),query.getPageSize());
            return ControllerUtils.getPaginatedResponse(uriInfo,ans,query.getPage(),PassengerReviewDto::fromPassengerReview,PassengerReviewDto.class);
        }
        LOGGER.debug("GET request for passenger reviews for user {}",query.getForUser());
        final PagedContent<PassengerReview> ans = passengerReviewService.getPassengerReviews(query.getForUser(),query.getPage(),query.getPageSize());
        return ControllerUtils.getPaginatedResponse(uriInfo,ans,query.getPage(),PassengerReviewDto::fromPassengerReview,PassengerReviewDto.class);
    }

}
