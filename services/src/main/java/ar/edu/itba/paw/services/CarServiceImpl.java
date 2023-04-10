package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistence.CarDao;
import ar.edu.itba.paw.interfaces.persistence.UserDao;
import ar.edu.itba.paw.interfaces.services.CarService;
import ar.edu.itba.paw.models.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarServiceImpl implements CarService {

    private final CarDao carDao;

    @Autowired
    public CarServiceImpl(final CarDao carDao, final UserDao userDao){
        this.carDao=carDao;
    }

    @Override
    public Car createCar(String plate, long userId) {
        return carDao.create(plate, userId);
    }

    @Override
    public Car createCarIfNotExists(String plate, long userId) {
        Optional<Car> current = carDao.findByPlateAndUserId(plate, userId);
        return current.orElseGet(() -> carDao.create(plate, userId));
    }

    @Override
    public Optional<Car> findById(long carId) {
        return carDao.findById(carId);
    }

    @Override
    public Optional<Car> findByPlateAndUserId(String plate, long userId) {
        return carDao.findByPlateAndUserId(plate, userId);
    }

    @Override
    public Optional<List<Car>> findByUserId(long userId) {
        return carDao.findByUserId(userId);
    }
}
