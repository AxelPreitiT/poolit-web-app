package ar.edu.itba.paw.webapp.controller;


import ar.edu.itba.paw.interfaces.exceptions.UserNotFoundException;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/api/users")
@Component
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;


    @Context
    private UriInfo uriInfo;

    @Inject
    @Autowired
    public UserController(final UserService userService){
        this.userService = userService;
    }

    @GET
    @Path("/{id}")
    @Produces( value = { MediaType.APPLICATION_JSON } )
    public Response getById(@PathParam("id") final long id) throws UserNotFoundException{
        final User user = userService.findById(id).orElseThrow(UserNotFoundException::new);
        return Response.ok(UserDto.fromUser(uriInfo,user)).build();
    }

}
