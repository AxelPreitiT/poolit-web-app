package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.models.CarBrand;
import ar.edu.itba.paw.models.FeatureCar;
import ar.edu.itba.paw.webapp.form.annotations.MPFile;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.web.multipart.MultipartFile;
import ar.edu.itba.paw.webapp.form.annotations.NotSamePlate;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

public class CreateCarForm {

    @Size(min = 1)
    @Pattern(regexp = "([a-zA-Z]{2}\\s?\\d{3}\\s?[a-zA-Z]{2})|([a-zA-Z]{3}\\s?\\d{3})")
    @NotSamePlate
    private String plate;

    @Size(min = 5, max = 100)
    private String carInfo;

    @MPFile
    private MultipartFile imageFile;

    @Min(value = 1)
    private int seats;

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

    public MultipartFile getImageFile() { return imageFile; }

    public void setImageFile(MultipartFile imageFile) { this.imageFile = imageFile; }


}
