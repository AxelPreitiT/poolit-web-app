package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.interfaces.exceptions.CarNotFoundException;
import ar.edu.itba.paw.interfaces.exceptions.ImageNotFoundException;
import ar.edu.itba.paw.interfaces.exceptions.UserNotFoundException;
import ar.edu.itba.paw.models.*;

import java.util.List;
import java.util.Optional;

public interface CarService {

    Car createCar(String plate, String infoCar, final byte[] imgData, int seats, CarBrand brand, List<FeatureCar> features) throws UserNotFoundException;

    Car modifyCar(long carId, String infoCar, Integer seats, List<FeatureCar> features, byte[] imgData) throws CarNotFoundException;

    Optional<Car> findById(long carId);

    List<Car> findCurrentUserCars() throws UserNotFoundException;

    List<Car> findUserCars(final long userId) throws UserNotFoundException;

    Optional<Car> findByUserAndPlate(User user, String plate);

//    boolean currentUserIsCarOwner(Car car);

    Image getCarImage(final long carId,final Image.Size size) throws CarNotFoundException, ImageNotFoundException;

    void updateCarImage(final long carId, final byte[] content) throws CarNotFoundException, ImageNotFoundException;

}
