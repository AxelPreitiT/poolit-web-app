package ar.edu.itba.paw.models.reports;

import ar.edu.itba.paw.models.Passenger;
import ar.edu.itba.paw.models.User;

import java.util.List;

public class TripReportCollection {

    final private ItemReport<User> driver;
    final private List<ItemReport<Passenger>> passengers;

    public TripReportCollection(ItemReport<User> driver, List<ItemReport<Passenger>> passengers) {
        this.driver = driver;
        this.passengers = passengers;
    }

    public static TripReportCollection empty() {
        return new TripReportCollection(null, null);
    }

    public ItemReport<User> getDriver() {
        return driver;
    }

    public List<ItemReport<Passenger>> getPassengers() {
        return passengers;
    }

    public boolean getCanReportDriver() {
        return driver != null;
    }

    public boolean getCanReportPassengers() {
        return passengers != null && !passengers.isEmpty();
    }

    public boolean getCanReport() {
        return getCanReportDriver() || getCanReportPassengers();
    }
}
