package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.form.annotations.MPFile;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Pattern;

public class CreateCarForm {

    @Pattern(regexp = "([a-zA-Z]{2}\\s?\\d{3}\\s?[a-zA-Z]{2})|([a-zA-Z]{3}\\s?\\d{3})", message = "No es una patente valida")
    private String plate;

    @Pattern(regexp = ".+", message="No es un modelo valido")
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
