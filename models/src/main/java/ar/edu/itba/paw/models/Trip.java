package ar.edu.itba.paw.models;

public class Trip {

    private final City originCity, destinationCity;
    private final User driver;
    private final String originAddress, destinationAddress, date, time;
    private final int seats;

    public Trip(final City originCity, final String originAddress, final City destinationCity, final String destinationAddress, final String date, final String time, final int seats, User driver) {
        this.originCity = originCity;
        this.originAddress = originAddress;
        this.destinationCity = destinationCity;
        this.destinationAddress = destinationAddress;
        this.date = date;
        this.time = time;
        this.seats = seats;
        this.driver = driver;
    }

    public City getOriginCity() {
        return originCity;
    }

    public String getOriginAddress() {
        return originAddress;
    }

    public City getDestinationCity() {
        return destinationCity;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public User getDriver() {
        return driver;
    }

    public int getSeats() {
        return seats;
    }
}
