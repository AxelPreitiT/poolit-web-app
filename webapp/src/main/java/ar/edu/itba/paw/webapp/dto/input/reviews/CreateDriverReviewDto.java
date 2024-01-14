package ar.edu.itba.paw.webapp.dto.input.reviews;

import ar.edu.itba.paw.models.reviews.DriverReviewOptions;
import ar.edu.itba.paw.webapp.dto.validation.annotations.ValidOption;

import javax.validation.constraints.NotNull;

@ValidOption(listableField = "option")
public class CreateDriverReviewDto extends CreateReviewDto{
    @NotNull
    private DriverReviewOptions option;

    private int driverId;

    public DriverReviewOptions getOption() {
        return option;
    }

    public void setOption(DriverReviewOptions option) {
        this.option = option;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }
}
