package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.exceptions.*;
import ar.edu.itba.paw.interfaces.services.CarReviewService;
import ar.edu.itba.paw.interfaces.services.CarService;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.webapp.controller.mediaType.VndType;
import ar.edu.itba.paw.webapp.dto.input.CreateCarDto;
import ar.edu.itba.paw.webapp.dto.input.UpdateCarDto;
import ar.edu.itba.paw.webapp.dto.output.CarDto;
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
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Path("/api/cars")
@Component
public class CarController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarController.class);

    private final CarService carService;

    @Context
    private UriInfo uriInfo;

    @Inject
    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
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
        final Car car = carService.findById(id).orElseThrow(CarNotFoundException::new);
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
    @Path("/{id}/image")
    @Produces({"image/*"})
    public Response getCarImage(@PathParam("id") final long id) throws ImageNotFoundException, CarNotFoundException {
        LOGGER.debug("GET request for image of car with carId {}",id);
        final byte[] image = carService.getCarImage(id);
//        TODO: add caching capability
        return Response.ok(image).build();
    }

    @PUT
    @Path("/{id}/image")
    @PreAuthorize("@authValidator.checkIfWantedIsSelf(#id)")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response updateCarImage(@PathParam("id") final long id,
                                    @ImageType @FormDataParam("image") final FormDataBodyPart type,
                                    @ImageSize @FormDataParam("image") final byte[] image) throws ImageNotFoundException, CarNotFoundException{
        LOGGER.debug("PUT request to update image of car with carId {}",id);
        carService.updateCarImage(id,image);
        return Response.noContent().contentLocation(uriInfo.getBaseUriBuilder().path(String.valueOf(id)).path("image/").build()).build();
    }


}
