package ar.edu.itba.paw.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy =  GenerationType.SEQUENCE, generator ="cars_car_id_seq" )
    @SequenceGenerator(sequenceName = "cars_car_id_seq" , name = "cars_car_id_seq", allocationSize = 1)
    @Column(name = "car_id")
    private long carId;

    @Column(name = "seats")
    private int seats;


    @Enumerated(EnumType.STRING)
    @Column(name = "features")
    @ElementCollection(targetClass = FeatureCar.class)
    private List<FeatureCar> features;

    @Enumerated(EnumType.STRING)
    @Column(name = "brand")
    private CarBrand brand;

    @Column(name = "plate")
    private String plate;

    @Column(name = "info_car")
    private String infoCar;

    //@ManyToOne(fetch = FetchType.LAZY,optional = false)
    @ManyToOne(fetch = FetchType.EAGER,optional = false)
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

    public Car(String plate, String infoCar, User user, long image_id, int seats, CarBrand brand, List<FeatureCar> features) {
        this.plate = plate;
        this.infoCar = infoCar;
        this.user = user;
        this.image_id = image_id;
        this.seats = seats;
        this.brand = brand;
        this.features = features;
    }

    @Override
    public String toString() {
        return String.format("Car { id: %d, plate: '%s', infoCar: '%s', userId: %d, imageId: %d }",
                carId, plate, infoCar, user.getUserId(), image_id);
    }

    public void setInfoCar(String infoCar) {
        this.infoCar = infoCar;
    }

    public void setImage_id(long image_id) {
        this.image_id = image_id;
    }

    public int getSeats() {
        return seats;
    }

    public CarBrand getBrand() {
        return brand;
    }

    public void setFeatures(List<FeatureCar> features) {
        this.features = features;
    }

    public void setBrand(CarBrand brand) {
        this.brand = brand;
    }

    public List<FeatureCar> getFeatures() {
        return features;
    }

    public void setSeats(int seats) {
        this.seats = seats;
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
