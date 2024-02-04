package ar.edu.itba.paw.webapp.dto.output.reviews;

import ar.edu.itba.paw.models.reviews.CarReview;
import ar.edu.itba.paw.models.reviews.CarReviewOptions;
import ar.edu.itba.paw.webapp.controller.utils.UrlHolder;

import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.time.LocalDateTime;

public class CarReviewDto {
    private long id;
    private int rating;
    private String comment;
    private CarReviewOptions option;
    private LocalDateTime reviewDateTime;
    private URI tripUri;
    private URI carUri;
    private URI selfUri;

    public static CarReviewDto fromCarReview(final UriInfo uriInfo, final CarReview carReview){
        CarReviewDto ans = new CarReviewDto();
        ans.id = carReview.getReviewId();
        ans.rating = carReview.getRating();
        ans.comment = carReview.getComment();
        ans.option = carReview.getOption();
        ans.reviewDateTime = carReview.getDate();
        ans.tripUri = uriInfo.getBaseUriBuilder().path(UrlHolder.TRIPS_BASE).path(String.valueOf(carReview.getTrip().getTripId())).build();
        ans.carUri = uriInfo.getBaseUriBuilder().path(UrlHolder.CAR_BASE).path(String.valueOf(carReview.getCar().getCarId())).build();
        ans.selfUri = uriInfo.getBaseUriBuilder().path(UrlHolder.CAR_BASE).path(String.valueOf(carReview.getCar().getCarId())).path(UrlHolder.REVIEWS_ENTITY).path(String.valueOf(carReview.getReviewId())).build();
        return ans;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public CarReviewOptions getOption() {
        return option;
    }

    public void setOption(CarReviewOptions options) {
        this.option = options;
    }

    public LocalDateTime getReviewDateTime() {
        return reviewDateTime;
    }

    public void setReviewDateTime(LocalDateTime reviewDateTime) {
        this.reviewDateTime = reviewDateTime;
    }

    public URI getTripUri() {
        return tripUri;
    }

    public void setTripUri(URI tripUri) {
        this.tripUri = tripUri;
    }

    public URI getCarUri() {
        return carUri;
    }

    public void setCarUri(URI carUri) {
        this.carUri = carUri;
    }

    public URI getSelfUri() {
        return selfUri;
    }

    public void setSelfUri(URI selfUri) {
        this.selfUri = selfUri;
    }
}
