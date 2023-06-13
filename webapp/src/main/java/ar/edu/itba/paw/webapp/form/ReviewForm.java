package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public abstract class ReviewForm {

    @Min(value = 1)
    @Max(value = 5)
    private int rating;

    private String comment;

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
