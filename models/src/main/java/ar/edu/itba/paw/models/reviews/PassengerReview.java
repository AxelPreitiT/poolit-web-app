package ar.edu.itba.paw.models.reviews;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.trips.Trip;

import javax.persistence.*;

@Entity
@Table(name = "passenger_reviews")
public class PassengerReview extends UserReview {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PassengerReviewOptions option;

    protected PassengerReview() {
        // Just for Hibernate
    }

    public PassengerReview(Trip trip, User reviewer, User reviewed, int rating, String comment, PassengerReviewOptions option) {
        super(trip, reviewer, reviewed, rating, comment);
        this.option = option;
    }

    @Override
    public String toString() {
        return String.format("PassengerReview { id: %d, reviewerId: %d, reviewedId: %d, tripId: %d, rating: %d, option: '%s', comment: '%s' }",
                reviewId, reviewer.getUserId(), reviewed.getUserId(), trip.getTripId(), rating, option, comment);
    }

    public PassengerReviewOptions getOption() {
        return option;
    }
}
