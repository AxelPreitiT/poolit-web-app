package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.form.annotations.MPFile;
import org.springframework.web.multipart.MultipartFile;
import ar.edu.itba.paw.webapp.form.annotations.NotSamePlate;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class CreateCarForm {

    @Size(min = 1)
    @Pattern(regexp = "([a-zA-Z]{2}\\s?\\d{3}\\s?[a-zA-Z]{2})|([a-zA-Z]{3}\\s?\\d{3})")
    @NotSamePlate
    private String plate;

    @Size(min = 5, max = 100)
    private String carInfo;

    @MPFile
    private MultipartFile imageFile;

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
