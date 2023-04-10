package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Car;

import java.util.List;
import java.util.Optional;

public interface CarService {

    Car createCar(String plate, String infoCar, long userId);

    Car createCarIfNotExists(String plate, String infoCar, long userId);

    Optional<Car> findById(long carId);

    Optional<Car> findByPlateAndUserId(String plate, long userId);

    Optional<List<Car>> findByUserId(long userId);
}
