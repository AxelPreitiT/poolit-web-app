package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.exceptions.*;
import ar.edu.itba.paw.interfaces.services.DriverReviewService;
import ar.edu.itba.paw.models.PagedContent;
import ar.edu.itba.paw.models.reviews.DriverReview;
import ar.edu.itba.paw.webapp.controller.utils.ControllerUtils;
import ar.edu.itba.paw.webapp.controller.utils.UrlHolder;
import ar.edu.itba.paw.webapp.dto.input.reviews.CreateDriverReviewDto;
import ar.edu.itba.paw.webapp.dto.output.reviews.DriverReviewDto;
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

@Path(UrlHolder.DRIVER_REVIEWS_BASE)
@Component
public class DriverReviewController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DriverReviewController.class);

    private final DriverReviewService driverReviewService;

    private static final int PAGE_SIZE = 10;

    @Context
    private UriInfo uriInfo;

    @Inject
    @Autowired
    public DriverReviewController(final DriverReviewService driverReviewService) {
        this.driverReviewService = driverReviewService;
    }

    @POST
    public Response createReview(@Valid final CreateDriverReviewDto createDriverReviewDto) throws UserNotFoundException, PassengerNotFoundException, UserNotLoggedInException, TripNotFoundException {
        LOGGER.debug("POST request to add a driver review for trip {} to driver {}",createDriverReviewDto.getTripId(),createDriverReviewDto.getDriverId());
        final DriverReview driverReview = driverReviewService.createDriverReview(createDriverReviewDto.getTripId(),createDriverReviewDto.getDriverId(),createDriverReviewDto.getRating(),createDriverReviewDto.getComment(),createDriverReviewDto.getOption());
        return Response.created(uriInfo.getBaseUriBuilder().path(String.valueOf(driverReview.getReviewId())).build()).build();
    }

    @GET
    @Path("/{id}")
    public Response getReview(@PathParam("id") final long id) throws ReviewNotFoundException {
        LOGGER.debug("GET request for passenger review with id {}",id);
        final DriverReview driverReview = driverReviewService.findById(id).orElseThrow(ControllerUtils.notFoundExceptionOf(ReviewNotFoundException::new));
        return Response.ok(DriverReviewDto.fromDriverReview(uriInfo,driverReview)).build();
    }

    @GET
    @PreAuthorize("@authValidator.checkIfWantedIsSelf(#reviewerUserId)")
    public Response getReviews(@QueryParam("forUser") final Integer userId,
                               @QueryParam("madeBy") final Integer reviewerUserId,
                               @QueryParam("forTrip") final Integer tripId,
                               @QueryParam(ControllerUtils.PAGE_QUERY_PARAM) @DefaultValue("0") @Valid @Min(0) final int page) throws UserNotFoundException, TripNotFoundException {
        if(reviewerUserId!=null || tripId!=null){
            if(reviewerUserId==null || tripId == null || userId != null){
                throw new IllegalArgumentException();
            }
            LOGGER.debug("GET request for driver reviews made by user {} for passengers on trip {}",reviewerUserId,tripId);
            final PagedContent<DriverReview> ans = driverReviewService.getDriverReviewsMadeByUserOnTrip(reviewerUserId,tripId,page,PAGE_SIZE);
            return ControllerUtils.getPaginatedResponse(uriInfo,ans,page,DriverReviewDto::fromDriverReview,DriverReviewDto.class);
        }
        if(userId==null){
            throw new IllegalArgumentException();
        }
        LOGGER.debug("GET request for driver reviews for user {}",userId);
        final PagedContent<DriverReview> ans = driverReviewService.getDriverReviews(userId,page,PAGE_SIZE);
        return ControllerUtils.getPaginatedResponse(uriInfo,ans,page,DriverReviewDto::fromDriverReview,DriverReviewDto.class);
    }



}
