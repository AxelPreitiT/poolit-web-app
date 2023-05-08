package ar.edu.itba.paw.models;

public class Review {

    private final long reviewId;
    private final long travelId;

    private final User user;

    private final int rating;

    private final String review;

    public Review(long reviewId, long travelId, User user, int rating, String review) {
        this.reviewId = reviewId;
        this.travelId = travelId;
        this.user = user;
        this.rating = rating;
        this.review = review;
    }

    public long getReviewId() {
        return reviewId;
    }

    public long getTravelId() {
        return travelId;
    }

    public User getUser() {
        return user;
    }

    public long getDriver() {
        return travelId;
    }

    public String getReview() {
        return review;
    }

    public int getRating() {
        return rating;
    }
}
