package ar.edu.itba.paw.webapp.form;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;

public class CreateTripForm {
    private int originCityId;

    @Pattern(regexp = ".+")
    @Size(max = 30)
    private String originAddress;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private String date;
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private String time;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private String lastDate;

    private boolean multitrip;

    private int destinationCityId;
    @Pattern(regexp = ".+")
    @Size(max = 30)
    private String destinationAddress;

    @Min(value = 0)
    private long carId;

    @Min(value = 1)
    private int maxSeats;

    @Min(value = 0)
    private double price;

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public String getLastDate() {
        return lastDate;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

    public boolean isMultitrip() {
        return multitrip;
    }

    public void setMultitrip(boolean multitrip) {
        this.multitrip = multitrip;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getCarId() {
        return carId;
    }

    public void setCarId(long carId) {
        this.carId = carId;
    }

    public int getMaxSeats() {
        return maxSeats;
    }

    public void setMaxSeats(int maxSeats) {
        this.maxSeats = maxSeats;
    }
}
