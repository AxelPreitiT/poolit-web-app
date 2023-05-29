package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class ReviewForm {

    @Min(value = 1)
    @Max(value = 5)
    private int rating;

    @Size(min = 5, max = 500)
    private String review;

    private boolean failedReview = false;

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

    public boolean isFailedReview() {
        return failedReview;
    }

    public void setFailedReview(boolean failedReview) {
        this.failedReview = failedReview;
    }
}
