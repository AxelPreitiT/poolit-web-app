package ar.edu.itba.paw.webapp.dto.user;

import ar.edu.itba.paw.models.User;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

public class PublicUserDto {
    private String name;
    private String surname;
    private URI self;
    private URI image;

    public PublicUserDto(){}

    protected PublicUserDto(final UriInfo uriInfo, final User user){
        this.name= user.getName();
        this.surname= user.getSurname();
        this.self= uriInfo.getBaseUriBuilder().path("/api/users/").path(String.valueOf(user.getUserId())).build();
        this.image= uriInfo.getBaseUriBuilder().path("/api/users/").path(String.valueOf(user.getUserId())).path("image/").build();
    }

    public static PublicUserDto fromUser(final UriInfo uriInfo, final User user){
        return new PublicUserDto(uriInfo,user);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public URI getSelf() {
        return self;
    }

    public void setSelf(URI self) {
        this.self = self;
    }

    public URI getImage() {
        return image;
    }

    public void setImage(URI image) {
        this.image = image;
    }
}
