package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.exceptions.*;
import ar.edu.itba.paw.interfaces.services.CarReviewService;
import ar.edu.itba.paw.interfaces.services.CarService;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.reviews.CarReview;
import ar.edu.itba.paw.webapp.controller.mediaType.VndType;
import ar.edu.itba.paw.webapp.controller.utils.ControllerUtils;
import ar.edu.itba.paw.webapp.controller.utils.UrlHolder;
import ar.edu.itba.paw.webapp.dto.input.CreateCarDto;
import ar.edu.itba.paw.webapp.dto.input.UpdateCarDto;
import ar.edu.itba.paw.webapp.dto.input.reviews.CreateCarReviewDto;
import ar.edu.itba.paw.webapp.dto.output.CarDto;
import ar.edu.itba.paw.webapp.dto.output.reviews.CarReviewDto;
import ar.edu.itba.paw.webapp.dto.validation.annotations.ImageSize;
import ar.edu.itba.paw.webapp.dto.validation.annotations.ImageType;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path(UrlHolder.CAR_BASE)
@Component
public class CarController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarController.class);

    private final CarService carService;

    private final CarReviewService carReviewService;

    private static final int PAGE_SIZE = 10;

    @Context
    private UriInfo uriInfo;

    @Inject
    @Autowired
    public CarController(final CarService carService, final CarReviewService carReviewService) {
        this.carService = carService;
        this.carReviewService = carReviewService;
    }


    //TODO: revisar si se quiere paginado
    @GET
    @PreAuthorize("@authValidator.checkIfWantedIsSelf(#userId)")
    public Response getCars(@QueryParam("fromUser")@Valid @NotNull(message = "{dto.validation.fromUser}") Integer userId) throws UserNotFoundException {
        LOGGER.debug("GET request for cars from user {}",userId);
        final List<CarDto> cars = carService.findUserCars(userId).stream().map(car -> CarDto.fromCar(uriInfo,car)).collect(Collectors.toList());
        return Response.ok(new GenericEntity<List<CarDto>>(cars){}).build();
    }

    //TODO: ver de buscar el rating en el servicio para el auto (como el usuario)
    @GET
    @Path("/{id}")
    @Produces(VndType.APPLICATION_CAR)
    public Response getCarById(@PathParam("id") final long id) throws CarNotFoundException{
        LOGGER.debug("GET request for car with carId {}",id);
        final Car car = carService.findById(id).orElseThrow(ControllerUtils.notFoundExceptionOf(CarNotFoundException::new));
        return Response.ok(CarDto.fromCar(uriInfo, car)).build();
    }


    @POST
    @Consumes( value = {MediaType.APPLICATION_JSON})
    public Response createCar(@Valid final CreateCarDto carDto) throws UserNotFoundException {
        LOGGER.debug("POST request to create car");
        final Car car = carService.createCar(carDto.getPlate(), carDto.getCarInfo(), new byte[0], carDto.getSeats(),carDto.getCarBrand(),
                                                carDto.getFeatures());
        final URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(car.getCarId())).build();
        return Response.created(uri).build();
    }

    @PUT
    @Path("/{id}")
    @PreAuthorize("@authValidator.checkIfCarIsOwn(#id)")
    @Produces( value = { MediaType.APPLICATION_JSON } )
    public Response modifyCar(@PathParam("id") final long id, @Valid final UpdateCarDto updateCarDto) throws CarNotFoundException {
        LOGGER.debug("PUT request to update car with carId {}",id);
        carService.modifyCar(id, updateCarDto.getCarInfo(), updateCarDto.getSeats(), updateCarDto.getFeatures(), new byte[0]);
        return Response.noContent().build();
    }

    @GET
    @Path("/{id}"+UrlHolder.IMAGE_ENTITY)
    @Produces({"image/*"})
    public Response getCarImage(@PathParam("id") final long id) throws ImageNotFoundException, CarNotFoundException {
        LOGGER.debug("GET request for image of car with carId {}",id);
        final byte[] image = carService.getCarImage(id);
//        TODO: add caching capability
        return Response.ok(image).build();
    }

    @PUT
    @Path("/{id}"+UrlHolder.IMAGE_ENTITY)
    @PreAuthorize("@authValidator.checkIfCarIsOwn(#id)")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response updateCarImage(@PathParam("id") final long id,
                                    @ImageType @FormDataParam("image") final FormDataBodyPart type,
                                    @ImageSize @FormDataParam("image") final byte[] image) throws ImageNotFoundException, CarNotFoundException{
        LOGGER.debug("PUT request to update image of car with carId {}",id);
        carService.updateCarImage(id,image);
        return Response.noContent().contentLocation(uriInfo.getBaseUriBuilder().path(String.valueOf(id)).path("image/").build()).build();
    }

    //Add a review
    @POST
    @Path("/{id}"+UrlHolder.REVIEWS_ENTITY)
    public Response addReview(@PathParam("id") final long id,
                              @Valid final CreateCarReviewDto createCarReviewDto) throws UserNotFoundException, PassengerNotFoundException, CarNotFoundException, TripNotFoundException {
        LOGGER.debug("POST request to add a review for car {} on trip {}",id,createCarReviewDto.getTripId());
        final CarReview carReview = carReviewService.createCarReview(createCarReviewDto.getTripId(),id,createCarReviewDto.getRating(),createCarReviewDto.getComment(),createCarReviewDto.getOption());
        return Response.created(uriInfo.getAbsolutePathBuilder().path(String.valueOf(carReview.getReviewId())).build()).build();
    }


    //Get one review
    @GET
    @Path("/{id}"+UrlHolder.REVIEWS_ENTITY+"/{reviewId}")
    public Response getReview(@PathParam("id") final long id,
                              @PathParam("reviewId") final long reviewId) throws ReviewNotFoundException {
        LOGGER.debug("GET request for review for car {} with reviewId {}",id,reviewId);
        final CarReview carReview = carReviewService.findById(reviewId).orElseThrow(ControllerUtils.notFoundExceptionOf(ReviewNotFoundException::new));
        return Response.ok(CarReviewDto.fromCarReview(uriInfo,carReview)).build();
    }

    //Get multiple reviews (made by a user or all of them)
    @GET
    @Path("/{id}"+UrlHolder.REVIEWS_ENTITY)
    @PreAuthorize("@authValidator.checkIfWantedIsSelf(#reviewerUserId)")
    public Response getAllReviews(@PathParam("id") final long id,
                                  @QueryParam("madeBy") final Integer reviewerUserId,
                                  @QueryParam("forTrip") final Integer tripId,
                                  @QueryParam(ControllerUtils.PAGE_QUERY_PARAM) @DefaultValue("0") @Valid @Min(0) final int page) throws CarNotFoundException, UserNotFoundException, TripNotFoundException {
        if(reviewerUserId!=null || tripId!=null){
            //request for a user and trip should be made
            if(reviewerUserId==null || tripId==null){
                throw new IllegalArgumentException();
            }
            final PagedContent<CarReview> ans =  carReviewService.getCarReviewsMadeByUserOnTrip(id,reviewerUserId,tripId,page,PAGE_SIZE);
            return ControllerUtils.getPaginatedResponse(uriInfo,ans,page,CarReviewDto::fromCarReview,CarReviewDto.class);
        }

        final PagedContent<CarReview> ans = carReviewService.getCarReviews(id,page,PAGE_SIZE);
        return ControllerUtils.getPaginatedResponse(uriInfo,ans,page,CarReviewDto::fromCarReview,CarReviewDto.class);
    }




}
