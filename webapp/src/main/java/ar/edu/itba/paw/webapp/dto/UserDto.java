package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.User;
import javax.ws.rs.core.UriInfo;

import java.net.URI;

public class UserDto {

    private String name;

    private String surname;

    //TODO: revisar como no devolverlo siempre
    //Podemos tener /user/id/contact
    private String email;
    private String phone;
    private URI self;
    public static UserDto fromUser(final UriInfo uriInfo, final User user){
       final UserDto dto = new UserDto();
       dto.name= user.getName();
       dto.surname= user.getSurname();
       dto.email= user.getEmail();
       dto.phone=user.getPhone();
       dto.self= uriInfo.getBaseUriBuilder().path("/api/users/").path(String.valueOf(user.getUserId())).build();
       return dto;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public URI getSelf() {
        return self;
    }

    public void setSelf(URI self) {
        this.self = self;
    }
}
