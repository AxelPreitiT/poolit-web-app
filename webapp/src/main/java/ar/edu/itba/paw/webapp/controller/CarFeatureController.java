package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.CarFeatureService;
import ar.edu.itba.paw.models.FeatureCar;
import ar.edu.itba.paw.webapp.controller.mediaType.VndType;
import ar.edu.itba.paw.webapp.controller.utils.ControllerUtils;
import ar.edu.itba.paw.webapp.controller.utils.UrlHolder;
import ar.edu.itba.paw.webapp.dto.output.CarFeatureDto;
import ar.edu.itba.paw.webapp.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.stream.Collectors;


@Path(UrlHolder.CAR_FEATURE_BASE)
@Component
public class CarFeatureController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarFeatureController.class);

    private final CarFeatureService carFeatureService;

    private final MessageSource messageSource;

    @Context
    private UriInfo uriInfo;

    @Autowired
    @Inject
    public CarFeatureController(final CarFeatureService carFeatureService, final MessageSource messageSource){
        this.carFeatureService = carFeatureService;
        this.messageSource = messageSource;
    }

    @GET
    @Produces(value = VndType.APPLICATION_CAR_FEATURE_LIST)
    public Response getCarFeatures(){
        LOGGER.debug("GET request for all car features");
        List<FeatureCar> ans = carFeatureService.getCarFeatures();
        Response.ResponseBuilder res = Response.ok(new GenericEntity<List<CarFeatureDto>>(ans.stream().map(e -> CarFeatureDto.fromCarFeature(uriInfo,e,messageSource)).collect(Collectors.toList())){});
        return ControllerUtils.getUnconditionalCacheResponseBuilder(res).build();
    }

    @GET
    @Path("{id}")
    @Produces(value = VndType.APPLICATION_CAR_FEATURE)
    public Response getCarFeature(@PathParam("id") final String id){
        LOGGER.debug("GET request for car feature with id {}",id);
        FeatureCar ans = carFeatureService.findById(id).orElseThrow(ResourceNotFoundException::new);
        Response.ResponseBuilder res = Response.ok(CarFeatureDto.fromCarFeature(uriInfo,ans,messageSource));
        return ControllerUtils.getUnconditionalCacheResponseBuilder(res).build();
    }
}
