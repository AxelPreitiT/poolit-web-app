package ar.edu.itba.paw.models;

import javax.persistence.*;

@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy =  GenerationType.SEQUENCE, generator ="cars_car_id_seq" )
    @SequenceGenerator(sequenceName = "cars_car_id_seq" , name = "cars_car_id_seq", allocationSize = 1)
    @Column(name = "car_id")
    private long carId;

    @Column(name = "plate")
    private String plate;

    @Column(name = "info_car")
    private String infoCar;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "image_id")
    private long image_id;

    protected Car(){

    }

    public Car(final long carId, String plate, String infoCar, final User user, final long image_id){
        this.carId=carId;
        this.plate=plate;
        this.infoCar=infoCar;
        this.user=user;
        this.image_id=image_id;
    }

    public Car(String plate, String infoCar, User user, long image_id) {
        this.plate = plate;
        this.infoCar = infoCar;
        this.user = user;
        this.image_id = image_id;
    }

    @Override
    public String toString() {
        return String.format("Car { id: %d, plate: '%s', infoCar: '%s', userId: %d, imageId: %d }",
                carId, plate, infoCar, user.getUserId(), image_id);
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
