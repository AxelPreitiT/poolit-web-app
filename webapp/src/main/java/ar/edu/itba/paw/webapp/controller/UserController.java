package ar.edu.itba.paw.webapp.controller;


import ar.edu.itba.paw.interfaces.exceptions.*;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.Image;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.controller.mediaType.VndType;
import ar.edu.itba.paw.webapp.controller.utils.ControllerUtils;
import ar.edu.itba.paw.webapp.controller.utils.UrlHolder;
import ar.edu.itba.paw.webapp.controller.utils.queryBeans.ImageQuery;
import ar.edu.itba.paw.webapp.dto.input.CreateUserDto;
import ar.edu.itba.paw.webapp.dto.input.UpdateUserDto;
import ar.edu.itba.paw.webapp.dto.output.UserRoleDto;
import ar.edu.itba.paw.webapp.dto.output.user.DriverUserDto;
import ar.edu.itba.paw.webapp.dto.output.user.PrivateUserDto;
import ar.edu.itba.paw.webapp.dto.output.user.PublicUserDto;
import ar.edu.itba.paw.webapp.dto.validation.annotations.ImageSize;
import ar.edu.itba.paw.webapp.dto.validation.annotations.ImageType;
import ar.edu.itba.paw.webapp.exceptions.ResourceNotFoundException;
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
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.Arrays;

@Path(UrlHolder.USER_BASE)
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
    public Response getByIdPublic(@PathParam("id") final long id){
        LOGGER.debug("GET request for public userId {}",id);
        final User user = userService.findById(id).orElseThrow(ResourceNotFoundException::new);
        return Response.ok(PublicUserDto.fromUser(uriInfo,user)).build();
    }

    @GET
    @Path("/{id}")
    @Produces(VndType.APPLICATION_USER_DRIVER)
    @PreAuthorize("@authValidator.checkIfUserIsPassengerOf(#id) or @authValidator.checkIfWantedIsSelf(#id)")
    public Response getByIdDriver(@PathParam("id") final long id){
        LOGGER.debug("GET request for public userId {}",id);
        final User user = userService.findById(id).orElseThrow(ResourceNotFoundException::new);
        return Response.ok(DriverUserDto.fromUser(uriInfo,user)).build();
    }


    @GET
    @Path("/{id}")
    @Produces(VndType.APPLICATION_USER_PRIVATE)
    @PreAuthorize("@authValidator.checkIfWantedIsSelf(#id)")
    public Response getByIdPrivate(@PathParam("id") final long id){
        LOGGER.debug("GET request for private userId {}",id);
        final User user = userService.findById(id).orElseThrow(ResourceNotFoundException::new);
        return Response.ok(PrivateUserDto.fromUser(uriInfo,user)).build();
    }

    @POST
    @Consumes( value = VndType.APPLICATION_USER)
    public Response createUser(@Valid final CreateUserDto userDto) throws EmailAlreadyExistsException, CityNotFoundException {
        LOGGER.debug("POST request to create user");
        final User user = userService.createUser(userDto.getUsername(), userDto.getSurname(), userDto.getEmail(), userDto.getPhone(),
                userDto.getPassword(), userDto.getBornCityId(), userDto.getMailLocale(), null, new byte[0]);
        final URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(user.getUserId())).build();
        return Response.created(uri).build();
    }

    @PATCH
    @Path("/{id}")
    @Consumes(value = VndType.APPLICATION_USER)
    public Response modifyUser(@PathParam("id") final long id, @Valid final UpdateUserDto userForm) throws UserNotFoundException, CityNotFoundException {
        LOGGER.debug("PUT request to update user with userId {}",id);
        userService.modifyUser(id, userForm.getUsername(),userForm.getSurname(),userForm.getPhone(),userForm.getBornCityId(),userForm.getMailLocale());
        return Response.noContent().build();
    }
    @PATCH
    @Path("/{id}/")
    @Consumes( value = { VndType.APPLICATION_USER_ROLE } )
    public Response modifyRole(@PathParam("id") final long id, @Valid final UserRoleDto userRoleDto) throws UserNotFoundException, RoleAlreadyChangedException {
        LOGGER.debug("PUT request for role of user with userId {}",id);
        userService.changeRole(id,userRoleDto.getRole());
        return Response.noContent().build();
    }

    @GET
    @Path("/{id}/image")
    @Produces({"image/*"})
    public Response getUserImage(@PathParam("id") final long id,
                                 @Valid @BeanParam final ImageQuery query,
                                 @Context Request request) throws ImageNotFoundException, UserNotFoundException {
        LOGGER.debug("GET request for image of user with userId {}",id);
        final Image image = userService.getUserImage(id,query.getImageSize());
        return ControllerUtils.getConditionalCacheResponse(request,image.getData(query.getImageSize()), image.getImageId());
//        return Response.ok(image).build();
    }

    @PUT
    @Path("/{id}/image")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response updateUserImage(@PathParam("id") final long id,
                                    @ImageType @FormDataParam("image") final FormDataBodyPart type,
                                    @ImageSize @FormDataParam("image") final byte[] image) throws ImageNotFoundException, UserNotFoundException{
        LOGGER.debug("PUT request to update image of user with userId {}",id);
        userService.updateUserImage(id,image);
        return Response.noContent().contentLocation(uriInfo.getBaseUriBuilder().path(String.valueOf(id)).path("image/").build()).build();
    }

}
