package ar.edu.itba.paw.webapp.dto.input.reviews;

import ar.edu.itba.paw.models.reviews.CarReviewOptions;
import ar.edu.itba.paw.webapp.dto.validation.annotations.ValidReviewOption;

import javax.validation.constraints.NotNull;

@ValidReviewOption(reviewOption = "option")
public class CreateCarReviewDto extends CreateReviewDto{
    @NotNull
    private CarReviewOptions option;

    public CarReviewOptions getOption() {
        return option;
    }

    public void setOption(CarReviewOptions option) {
        this.option = option;
    }
}
