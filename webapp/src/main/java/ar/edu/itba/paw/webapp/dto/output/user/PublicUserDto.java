package ar.edu.itba.paw.webapp.dto.output.user;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.controller.utils.UrlHolder;

import javax.ws.rs.core.UriInfo;
import java.net.URI;

public class PublicUserDto {
    private long userId;
    private String username;
    private String surname;
    private double driverRating;
    private double passengerRating;
    private int tripCount;
    private URI reviewsDriverUri;
    private URI reviewsPassengerUri;
    private URI selfUri;
    private URI imageUri;

    public PublicUserDto(){}

    protected PublicUserDto(final UriInfo uriInfo, final User user){
        this.userId = user.getUserId();
        this.username = user.getName();
        this.surname= user.getSurname();
        this.driverRating = user.getDriverRating();
        this.passengerRating = user.getPassengerRating();
        this.tripCount = user.getTripCount();
        this.reviewsDriverUri = uriInfo.getBaseUriBuilder().path(UrlHolder.DRIVER_REVIEWS_BASE).queryParam("forUser",user.getUserId()).build();
        this.reviewsPassengerUri = uriInfo.getBaseUriBuilder().path(UrlHolder.PASSENGER_REVIEWS_BASE).queryParam("forUser",user.getUserId()).build();
        this.selfUri = uriInfo.getBaseUriBuilder().path(UrlHolder.USER_BASE).path(String.valueOf(user.getUserId())).build();
        this.imageUri = uriInfo.getBaseUriBuilder().path(UrlHolder.USER_BASE).path(String.valueOf(user.getUserId())).path(UrlHolder.IMAGE_ENTITY).build();
    }

    public static PublicUserDto fromUser(final UriInfo uriInfo, final User user){
        return new PublicUserDto(uriInfo,user);
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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

    public int getTripCount() { return tripCount; }

    public void setTripCount(int tripCount) { this.tripCount = tripCount; }


    public URI getReviewsDriverUri() { return reviewsDriverUri; }

    public void setReviewsDriverUri(URI reviewsDriverUri) { this.reviewsDriverUri = reviewsDriverUri; }

    public URI getReviewsPassengerUri() { return reviewsPassengerUri; }

    public void setReviewsPassengerUri(URI reviewsPassengerUri) { this.reviewsPassengerUri = reviewsPassengerUri; }
}
