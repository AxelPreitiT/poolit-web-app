package ar.edu.itba.paw.models;

public class Review {

    private final long reviewId;
    private final long tripId;

    private final User user;

    private final int rating;

    private final String review;

    public Review(long reviewId, long tripId, User user, int rating, String review) {
        this.reviewId = reviewId;
        this.tripId = tripId;
        this.user = user;
        this.rating = rating;
        this.review = review;
    }

    @Override
    public String toString() {
        return String.format("Review { id: %d, tripId: %d, userId: %d, rating: %d, review: '%s' }",
                reviewId, tripId, user.getUserId(), rating, review);
    }

    public long getReviewId() {
        return reviewId;
    }

    public long getTripId() {
        return tripId;
    }

    public User getUser() {
        return user;
    }

    public long getDriver() {
        return tripId;
    }

    public String getReview() {
        return review;
    }

    public int getRating() {
        return rating;
    }
}
