package ar.edu.itba.paw.models.reviews;

import ar.edu.itba.paw.models.Car;
import ar.edu.itba.paw.models.Passenger;
import ar.edu.itba.paw.models.User;

import java.util.List;

public class TripReviewCollection {

    final private User driver;

    final private Car car;

    final private List<Passenger> passengers;

    public TripReviewCollection(final User driver, final Car car, final List<Passenger> passengers) {
        this.driver = driver;
        this.car = car;
        this.passengers = passengers;
    }

    public User getDriver() {
        return driver;
    }

    public Car getCar() {
        return car;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public boolean canReviewDriver() {
        return driver != null;
    }

    public boolean canReviewCar() {
        return car != null;
    }

    public boolean canReviewPassengers() {
        return passengers != null && !passengers.isEmpty();
    }

    public boolean canReview() {
        return canReviewDriver() || canReviewCar() || canReviewPassengers();
    }
}
