package ar.edu.itba.paw.interfaces.persistence;

import ar.edu.itba.paw.models.Car;

import java.util.List;
import java.util.Optional;

public interface CarDao {

    Car create(String plate, long user_id);

    Optional<Car> findById(long carId);

    Optional<Car> findByPlateAndUserId(String plate, long userId);

    Optional<List<Car>> findByUserId(long userId);
}
