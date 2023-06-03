package ar.edu.itba.paw.models;

import ar.edu.itba.paw.models.trips.Trip;

import javax.persistence.*;

@Entity
@Table(name = "car_review")
public class ReviewCar {
    @Id
    @GeneratedValue(strategy =  GenerationType.SEQUENCE, generator ="reviews_review_id_seq" )
    @SequenceGenerator(sequenceName = "reviews_review_id_seq" , name = "reviews_review_id_seq", allocationSize = 1)
    @Column(name = "review_id")
    private Long reviewId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "car_id")
    private Car car;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "rating")
    private int rating;

    @Column(name = "review")
    private String review;

    public ReviewCar(User user, int rating, String review, Car car) {
        this.user = user;
        this.rating = rating;
        this.review = review;
        this.car = car;
    }

    protected ReviewCar(){

    }

    public Long getReviewId() {
        return reviewId;
    }

    public Car getCar() {
        return car;
    }

    public User getUser() {
        return user;
    }

    public int getRating() {
        return rating;
    }

    public String getReview() {
        return review;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
