package ar.edu.itba.paw.models.reviews;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.trips.Trip;

import javax.persistence.*;
import java.time.LocalDateTime;

@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name = "user_reviews")
public class UserReview {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_review_id_seq")
    @SequenceGenerator(sequenceName = "user_review_id_seq", name = "user_review_id_seq", allocationSize = 1)
    @Column(name = "review_id")
    protected Long reviewId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "trip_id")
    protected Trip trip;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "reviewer_id")
    protected User reviewer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "reviewed_id")
    protected User reviewed;

    @Column(name = "rating", nullable = false)
    protected int rating;

    @Column(name = "comment")
    protected String comment;

    @Column(name = "date", nullable = false)
    protected LocalDateTime date;

    protected UserReview() {
        // Just for Hibernate
    }

    public UserReview(Trip trip, User reviewer, User reviewed, int rating, String extraInfo, LocalDateTime date) {
        this.reviewer = reviewer;
        this.reviewed = reviewed;
        this.trip = trip;
        this.rating = rating;
        this.comment = extraInfo;
        this.date = date;
    }

    public UserReview(Trip trip, User reviewer, User reviewed, int rating, String extraInfo) {
        this(trip, reviewer, reviewed, rating, extraInfo, LocalDateTime.now());
    }

    @Override
    public String toString() {
        return String.format("UserReview { id: %d, reviewerId: %d, reviewedId: %d, tripId: %d, rating: %d, comment: '%s' }",
                reviewId, reviewer.getUserId(), reviewed.getUserId(), trip.getTripId(), rating, comment);
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public Long getReviewId() {
        return reviewId;
    }

    public User getReviewer() {
        return reviewer;
    }

    public User getReviewed() {
        return reviewed;
    }

    public Trip getTrip() {
        return trip;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }
}
