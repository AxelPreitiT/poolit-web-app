package ar.edu.itba.paw.models;

public class Trip {

    private final String originCity, originAddress, destinationCity, destinationAddress, date, time, email, phone;
    private final int seats;

    public Trip(final String originCity, final String originAddress, final String destinationCity, final String destinationAddress, final String date, final String time, final int seats, final String email, final String phone) {
        this.originCity = originCity;
        this.originAddress = originAddress;
        this.destinationCity = destinationCity;
        this.destinationAddress = destinationAddress;
        this.date = date;
        this.time = time;
        this.seats = seats;
        this.email = email;
        this.phone = phone;
    }

    public String getOriginCity() {
        return originCity;
    }

    public String getOriginAddress() {
        return originAddress;
    }

    public String getDestinationCity() {
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

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public int getSeats() {
        return seats;
    }
}
