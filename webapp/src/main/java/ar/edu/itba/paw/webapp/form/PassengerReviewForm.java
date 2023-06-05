package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.models.reviews.PassengerReviewOptions;

public class PassengerReviewForm extends ReviewForm {

    private PassengerReviewOptions option;

    public PassengerReviewOptions getOption() {
        return option;
    }

    public void setOption(PassengerReviewOptions option) {
        this.option = option;
    }
}
