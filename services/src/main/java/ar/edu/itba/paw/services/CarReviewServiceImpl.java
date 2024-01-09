package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.exceptions.CarNotFoundException;
import ar.edu.itba.paw.interfaces.exceptions.PassengerNotFoundException;
import ar.edu.itba.paw.interfaces.exceptions.TripNotFoundException;
import ar.edu.itba.paw.interfaces.exceptions.UserNotFoundException;
import ar.edu.itba.paw.interfaces.persistence.CarReviewDao;
import ar.edu.itba.paw.interfaces.services.CarReviewService;
import ar.edu.itba.paw.interfaces.services.CarService;
import ar.edu.itba.paw.interfaces.services.TripService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.Car;
import ar.edu.itba.paw.models.PagedContent;
import ar.edu.itba.paw.models.Passenger;
import ar.edu.itba.paw.models.User;
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
import java.util.Optional;

@Service
public class CarReviewServiceImpl implements CarReviewService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarReviewServiceImpl.class);

    private final CarReviewDao carReviewDao;
    private final TripService tripService;

    private final UserService userService;

    private final CarService carService;

    @Autowired
    public CarReviewServiceImpl(CarReviewDao carReviewDao, TripService tripService, UserService userService,
                                CarService carService) {
        this.carReviewDao = carReviewDao;
        this.tripService = tripService;
        this.userService = userService;
        this.carService = carService;
    }

    @Transactional
    @Override
    public CarReview createCarReview(final long tripId, final long carId, final int rating, final String comment, final CarReviewOptions option) throws TripNotFoundException, UserNotFoundException, CarNotFoundException, PassengerNotFoundException {
        Trip trip = tripService.findById(tripId).orElseThrow (TripNotFoundException::new);
        User currentUser = userService.getCurrentUser().orElseThrow(UserNotFoundException::new);
        Passenger reviewer = tripService.getPassenger(trip, currentUser).orElseThrow(PassengerNotFoundException::new);
        Car car = carService.findById(carId).orElseThrow(CarNotFoundException::new);
        if(!canReviewCar(trip, reviewer, car)) {
            IllegalArgumentException e = new IllegalArgumentException();
            LOGGER.error("Passenger with id {} tried to review car with id {}, but it's not finished yet or was already reviewed", reviewer.getUserId(), car.getCarId(), e);
            throw e;
        }
        return carReviewDao.createCarReview(trip, reviewer, car, rating, comment, option);
    }

    @Transactional
    @Override
    public double getCarsRating(final long carId) throws CarNotFoundException {
        Car car = carService.findById(carId).orElseThrow(CarNotFoundException::new);
        return carReviewDao.getCarRating(car);
    }

    @Transactional
    @Override
    public PagedContent<CarReview> getCarReviews(final long carId, final int page, final int pageSize) throws CarNotFoundException {
        Car car = carService.findById(carId).orElseThrow(CarNotFoundException::new);
        return carReviewDao.getCarReviews(car, page, pageSize);
    }

    @Transactional
    @Override
    public boolean canReviewCar(final Trip trip, final Passenger reviewer, final Car car) {
        if(trip.getCar().getCarId() != car.getCarId()) {
            IllegalArgumentException e = new IllegalArgumentException();
            LOGGER.error("Passenger with id {} tried to review car with id {}, but it's not the car of the trip with id {}", reviewer.getUserId(), car.getCarId(), trip.getTripId(), e);
            throw e;
        }
        if(!tripService.userIsPassenger(trip.getTripId(), reviewer.getUser())) {
            IllegalArgumentException e = new IllegalArgumentException();
            LOGGER.error("User with id {} tried to review car with id {}, but he's not a passenger of the trip with id {}", reviewer.getUserId(), car.getCarId(), trip.getTripId(), e);
            throw e;
        }
        if(car.getUser().getUserId() == reviewer.getUserId()) {
            IllegalArgumentException e = new IllegalArgumentException();
            LOGGER.error("User with id {} tried to review car with id {}, but he's the owner of the car", reviewer.getUserId(), car.getCarId(), e);
            throw e;
        }
        return getReviewState(trip, reviewer, car) == ReviewState.PENDING;
    }

    private ReviewState getReviewState(final Trip trip, final Passenger reviewer, final Car car) {
        LocalDateTime now = LocalDateTime.now();
        if(now.isBefore(trip.getEndDateTime()) && now.isBefore(reviewer.getEndDateTime())) {
            return ReviewState.DISABLED;
        }
        return carReviewDao.canReviewCar(trip, reviewer, car) ? ReviewState.PENDING : ReviewState.DONE;
    }

    @Transactional
    @Override
    public ItemReview<Car> getCarReviewState(final long tripId) throws TripNotFoundException, UserNotFoundException, CarNotFoundException {
        Trip trip = tripService.findById(tripId).orElseThrow(TripNotFoundException::new);
        User currentUser = userService.getCurrentUser().orElseThrow(UserNotFoundException::new);
        Passenger reviewer = tripService.getPassenger(trip, currentUser).get();
        Car car = carService.findById(trip.getCar().getCarId()).orElseThrow(CarNotFoundException::new);
        return new ItemReview<>(car, getReviewState(trip, reviewer, car));
    }

    @Transactional
    @Override
    public Optional<CarReview> findById(final long reviewId){
        return carReviewDao.findById(reviewId);
    }

    @Transactional
    @Override
    public PagedContent<CarReview> getCarReviewsMadeByUserOnTrip(final long carId, final long userId, final long tripId, final int page, final int pageSize) throws CarNotFoundException, UserNotFoundException, TripNotFoundException {
        final Car car = carService.findById(carId).orElseThrow(CarNotFoundException::new);
        final User user = userService.findById(userId).orElseThrow(UserNotFoundException::new);
        final Trip trip = tripService.findById(tripId).orElseThrow(TripNotFoundException::new);
        return carReviewDao.getCarReviewsMadeByUserOnTrip(car,user,trip,page,pageSize);
    }
}
