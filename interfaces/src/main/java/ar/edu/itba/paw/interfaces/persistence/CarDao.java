package ar.edu.itba.paw.interfaces.persistence;

import ar.edu.itba.paw.models.Car;
import ar.edu.itba.paw.models.User;

import java.util.List;
import java.util.Optional;

public interface CarDao {

    Car create(String plate, String infoCar, final User user);

    Optional<Car> findById(long carId);

    Optional<Car> findByPlateAndUser(String plate, final User user);

    List<Car> findByUser(User user);
}
