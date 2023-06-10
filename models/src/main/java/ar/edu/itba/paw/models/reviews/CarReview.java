package ar.edu.itba.paw.models.reviews;

import ar.edu.itba.paw.models.Car;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.trips.Trip;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "car_reviews")
public class CarReview {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "car_review_id_seq")
    @SequenceGenerator(sequenceName = "car_review_id_seq", name = "car_review_id_seq", allocationSize = 1)
    @Column(name = "review_id")
    private Long reviewId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "trip_id")
    protected Trip trip;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "car_id")
    private Car car;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "reviewer_id")
    private User reviewer;

    @Column(name = "rating", nullable = false)
    private int rating;

    @Column(name = "comment")
    private String comment;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CarReviewOptions option;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    protected CarReview() {
        // Just for Hibernate
    }

    public CarReview(Trip trip, User reviewer, Car car, int rating, String comment, CarReviewOptions option, LocalDateTime date) {
        this.trip = trip;
        this.car = car;
        this.reviewer = reviewer;
        this.rating = rating;
        this.comment = comment;
        this.option = option;
        this.date = date;
    }

    public CarReview(Trip trip, User reviewer, Car car, int rating, String comment, CarReviewOptions option) {
        this(trip, reviewer, car, rating, comment, option, LocalDateTime.now());
    }

    @Override
    public String toString() {
        return String.format("CarReview { id: %d, carId: %d, reviewerId: %d, rating: %d, option: '%s', comment: '%s' }",
                reviewId, car.getCarId(), reviewer.getUserId(), rating, option, comment);
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public Long getReviewId() {
        return reviewId;
    }

    public Trip getTrip() {
        return trip;
    }

    public Car getCar() {
        return car;
    }

    public User getReviewer() {
        return reviewer;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public CarReviewOptions getOption() {
        return option;
    }
}
