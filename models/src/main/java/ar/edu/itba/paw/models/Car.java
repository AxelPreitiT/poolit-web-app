package ar.edu.itba.paw.models;

import org.hibernate.annotations.Formula;

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

    @Column(name = "seats", nullable = false, columnDefinition = "INT DEFAULT 4")
    private int seats;


    @Enumerated(EnumType.STRING)
//    @Column(name = "features") //TODO: revisar
    @ElementCollection(targetClass = FeatureCar.class)
    private List<FeatureCar> features;

    @Enumerated(EnumType.STRING)
    @Column(name = "brand", nullable = false, columnDefinition = "TEXT DEFAULT 'UNKNOWN'")
    private CarBrand brand;

    @Column(name = "plate")
    private String plate;

    @Column(name = "info_car")
    private String infoCar;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "image_id")
    private long imageId;

    @Formula("(SELECT coalesce(avg(car_reviews.rating),0) FROM car_reviews WHERE car_reviews.car_id = car_id)")
    private double carRating;

    protected Car(){

    }

    public Car(final long carId, String plate, String infoCar, final User user, final long imageId){
        this.carId=carId;
        this.plate=plate;
        this.infoCar=infoCar;
        this.user=user;
        this.imageId = imageId;
    }

    public Car(String plate, String infoCar, User user, long imageId, int seats, CarBrand brand, List<FeatureCar> features) {
        this.plate = plate;
        this.infoCar = infoCar;
        this.user = user;
        this.imageId = imageId;
        this.seats = seats;
        this.brand = brand;
        this.features = features;
    }

    @Override
    public String toString() {
        return String.format("Car { id: %d, plate: '%s', infoCar: '%s', userId: %d, imageId: %d }",
                carId, plate, infoCar, user.getUserId(), imageId);
    }

    public void setInfoCar(String infoCar) {
        this.infoCar = infoCar;
    }

    public void setImageId(long imageId) {
        this.imageId = imageId;
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

    public long getImageId() { return imageId; }

    public boolean hasFeature(FeatureCar feature) {
        return features != null && features.contains(feature);
    }

    public boolean hasInfoCar() {
        return infoCar != null && !infoCar.isEmpty();
    }

    public boolean hasBrand() {
        return brand != null && brand.hasBrand();
    }

    public double getCarRating() {
        return carRating;
    }
}
