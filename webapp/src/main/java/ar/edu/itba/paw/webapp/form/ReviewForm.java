package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.Pattern;

public class ReviewForm {

    private int rating;

    @Pattern(regexp = ".+")
    private String review;

    public int getRating() {
        return rating;
    }

    public String getReview() {
        return review;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
