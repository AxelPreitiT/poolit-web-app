package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Car;
import ar.edu.itba.paw.models.CarBrand;
import ar.edu.itba.paw.models.FeatureCar;
import ar.edu.itba.paw.models.User;

import java.util.List;
import java.util.Optional;

public interface CarService {

    Car createCar(String plate, String infoCar, final User user, final long image_id, int seats, CarBrand brand, List<FeatureCar> features);

    Car ModifyCar(long carId, String infoCar, int seats, List<FeatureCar> features);

    Optional<Car> findById(long carId);

    List<Car> findByUser(User user);

    Optional<Car> findByUserAndPlate(User user, String plate);
}
