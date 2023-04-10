package ar.edu.itba.paw.models;

public class Car {

    private final long carId;

    private String plate;

    private final long user_id;

    public Car(final long carId, String plate, final long user_id){
        this.carId=carId;
        this.plate=plate;
        this.user_id=user_id;
    }

    public long getCarId() {
        return carId;
    }

    public long getUser_id() {
        return user_id;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate){
        this.plate=plate;
    }
}
