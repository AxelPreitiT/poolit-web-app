package ar.edu.itba.paw.models;

public class Trip {

    private final City originCity, destinationCity;
    private final String originAddress, destinationAddress, date, time, email, phone, carInfo;
    private final int seats;

    public Trip(final City originCity, final String originAddress, final City destinationCity, final String destinationAddress, final String date, final String time, final String carInfo, final int seats, final String email, final String phone) {
        this.originCity = originCity;
        this.originAddress = originAddress;
        this.destinationCity = destinationCity;
        this.destinationAddress = destinationAddress;
        this.date = date;
        this.time = time;
        this.carInfo = carInfo;
        this.seats = seats;
        this.email = email;
        this.phone = phone;
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

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public int getSeats() {
        return seats;
    }

    public String getCarInfo() {
        return carInfo;
    }
}
