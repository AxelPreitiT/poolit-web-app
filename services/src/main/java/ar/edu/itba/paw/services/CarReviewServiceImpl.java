package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistence.CarReviewDao;
import ar.edu.itba.paw.interfaces.services.CarReviewService;
import ar.edu.itba.paw.interfaces.services.TripService;
import ar.edu.itba.paw.models.Car;
import ar.edu.itba.paw.models.PagedContent;
import ar.edu.itba.paw.models.Passenger;
import ar.edu.itba.paw.models.reviews.CarReview;
import ar.edu.itba.paw.models.reviews.CarReviewOptions;
import ar.edu.itba.paw.models.reviews.ItemReview;
import ar.edu.itba.paw.models.reviews.ReviewState;
import ar.edu.itba.paw.models.trips.Trip;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CarReviewServiceImpl implements CarReviewService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarReviewServiceImpl.class);

    private final CarReviewDao carReviewDao;
    private final TripService tripService;

    @Autowired
    public CarReviewServiceImpl(CarReviewDao carReviewDao, TripService tripService) {
        this.carReviewDao = carReviewDao;
        this.tripService = tripService;
    }

    @Transactional
    @Override
    public CarReview createCarReview(Trip trip, Passenger reviewer, Car car, int rating, String comment, CarReviewOptions option) {
        if(!canReviewCar(trip, reviewer, car)) {
            IllegalStateException e = new IllegalStateException();
            LOGGER.error("Passenger with id {} tried to review car with id {}, but it's not finished yet or was already reviewed", reviewer.getUserId(), car.getCarId(), e);
            throw e;
        }
        return carReviewDao.createCarReview(trip, reviewer, car, rating, comment, option);
    }

    @Override
    public double getCarsRating(Car car) {
        return carReviewDao.getCarRating(car);
    }

    @Override
    public PagedContent<CarReview> getCarReviews(Car car, int page, int pageSize) {
        return carReviewDao.getCarReviews(car, page, pageSize);
    }

    @Override
    public boolean canReviewCar(Trip trip, Passenger reviewer, Car car) {
        if(trip.getCar().getCarId() != car.getCarId()) {
            IllegalStateException e = new IllegalStateException();
            LOGGER.error("Passenger with id {} tried to review car with id {}, but it's not the car of the trip with id {}", reviewer.getUserId(), car.getCarId(), trip.getTripId(), e);
            throw e;
        }
        if(!tripService.userIsPassenger(trip.getTripId(), reviewer.getUser())) {
            IllegalStateException e = new IllegalStateException();
            LOGGER.error("User with id {} tried to review car with id {}, but he's not a passenger of the trip with id {}", reviewer.getUserId(), car.getCarId(), trip.getTripId(), e);
            throw e;
        }
        if(car.getUser().getUserId() == reviewer.getUserId()) {
            IllegalStateException e = new IllegalStateException();
            LOGGER.error("User with id {} tried to review car with id {}, but he's the owner of the car", reviewer.getUserId(), car.getCarId(), e);
            throw e;
        }
        return getReviewState(trip, reviewer, car) == ReviewState.PENDING;
    }

    private ReviewState getReviewState(Trip trip, Passenger reviewer, Car car) {
        LocalDateTime now = LocalDateTime.now();
        if(now.isBefore(trip.getEndDateTime()) && now.isBefore(reviewer.getEndDateTime())) {
            return ReviewState.DISABLED;
        }
        return carReviewDao.canReviewCar(trip, reviewer, car) ? ReviewState.PENDING : ReviewState.DONE;
    }

    @Override
    public ItemReview<Car> getCarReviewState(Trip trip, Passenger reviewer, Car car) {
        return new ItemReview<>(car, getReviewState(trip, reviewer, car));
    }
}
