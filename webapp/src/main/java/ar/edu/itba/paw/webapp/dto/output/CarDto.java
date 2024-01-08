package ar.edu.itba.paw.webapp.dto.output;

import ar.edu.itba.paw.models.Car;
import ar.edu.itba.paw.models.CarBrand;
import ar.edu.itba.paw.models.FeatureCar;

import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

public class CarDto {

    private int seats;
    private List<FeatureCar> features;

    private CarBrand brand;

    private String plate;

    private String infoCar;
    private URI selfUri;
    private URI imageUri;

    public CarDto(){}

    protected CarDto(final UriInfo uriInfo, final Car car){
        this.infoCar = car.getInfoCar();
        this.brand = car.getBrand();
        this.plate = car.getPlate();
        // TODO chequear esto
        //this.features = car.getFeatures();
        this.seats= car.getSeats();

        this.selfUri = uriInfo.getBaseUriBuilder().path("/api/cars/").path(String.valueOf(car.getCarId())).build();
        this.imageUri = uriInfo.getBaseUriBuilder().path("/api/cars/").path(String.valueOf(car.getCarId())).path("image/").build();
    }

    public static CarDto fromCar(final UriInfo uriInfo, final Car car){
        return new CarDto(uriInfo,car);
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public List<FeatureCar> getFeatures() { return features; }

    public void setFeatures(List<FeatureCar> features) { this.features = features; }

    public CarBrand getBrand() { return brand; }

    public void setBrand(CarBrand brand) { this.brand = brand; }

    public String getPlate() { return plate; }

    public void setPlate(String plate) { this.plate = plate; }

    public String getInfoCar() { return infoCar; }

    public void setInfoCar(String infoCar) { this.infoCar = infoCar; }

    public URI getSelfUri() {
        return selfUri;
    }

    public void setSelfUri(URI selfUri) {
        this.selfUri = selfUri;
    }

    public URI getImageUri() {
        return imageUri;
    }

    public void setImageUri(URI imageUri) {
        this.imageUri = imageUri;
    }
}
