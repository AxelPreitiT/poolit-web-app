package ar.edu.itba.paw.webapp.controller;


import ar.edu.itba.paw.interfaces.services.CarBrandService;
import ar.edu.itba.paw.models.CarBrand;
import ar.edu.itba.paw.webapp.controller.utils.ControllerUtils;
import ar.edu.itba.paw.webapp.controller.utils.UrlHolder;
import ar.edu.itba.paw.webapp.dto.output.CarBrandDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path(UrlHolder.CAR_BRAND_BASE)
public class CarBrandController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarBrandController.class);

    @Context
    private UriInfo uriInfo;

    private CarBrandService carBrandService;


    @Inject
    @Autowired
    public CarBrandController(final CarBrandService carBrandService){
        this.carBrandService = carBrandService;
    }

    @GET
    public Response getCarBrands(){
        LOGGER.debug("GET request for all car brands");
        List<CarBrand> ans = carBrandService.getCarBrands();
        Response.ResponseBuilder res = Response.ok(new GenericEntity<List<CarBrandDto>>(ans.stream().map(e -> CarBrandDto.fromCarBrand(uriInfo,e)).collect(Collectors.toList())){});
        return ControllerUtils.getUnconditionalCacheResponseBuilder(res).build();
    }

    @GET
    @Path("{id}")
    public Response getCarBrand(@PathParam("id") final String id){
        LOGGER.debug("GET request for car brand with id {}",id);
        Optional<CarBrand> ans = carBrandService.findById(id);
        if(!ans.isPresent()){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        Response.ResponseBuilder res = Response.ok(CarBrandDto.fromCarBrand(uriInfo,ans.get()));
        return ControllerUtils.getUnconditionalCacheResponseBuilder(res).build();
    }

}
