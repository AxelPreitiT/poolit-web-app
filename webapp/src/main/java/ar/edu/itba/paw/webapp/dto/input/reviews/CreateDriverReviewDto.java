package ar.edu.itba.paw.webapp.dto.input.reviews;

import ar.edu.itba.paw.models.reviews.DriverReviewOptions;
import ar.edu.itba.paw.webapp.dto.validation.annotations.ValidOption;

import javax.validation.constraints.NotNull;

@ValidOption(listableField = "option")
public class CreateDriverReviewDto extends CreateReviewDto{
    @NotNull
    private DriverReviewOptions option;

    @NotNull
    private Integer driverId;

    public DriverReviewOptions getOption() {
        return option;
    }

    public void setOption(DriverReviewOptions option) {
        this.option = option;
    }

    public Integer getDriverId() {
        return driverId;
    }

    public void setDriverId(Integer driverId) {
        this.driverId = driverId;
    }
}
