package ar.edu.itba.paw.webapp.dto.output.reviews;

import ar.edu.itba.paw.models.reviews.PassengerReview;
import ar.edu.itba.paw.models.reviews.PassengerReviewOptions;
import ar.edu.itba.paw.webapp.controller.utils.UrlHolder;

import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.time.LocalDateTime;

public class PassengerReviewDto {

    private long id;
    private int rating;
    private String comment;
    private PassengerReviewOptions option;
    private LocalDateTime reviewDateTime;
    private URI tripUri;
    private URI reviewedUserUri;
    private URI selfUri;

    public static PassengerReviewDto fromPassengerReview(final UriInfo uriInfo, final PassengerReview passengerReview){
        PassengerReviewDto ans = new PassengerReviewDto();
        ans.id = passengerReview.getReviewId();
        ans.rating = passengerReview.getRating();
        ans.comment = passengerReview.getComment();
        ans.option = passengerReview.getOption();
        ans.reviewDateTime = passengerReview.getDate();
        ans.tripUri = uriInfo.getBaseUriBuilder().path(UrlHolder.TRIPS_BASE).path(String.valueOf(passengerReview.getTrip().getTripId())).build();
        ans.reviewedUserUri = uriInfo.getBaseUriBuilder().path(UrlHolder.USER_BASE).path(String.valueOf(passengerReview.getReviewed().getUserId())).build();
        ans.selfUri = uriInfo.getBaseUriBuilder().path(UrlHolder.PASSENGER_REVIEWS_BASE).path(String.valueOf(passengerReview.getReviewId())).build();
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

    public PassengerReviewOptions getOption() {
        return option;
    }

    public void setOption(PassengerReviewOptions option) {
        this.option = option;
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

    public URI getReviewedUserUri() {
        return reviewedUserUri;
    }

    public void setReviewedUserUri(URI reviewedUserUri) {
        this.reviewedUserUri = reviewedUserUri;
    }

    public URI getSelfUri() {
        return selfUri;
    }

    public void setSelfUri(URI selfUri) {
        this.selfUri = selfUri;
    }
}
