package ar.edu.itba.paw.models;

public class Car {

    private final long carId;

    private final String plate, infoCar;

    private final User user;

    private final long image_id;

    public Car(final long carId, String plate, String infoCar, final User user, final long image_id){
        this.carId=carId;
        this.plate=plate;
        this.infoCar=infoCar;
        this.user=user;
        this.image_id=image_id;
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

    public long getImage_id() { return image_id; }
}
