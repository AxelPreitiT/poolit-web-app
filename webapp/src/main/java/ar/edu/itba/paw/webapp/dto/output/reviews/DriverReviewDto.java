package ar.edu.itba.paw.webapp.dto.output.reviews;

import ar.edu.itba.paw.models.reviews.DriverReview;
import ar.edu.itba.paw.models.reviews.DriverReviewOptions;
import ar.edu.itba.paw.webapp.controller.utils.UrlHolder;

import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.time.LocalDateTime;

public class DriverReviewDto {
    private long id;
    private int rating;
    private String comment;
    private DriverReviewOptions option;
    private LocalDateTime reviewDateTime;
    private URI tripUri;
    private URI reviewedUserUri;
    private URI selfUri;

    public static DriverReviewDto fromDriverReview(final UriInfo uriInfo, final DriverReview driverReview){
        DriverReviewDto ans = new DriverReviewDto();
        ans.id = driverReview.getReviewId();
        ans.rating = driverReview.getRating();
        ans.comment = driverReview.getComment();
        ans.option = driverReview.getOption();
        ans.reviewDateTime = driverReview.getDate();
        ans.tripUri = uriInfo.getBaseUriBuilder().path(UrlHolder.TRIPS_BASE).path(String.valueOf(driverReview.getTrip().getTripId())).build();
        ans.reviewedUserUri = uriInfo.getBaseUriBuilder().path(UrlHolder.USER_BASE).path(String.valueOf(driverReview.getReviewed().getUserId())).build();
        ans.selfUri = uriInfo.getBaseUriBuilder().path(UrlHolder.DRIVER_REVIEWS_BASE).path(String.valueOf(driverReview.getReviewId())).build();
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

    public DriverReviewOptions getOption() {
        return option;
    }

    public void setOption(DriverReviewOptions option) {
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
