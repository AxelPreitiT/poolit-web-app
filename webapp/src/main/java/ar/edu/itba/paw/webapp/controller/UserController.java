package ar.edu.itba.paw.webapp.controller;


import ar.edu.itba.paw.interfaces.exceptions.CityNotFoundException;
import ar.edu.itba.paw.interfaces.exceptions.EmailAlreadyExistsException;
import ar.edu.itba.paw.interfaces.exceptions.ImageNotFoundException;
import ar.edu.itba.paw.interfaces.exceptions.UserNotFoundException;
import ar.edu.itba.paw.interfaces.services.ImageService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.Image;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.controller.mediaType.VndType;
import ar.edu.itba.paw.webapp.dto.input.CreateUserDto;
import ar.edu.itba.paw.webapp.dto.output.UserRoleDto;
import ar.edu.itba.paw.webapp.dto.output.user.PrivateUserDto;
import ar.edu.itba.paw.webapp.dto.output.user.PublicUserDto;
import ar.edu.itba.paw.webapp.dto.validation.annotations.ImageSize;
import ar.edu.itba.paw.webapp.dto.validation.annotations.ImageType;
import ar.edu.itba.paw.webapp.form.CreateUserForm;
import ar.edu.itba.paw.webapp.form.UpdateUserForm;
import ar.edu.itba.paw.webapp.form.annotations.MPFile;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
import java.util.Optional;

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
    @Produces(VndType.APPLICATION_USER_PUBLIC)
    public Response getByIdPublic(@PathParam("id") final long id) throws UserNotFoundException{
        final User user = userService.findById(id).orElseThrow(UserNotFoundException::new);
        return Response.ok(PublicUserDto.fromUser(uriInfo,user)).build();
    }


//    TODO: ver por qué agrega "type" a la respuesta
    @GET
    @Path("/{id}")
    @Produces(VndType.APPLICATION_USER_PRIVATE)
    @PreAuthorize("@authValidator.checkIfWantedIsSelf(#id)") //TODO: ver por que lleva a 404
    public Response getByIdPrivate(@PathParam("id") final long id) throws UserNotFoundException{
        final User user = userService.findById(id).orElseThrow(UserNotFoundException::new);
        return Response.ok(PrivateUserDto.fromUser(uriInfo,user)).build();
    }

    @POST
    @Consumes( value = {MediaType.APPLICATION_JSON})
    public Response createUser(@Valid final CreateUserDto userDto) throws EmailAlreadyExistsException, CityNotFoundException {
        final User user = userService.createUser(userDto.getUsername(), userDto.getSurname(), userDto.getEmail(), userDto.getPhone(),
                userDto.getPassword(), userDto.getBornCityId(), userDto.getMailLocale(), null, new byte[0]);
        final URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(user.getUserId())).build();
        return Response.created(uri).build();
    }

    @GET
    @Path("/{id}/image")
    @Produces({"image/*"})
    public Response getUserImage(@PathParam("id") final long id) throws ImageNotFoundException, UserNotFoundException, IOException {
        final byte[] image = userService.getUserImage(id);

//        TODO: add caching capability
        return Response.ok(image).build();
    }

    @PUT
    @Path("/{id}/image")
    @PreAuthorize("@authValidator.checkIfWantedIsSelf(#id)")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response updateUserImage(@PathParam("id") final long id,
                                    @ImageType @FormDataParam("image") final FormDataBodyPart image,
                                    @ImageSize @FormDataParam("image") final byte[] content) throws ImageNotFoundException, UserNotFoundException{
        userService.updateUserImage(id,content);
        return Response.noContent().contentLocation(uriInfo.getBaseUriBuilder().path(String.valueOf(id)).path("image/").build()).build();
    }

    

//    @GET
//    @Path("/{id}")
//    @Produces(VndType.APPLICATION_USER_PASSENGER)
////    @PreAuthorize("@authValidator.checkIfWantedIsSelf(#id)")
//    public Response getByIdPassenger(@PathParam("id") final long id) throws UserNotFoundException{
//        final User user = userService.findById(id).orElseThrow(UserNotFoundException::new);
//        return Response.ok(UserDto.fromUser(uriInfo,user)).build();
//    }





    @PUT
    @Path("/{id}")
    @Produces( value = { MediaType.APPLICATION_JSON } )
    public Response modifyUser(@PathParam("id") final long id, @Valid final UpdateUserForm userForm) throws UserNotFoundException, IOException, CityNotFoundException {
        userService.modifyUser(id, userForm.getUsername(),userForm.getSurname(),userForm.getPhone(),userForm.getBornCityId(),userForm.getMailLocale(), userForm.getImageFile().getBytes());
        return Response.status(Response.Status.OK).build();
    }

    //TODO: revisar, lo necesitamos siempre al rol, pero como no lo cambiamos en el put lo pusimos aca
    @GET
    @Path("/{id}/role")
    @Produces( value = { MediaType.APPLICATION_JSON } )
    public Response getRole(@PathParam("id") final long id) throws UserNotFoundException{
        final User user = userService.findById(id).orElseThrow(UserNotFoundException::new);
        return Response.ok(UserRoleDto.fromString(user.getRole())).build();
    }

    @PUT
    @Path("/{id}/role")
    @Produces( value = { MediaType.APPLICATION_JSON } )
    public Response modifyRole(@PathParam("id") final long id, @Valid final UserRoleDto userRoleDto) throws UserNotFoundException{
        userService.changeRole(id,userRoleDto.getRole());
        return Response.status(Response.Status.OK).build();
    }

}
