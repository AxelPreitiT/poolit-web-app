package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.Pattern;

public class CreateCarForm {

    @Pattern(regexp = "([a-zA-Z]{2}\\s?\\d{3}\\s?[a-zA-Z]{2})|([a-zA-Z]{3}\\s?\\d{3})")
    private String carPlate;

    @Pattern(regexp = ".+")
    private String carInfo;

    public String getCarInfo() {
        return carInfo;
    }

    public String getCarPlate() {
        return carPlate;
    }
}
