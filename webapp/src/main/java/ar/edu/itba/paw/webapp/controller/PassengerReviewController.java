package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.exceptions.*;
import ar.edu.itba.paw.interfaces.services.PassengerReviewService;
import ar.edu.itba.paw.models.PagedContent;
import ar.edu.itba.paw.models.reviews.PassengerReview;
import ar.edu.itba.paw.webapp.controller.utils.ControllerUtils;
import ar.edu.itba.paw.webapp.controller.utils.UrlHolder;
import ar.edu.itba.paw.webapp.dto.input.reviews.CreatePassengerReviewDto;
import ar.edu.itba.paw.webapp.dto.output.reviews.PassengerReviewDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.Min;
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
    public Response createReview(@Valid final CreatePassengerReviewDto createPassengerReviewDto) throws UserNotFoundException, PassengerNotFoundException, UserNotLoggedInException, TripNotFoundException {
        LOGGER.debug("POST request to add a passenger review for trip {} to passenger {}",createPassengerReviewDto.getTripId(),createPassengerReviewDto.getPassengerId());
        final PassengerReview passengerReview = passengerReviewService.createPassengerReview(createPassengerReviewDto.getTripId(),createPassengerReviewDto.getPassengerId(),createPassengerReviewDto.getRating(),createPassengerReviewDto.getComment(),createPassengerReviewDto.getOption());
        return Response.created(uriInfo.getBaseUriBuilder().path(String.valueOf(passengerReview.getReviewId())).build()).build();
    }

    @GET
    @Path("/{id}")
    public Response getReview(@PathParam("id") final long id) throws ReviewNotFoundException {
        LOGGER.debug("GET request for passenger review with id {}",id);
        final PassengerReview passengerReview = passengerReviewService.findById(id).orElseThrow(ControllerUtils.notFoundExceptionOf(ReviewNotFoundException::new));
        return Response.ok(PassengerReviewDto.fromPassengerReview(uriInfo,passengerReview)).build();
    }


    @GET
    @PreAuthorize("@authValidator.checkIfWantedIsSelf(#reviewerUserId)")
    public Response getReviews(@QueryParam("forUser") final Integer userId,
                               @QueryParam("madeBy") final Integer reviewerUserId,
                               @QueryParam("forTrip") final Integer tripId,
                               @QueryParam(ControllerUtils.PAGE_QUERY_PARAM) @DefaultValue("0") @Valid @Min(0) final int page,
                               @QueryParam(ControllerUtils.PAGE_SIZE_QUERY_PARAM) @DefaultValue(PAGE_SIZE) @Valid @Min(1) final int pageSize) throws UserNotFoundException, TripNotFoundException {
        if(reviewerUserId!=null || tripId!=null){
            if(reviewerUserId==null || tripId == null || userId != null){
                throw new IllegalArgumentException();
            }
            LOGGER.debug("GET request for passenger reviews made by user {} for passengers on trip {}",reviewerUserId,tripId);
            final PagedContent<PassengerReview> ans = passengerReviewService.getPassengerReviewsMadeByUserOnTrip(reviewerUserId,tripId,page,pageSize);
            return ControllerUtils.getPaginatedResponse(uriInfo,ans,page,PassengerReviewDto::fromPassengerReview,PassengerReviewDto.class);
        }
        if(userId==null){
            throw new IllegalArgumentException();
        }
        LOGGER.debug("GET request for passenger reviews for user {}",userId);
        final PagedContent<PassengerReview> ans = passengerReviewService.getPassengerReviews(userId,page,pageSize);
        return ControllerUtils.getPaginatedResponse(uriInfo,ans,page,PassengerReviewDto::fromPassengerReview,PassengerReviewDto.class);
    }

}
