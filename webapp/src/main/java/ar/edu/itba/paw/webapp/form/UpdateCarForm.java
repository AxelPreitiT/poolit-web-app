package ar.edu.itba.paw.webapp.form;


import ar.edu.itba.paw.models.FeatureCar;
import ar.edu.itba.paw.webapp.form.annotations.MPFile;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

public class UpdateCarForm {

    @Size(min = 5, max = 100)
    private String carInfo;

    @Min(value = 1)
    private int seats;

    @MPFile
    private MultipartFile imageFile;

    private List<FeatureCar> features;

    public List<FeatureCar> getFeatures() { return features; }

    public MultipartFile getImageFile() { return imageFile; }

    public void setImageFile(MultipartFile imageFile) { this.imageFile = imageFile; }

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
