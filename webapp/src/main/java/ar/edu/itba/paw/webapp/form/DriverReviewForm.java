package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.models.reviews.DriverReviewOptions;

public class DriverReviewForm extends ReviewForm {

    private DriverReviewOptions option;

    public DriverReviewOptions getOption() {
        return option;
    }

    public void setOption(DriverReviewOptions option) {
        this.option = option;
    }
}
