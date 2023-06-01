package ar.edu.itba.paw.webapp.form;


import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class UpdateCarForm {

    @Size(min = 5, max = 100)
    private String carInfo;


    @Min(value = 1)
    private int seats;

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
