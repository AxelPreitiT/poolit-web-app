package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.exceptions.*;
import ar.edu.itba.paw.interfaces.services.DriverReviewService;
import ar.edu.itba.paw.models.PagedContent;
import ar.edu.itba.paw.models.reviews.DriverReview;
import ar.edu.itba.paw.webapp.controller.mediaType.VndType;
import ar.edu.itba.paw.webapp.controller.utils.ControllerUtils;
import ar.edu.itba.paw.webapp.controller.utils.UrlHolder;
import ar.edu.itba.paw.webapp.controller.utils.queryBeans.ReviewsQuery;
import ar.edu.itba.paw.webapp.dto.input.reviews.CreateDriverReviewDto;
import ar.edu.itba.paw.webapp.dto.output.reviews.DriverReviewDto;
import ar.edu.itba.paw.webapp.exceptions.ResourceNotFoundException;
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

@Path(UrlHolder.DRIVER_REVIEWS_BASE)
@Component
public class DriverReviewController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DriverReviewController.class);

    private final DriverReviewService driverReviewService;

    @Context
    private UriInfo uriInfo;

    @Inject
    @Autowired
    public DriverReviewController(final DriverReviewService driverReviewService) {
        this.driverReviewService = driverReviewService;
    }

    @POST
    @Consumes(value = VndType.APPLICATION_REVIEW_DRIVER)
    public Response createReview(@Valid final CreateDriverReviewDto createDriverReviewDto) throws UserNotFoundException, PassengerNotFoundException, UserNotLoggedInException, TripNotFoundException {
        LOGGER.debug("POST request to add a driver review for trip {} to driver {}",createDriverReviewDto.getTripId(),createDriverReviewDto.getDriverId());
        final DriverReview driverReview = driverReviewService.createDriverReview(createDriverReviewDto.getTripId(),createDriverReviewDto.getDriverId(),createDriverReviewDto.getRating(),createDriverReviewDto.getComment(),createDriverReviewDto.getOption());
        return Response.created(uriInfo.getBaseUriBuilder().path(String.valueOf(driverReview.getReviewId())).build()).build();
    }

    @GET
    @Path("/{id}")
    @Produces(value = VndType.APPLICATION_REVIEW_DRIVER)
    public Response getReview(@PathParam("id") final long id){
        LOGGER.debug("GET request for passenger review with id {}",id);
        final DriverReview driverReview = driverReviewService.findById(id).orElseThrow(ResourceNotFoundException::new);
        return Response.ok(DriverReviewDto.fromDriverReview(uriInfo,driverReview)).build();
    }

    @GET
    @PreAuthorize("@authValidator.checkIfWantedIsSelf(#query.madeBy)")
    @Produces(value = VndType.APPLICATION_REVIEW_DRIVER)
    public Response getReviews(@Valid @BeanParam final ReviewsQuery query) throws UserNotFoundException, TripNotFoundException {
        if(query.getMadeBy()!=null || query.getForTrip()!=null){
            LOGGER.debug("GET request for driver reviews made by user {} for passengers on trip {}",query.getMadeBy(),query.getForTrip());
            final PagedContent<DriverReview> ans = driverReviewService.getDriverReviewsMadeByUserOnTrip(query.getMadeBy(),query.getForTrip(),query.getPage(),query.getPageSize());
            return ControllerUtils.getPaginatedResponse(uriInfo,ans,query.getPage(),DriverReviewDto::fromDriverReview,DriverReviewDto.class);
        }
        LOGGER.debug("GET request for driver reviews for user {}",query.getForUser());
        final PagedContent<DriverReview> ans = driverReviewService.getDriverReviews(query.getForUser(),query.getPage(),query.getPageSize());
        return ControllerUtils.getPaginatedResponse(uriInfo,ans,query.getPage(),DriverReviewDto::fromDriverReview,DriverReviewDto.class);
    }



}
