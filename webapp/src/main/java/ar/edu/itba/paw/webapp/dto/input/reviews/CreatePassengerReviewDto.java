package ar.edu.itba.paw.webapp.dto.input.reviews;
import ar.edu.itba.paw.models.reviews.PassengerReviewOptions;
import ar.edu.itba.paw.webapp.dto.validation.annotations.ValidReviewOption;

import javax.validation.constraints.NotNull;


@ValidReviewOption(reviewOption = "option")
public class CreatePassengerReviewDto extends CreateReviewDto{
    @NotNull
    private PassengerReviewOptions option;

    private int passengerId;

    public PassengerReviewOptions getOption() {
        return option;
    }

    public void setOption(PassengerReviewOptions option) {
        this.option = option;
    }

    public int getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(int passengerId) {
        this.passengerId = passengerId;
    }
}
