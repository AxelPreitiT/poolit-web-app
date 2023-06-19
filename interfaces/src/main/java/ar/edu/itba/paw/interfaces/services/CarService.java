package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.interfaces.exceptions.CarNotFoundException;
import ar.edu.itba.paw.interfaces.exceptions.UserNotFoundException;
import ar.edu.itba.paw.models.Car;
import ar.edu.itba.paw.models.CarBrand;
import ar.edu.itba.paw.models.FeatureCar;
import ar.edu.itba.paw.models.User;

import java.util.List;
import java.util.Optional;

public interface CarService {

    Car createCar(String plate, String infoCar, final byte[] imgData, int seats, CarBrand brand, List<FeatureCar> features) throws UserNotFoundException;

    Car ModifyCar(long carId, String infoCar, int seats, List<FeatureCar> features, byte[] imgData) throws CarNotFoundException;

    Optional<Car> findById(long carId);

    List<Car> findCurrentUserCars() throws UserNotFoundException;

    Optional<Car> findByUserAndPlate(User user, String plate);

    boolean currentUserIsCarOwner(Car car);
}
