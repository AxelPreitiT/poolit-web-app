package ar.edu.itba.paw.models;

import ar.edu.itba.paw.models.trips.Trip;

import javax.persistence.*;

@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy =  GenerationType.SEQUENCE, generator ="reviews_review_id_seq" )
    @SequenceGenerator(sequenceName = "reviews_review_id_seq" , name = "reviews_review_id_seq", allocationSize = 1)
    @Column(name = "review_id")
    private Long reviewId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "trip_id")
    private Trip trip;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "rating")
    private int rating;

    @Column(name = "review")
    private String review;

//    public Review(long reviewId, Trip trip , User user, int rating, String review) {
//        this.reviewId = reviewId;
//        this.trip = trip;
//        this.user = user;
//        this.rating = rating;
//        this.review = review;
//    }

    public Review(Trip trip, User user, int rating, String review) {
        this.trip = trip;
        this.user = user;
        this.rating = rating;
        this.review = review;
    }

    protected Review(){

    }

    @Override
    public String toString() {
        return String.format("Review { id: %d, tripId: %d, userId: %d, rating: %d, review: '%s' }",
                reviewId, trip.getTripId(), user.getUserId(), rating, review);
    }

    public long getReviewId() {
        return reviewId;
    }

    public long getTripId() {
        return trip.getTripId();
    }

    public User getUser() {
        return user;
    }


    public String getReview() {
        return review;
    }

    public int getRating() {
        return rating;
    }
}
