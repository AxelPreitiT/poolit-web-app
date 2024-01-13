package ar.edu.itba.paw.webapp.dto.output.user;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.controller.utils.UrlHolder;

import javax.ws.rs.core.UriInfo;
import java.net.URI;

public class PublicUserDto {
    private String username;
    private String surname;
    private double driverRating;
    private double passengerRating;
    private URI selfUri;
    private URI imageUri;

    public PublicUserDto(){}

    protected PublicUserDto(final UriInfo uriInfo, final User user){
        this.username = user.getName();
        this.surname= user.getSurname();
        this.driverRating = user.getDriverRating();
        this.passengerRating = user.getPassengerRating();
        this.selfUri = uriInfo.getBaseUriBuilder().path(UrlHolder.USER_BASE).path(String.valueOf(user.getUserId())).build();
        this.imageUri = uriInfo.getBaseUriBuilder().path(UrlHolder.USER_BASE).path(String.valueOf(user.getUserId())).path(UrlHolder.IMAGE_ENTITY).build();
    }

    public static PublicUserDto fromUser(final UriInfo uriInfo, final User user){
        return new PublicUserDto(uriInfo,user);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public URI getSelfUri() {
        return selfUri;
    }

    public void setSelfUri(URI selfUri) {
        this.selfUri = selfUri;
    }

    public URI getImageUri() {
        return imageUri;
    }

    public void setImageUri(URI imageUri) {
        this.imageUri = imageUri;
    }

    public double getDriverRating() {
        return driverRating;
    }

    public void setDriverRating(double driverRating) {
        this.driverRating = driverRating;
    }

    public double getPassengerRating() {
        return passengerRating;
    }

    public void setPassengerRating(double passengerRating) {
        this.passengerRating = passengerRating;
    }
}
