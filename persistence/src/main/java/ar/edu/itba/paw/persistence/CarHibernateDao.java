package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.CarDao;
import ar.edu.itba.paw.models.Car;
import ar.edu.itba.paw.models.CarBrand;
import ar.edu.itba.paw.models.FeatureCar;
import ar.edu.itba.paw.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class CarHibernateDao implements CarDao {

    private final static Logger LOGGER = LoggerFactory.getLogger(CarHibernateDao.class);

    @PersistenceContext
    private EntityManager em;

    @Override
    public Car create(String plate, String infoCar, User user, long imageId, int seats, CarBrand brand, List<FeatureCar> features) {
        LOGGER.debug("Adding new car with plate '{}' from user with id {} to the database", plate, user.getUserId());
        final Car car = new Car(plate,infoCar,user,imageId, seats, brand, features);
        em.persist(car);
        LOGGER.info("Car with plate '{}' added to the database with id {}", plate, car.getCarId());
        LOGGER.debug("New {}", car);
        return car;
    }

    @Override
    public Optional<Car> findById(long carId) {
        LOGGER.debug("Looking for car with id {} in the database", carId);
        final Optional<Car> result = Optional.ofNullable(em.find(Car.class,carId));
        LOGGER.debug("Found {} in the database", result.isPresent() ? result.get() : "nothing");
        return result;
    }


    @Override
    public Optional<Car> findByPlateAndUser(String plate, User user) {
        final String plateToSearch = plate.toUpperCase();
        LOGGER.debug("Looking for car with plate '{}' from user with id {} in the database", plate, user.getUserId());
        final TypedQuery<Car> query = em.createQuery("from Car as c where c.plate = :plate AND c.user = :user",Car.class);
        query.setParameter("plate",plateToSearch);
        query.setParameter("user",user);
        Optional<Car> result = query.getResultList().stream().findFirst();
        LOGGER.debug("Found {} in the database", result.isPresent() ? result.get() : "nothing");
        return result;
    }

    @Override
    public List<Car> findByUser(User user) {
        LOGGER.debug("Looking for cars from user with id {} in the database", user.getUserId());
        final TypedQuery<Car> query = em.createQuery("from Car c where c.user = :user",Car.class);
        query.setParameter("user",user);
        List<Car> result = query.getResultList();
        LOGGER.debug("Found {} in the database", result);
        return result;
    }


    @Override
    public Car modifyCar(long carId, String infoCar, int seats, List<FeatureCar> features, long imageId){
        Optional<Car> car = findById(carId);
        if(car.isPresent()){
            Car carToModify = car.get();
            carToModify.setInfoCar(infoCar);
            carToModify.setSeats(seats);
            carToModify.setFeatures(features);
            carToModify.setImageId(imageId);
            return em.merge(carToModify);
        }
        return null;
    }
}
