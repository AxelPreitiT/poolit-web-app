package ar.edu.itba.paw.interfaces.persistence;

import ar.edu.itba.paw.models.Car;
import ar.edu.itba.paw.models.CarBrand;
import ar.edu.itba.paw.models.FeatureCar;
import ar.edu.itba.paw.models.User;

import java.util.List;
import java.util.Optional;

public interface CarDao {

    Car create(String plate, String infoCar, final User user, final long image_id, int seats, CarBrand brand, List<FeatureCar> features);

    Car modifyCar(long carId, String infoCar, int seats, List<FeatureCar> features);

    Optional<Car> findByPlate(String plate);

    Optional<Car> findById(long carId);

    Optional<Car> findByPlateAndUser(String plate, final User user);

    List<Car> findByUser(User user);
}
