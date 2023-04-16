package ar.edu.itba.paw.models;

public class Car {

    private final long carId;

    private final String plate, infoCar;

    private final User user;

    public Car(final long carId, String plate, String infoCar, final User user){
        this.carId=carId;
        this.plate=plate;
        this.infoCar=infoCar;
        this.user=user;
    }

    public long getCarId() {
        return carId;
    }

    public User getUser() {
        return user;
    }

    public String getPlate() {
        return plate;
    }

    public String getInfoCar() {
        return infoCar;
    }
}
