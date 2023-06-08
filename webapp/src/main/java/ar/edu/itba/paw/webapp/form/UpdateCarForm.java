package ar.edu.itba.paw.webapp.form;


import ar.edu.itba.paw.models.FeatureCar;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.List;

public class UpdateCarForm {

    @Size(min = 5, max = 100)
    private String carInfo;


    @Min(value = 1)
    private int seats;

    private List<FeatureCar> features;

    public List<FeatureCar> getFeatures() { return features; }

    public void setFeatures(List<FeatureCar> features) { this.features = features; }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public int getSeats() {
        return seats;
    }

    public String getCarInfo() {
        return carInfo;
    }

    public void setCarInfo(String carInfo) {
        this.carInfo = carInfo;
    }

}
