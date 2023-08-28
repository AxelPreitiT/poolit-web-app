package ar.edu.itba.paw.webapp.controller;


import ar.edu.itba.paw.interfaces.exceptions.CityNotFoundException;
import ar.edu.itba.paw.interfaces.exceptions.EmailAlreadyExistsException;
import ar.edu.itba.paw.interfaces.exceptions.UserNotFoundException;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.dto.UserDto;
import ar.edu.itba.paw.webapp.form.CreateUserForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;
import java.net.URI;

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

    @POST
    @Produces( value = {MediaType.APPLICATION_JSON})
    public Response createUser(final CreateUserForm userForm) throws EmailAlreadyExistsException, CityNotFoundException, IOException {
        final User user = userService.createUser(userForm.getUsername(), userForm.getSurname(), userForm.getEmail(), userForm.getPhone(),
                userForm.getPassword(), userForm.getBornCityId(), userForm.getMailLocale(), null, userForm.getImageFile().getBytes());
        final URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(user.getUserId())).build();
        return Response.created(uri).build();
    }

}
