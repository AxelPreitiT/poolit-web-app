package ar.edu.itba.paw.webapp.dto.input;

import ar.edu.itba.paw.models.CarBrand;
import ar.edu.itba.paw.models.FeatureCar;
import ar.edu.itba.paw.webapp.dto.validation.annotations.NotSamePlate;
import ar.edu.itba.paw.webapp.dto.validation.annotations.Plate;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class CreateCarDto {

    @NotNull
    @Size(min = 1)
    @Plate
    @NotSamePlate
    private String plate;

    @NotNull
    @Size(min = 5, max = 100)
    private String carInfo;

    @Min(value = 1)
    private int seats;

    @NotNull
    private CarBrand carBrand;

    private List<FeatureCar> features;

    public List<FeatureCar> getFeatures() { return features; }

    public void setFeatures(List<FeatureCar> features) { this.features = features; }

    public CarBrand getCarBrand() { return carBrand; }

    public void setCarBrand(CarBrand carBrand) { this.carBrand = carBrand; }

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

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }
    public boolean hasCarFeature(FeatureCar featureCar) {
        return features != null && features.contains(featureCar);
    }
}
