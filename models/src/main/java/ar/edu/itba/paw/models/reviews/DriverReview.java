package ar.edu.itba.paw.models.reviews;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.trips.Trip;

import javax.persistence.*;

@Entity
@Table(name = "driver_reviews")
public class DriverReview extends UserReview {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DriverReviewOptions option;

    protected DriverReview() {
        // Just for Hibernate
    }

    public DriverReview(Trip trip, User reviewer, User reviewed, int rating, String comment, DriverReviewOptions option) {
        super(trip, reviewer, reviewed, rating, comment);
        this.option = option;
    }

    @Override
    public String toString() {
        return String.format("DriverReview { id: %d, reviewerId: %d, reviewedId: %d, tripId: %d, rating: %d, option: '%s', comment: '%s' }",
                reviewId, reviewer.getUserId(), reviewed.getUserId(), trip.getTripId(), rating, option, comment);
    }

    public DriverReviewOptions getOption() {
        return option;
    }
}
