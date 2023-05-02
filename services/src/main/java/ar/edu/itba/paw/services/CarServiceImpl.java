package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistence.CarDao;
import ar.edu.itba.paw.interfaces.persistence.UserDao;
import ar.edu.itba.paw.interfaces.services.CarService;
import ar.edu.itba.paw.models.Car;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarServiceImpl implements CarService {

    private final CarDao carDao;

    @Autowired
    public CarServiceImpl(final CarDao carDao){
        this.carDao=carDao;
    }

    @Override
    public Car createCar(String plate, String infoCar, User user, long image_id) {
        return carDao.create(plate, infoCar, user, image_id);
    }

    @Override
    public Car createCarIfNotExists(String plate,String infoCar,  User user, long image_id) {
        Optional<Car> current = carDao.findByPlateAndUser(plate, user);
        return current.orElseGet(() -> carDao.create(plate, infoCar, user, image_id));
    }

    @Override
    public Optional<Car> findById(long carId) {
        return carDao.findById(carId);
    }

    @Override
    public Optional<Car> findByPlateAndUser(String plate, User user) {
        return carDao.findByPlateAndUser(plate, user);
    }

    @Override
    public List<Car> findByUser(User user) {
        return carDao.findByUser(user);
    }
}
