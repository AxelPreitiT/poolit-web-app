package ar.edu.itba.paw.webapp.form;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class CreateTripForm {
    private int originCityId;
    @NotBlank
    private String originAddress;
    @NotBlank
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private String originDate;
    @NotBlank
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private String originTime;
    private int destinationCityId;
    @NotBlank
    private String destinationAddress;
    @Pattern(regexp = "([a-zA-Z]{2}\\s?\\d{3}\\s?[a-zA-Z]{2})|([a-zA-Z]{3}\\s?\\d{3})")
    private String carPlate;
    @NotBlank
    private String carInfo;
    @Min(value = 1)
    private int maxSeats;
    @NotBlank
    @Email
    private String email;
    @Pattern(regexp = "^\\d{2,3}\\s?\\d{4}\\s?\\d{4}")
    private String phone;

    public int getOriginCityId() {
        return originCityId;
    }

    public void setOriginCityId(int originCityId) {
        this.originCityId = originCityId;
    }

    public String getOriginAddress() {
        return originAddress;
    }

    public void setOriginAddress(String originAddress) {
        this.originAddress = originAddress;
    }

    public String getOriginDate() {
        return originDate;
    }

    public void setOriginDate(String originDate) {
        this.originDate = originDate;
    }

    public String getOriginTime() {
        return originTime;
    }

    public void setOriginTime(String originTime) {
        this.originTime = originTime;
    }

    public int getDestinationCityId() {
        return destinationCityId;
    }

    public void setDestinationCityId(int destinationCityId) {
        this.destinationCityId = destinationCityId;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public String getCarPlate() {
        return carPlate;
    }

    public void setCarPlate(String carPlate) {
        this.carPlate = carPlate;
    }

    public String getCarInfo() {
        return carInfo;
    }

    public void setCarInfo(String carInfo) {
        this.carInfo = carInfo;
    }

    public int getMaxSeats() {
        return maxSeats;
    }

    public void setMaxSeats(int maxSeats) {
        this.maxSeats = maxSeats;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
