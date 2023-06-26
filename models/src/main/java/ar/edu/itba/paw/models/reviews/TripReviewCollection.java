package ar.edu.itba.paw.models.reviews;

import ar.edu.itba.paw.models.Car;
import ar.edu.itba.paw.models.Passenger;
import ar.edu.itba.paw.models.User;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TripReviewCollection {

    final private ItemReview<User> driver;

    final private ItemReview<Car> car;

    final private List<ItemReview<Passenger>> passengers;

    public TripReviewCollection(final ItemReview<User> driver, final ItemReview<Car> car, final List<ItemReview<Passenger>> passengers) {
        this.driver = driver;
        this.car = car;
        this.passengers = passengers;
    }

    public static TripReviewCollection empty() {
        return new TripReviewCollection(null, null, null);
    }

    public List<PassengerReviewOptions> getPassengerReviewOptionsByRating(int rating) {
        return Arrays.stream(PassengerReviewOptions.values())
                .filter(option -> option.getRatings().contains(rating))
                .collect(Collectors.toList());
    }

    public List<DriverReviewOptions> getDriverReviewOptionsByRating(int rating) {
        return Arrays.stream(DriverReviewOptions.values())
                .filter(option -> option.getRatings().contains(rating))
                .collect(Collectors.toList());
    }

    public List<CarReviewOptions> getCarReviewOptionsByRating(int rating) {
        return Arrays.stream(CarReviewOptions.values())
                .filter(option -> option.getRatings().contains(rating))
                .collect(Collectors.toList());
    }

    public ItemReview<User> getDriver() {
        return driver;
    }

    public ItemReview<Car> getCar() {
        return car;
    }

    public List<ItemReview<Passenger>> getPassengers() {
        return passengers;
    }

    public boolean getCanReviewDriver() {
        return driver != null;
    }

    public boolean getCanReviewCar() {
        return car != null;
    }

    public boolean getCanReviewPassengers() {
        return passengers != null && !passengers.isEmpty();
    }

    public boolean getCanReview() {
        return getCanReviewDriver() || getCanReviewCar() || getCanReviewPassengers();
    }

    public List<Integer> getRatingOptions() {
        return Arrays.asList(1, 2, 3, 4, 5);
    }

    public String getRatingOptionLabel(int rating) {
        return String.join("", Collections.nCopies(rating, "★"));
    }
}
