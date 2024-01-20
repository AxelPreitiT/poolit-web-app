package ar.edu.itba.paw.webapp.dto.output;

import ar.edu.itba.paw.models.Car;
import ar.edu.itba.paw.models.CarBrand;
import ar.edu.itba.paw.models.FeatureCar;
import ar.edu.itba.paw.webapp.controller.utils.UrlHolder;

import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

public class CarDto {

    private int seats;
    private String plate;

    private String infoCar;

    private double rating;
    private URI brandUri;
    private List<URI> featuresUri;
    private URI selfUri;
    private URI imageUri;
    private long carId;

    public CarDto(){}

    protected CarDto(final UriInfo uriInfo, final Car car){
        this.carId = car.getCarId();
        this.infoCar = car.getInfoCar();
        this.brandUri = uriInfo.getBaseUriBuilder().path(UrlHolder.CAR_BRAND_BASE).path(car.getBrand().name()).build();
        this.plate = car.getPlate();
        this.featuresUri = car.getFeatures().stream().map(e -> uriInfo.getBaseUriBuilder().path(UrlHolder.CAR_FEATURE_BASE).path(e.name()).build()).collect(Collectors.toList());
        this.seats= car.getSeats();
        this.rating = car.getCarRating();
        this.selfUri = uriInfo.getBaseUriBuilder().path(UrlHolder.CAR_BASE).path(String.valueOf(car.getCarId())).build();
        this.imageUri = uriInfo.getBaseUriBuilder().path(UrlHolder.CAR_BASE).path(String.valueOf(car.getCarId())).path(UrlHolder.IMAGE_ENTITY).build();
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
    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public URI getBrandUri() {
        return brandUri;
    }

    public void setBrandUri(URI brandUri) {
        this.brandUri = brandUri;
    }

    public List<URI> getFeaturesUri() {
        return featuresUri;
    }

    public void setFeaturesUri(List<URI> featuresUri) {
        this.featuresUri = featuresUri;
    }

    public long getCarId() {
        return carId;
    }

    public void setCarId(long carId) {
        this.carId = carId;
    }
}
