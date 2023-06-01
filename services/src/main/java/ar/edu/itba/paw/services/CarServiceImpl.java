package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistence.CarDao;
import ar.edu.itba.paw.interfaces.services.CarService;
import ar.edu.itba.paw.models.Car;
import ar.edu.itba.paw.models.CarBrand;
import ar.edu.itba.paw.models.FeatureCar;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CarServiceImpl implements CarService {

    private final CarDao carDao;

    @Autowired
    public CarServiceImpl(final CarDao carDao){
        this.carDao=carDao;
    }

    @Transactional
    @Override
    public Car createCar(String plate, String infoCar, User user, long image_id, int seats, CarBrand brand, List<FeatureCar> features){
        return carDao.create(plate, infoCar, user, image_id, seats, brand, features);
    }

    @Transactional
    @Override
    public Car ModifyCar(long carId, String infoCar, int seats, List<FeatureCar> features) {
        return carDao.ModifyCar(carId, infoCar, seats, features);
    }

    @Override
    public Optional<Car> findById(long carId) {
        return carDao.findById(carId);
    }

    @Override
    public List<Car> findByUser(User user) {
        return carDao.findByUser(user);
    }

    @Override
    public Optional<Car> findByUserAndPlate(User user, String plate){
        return carDao.findByPlateAndUser(plate,user);
    }
}
