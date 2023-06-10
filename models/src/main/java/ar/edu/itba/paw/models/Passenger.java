package ar.edu.itba.paw.models;

import ar.edu.itba.paw.models.trips.Trip;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

@Entity
@Table(name = "passengers")
@IdClass(PassengerKey.class)
public class Passenger{

    @Id
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "user_id")
    private User user;
    @Id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "trip_id")
    private Trip trip;
    @Column(name = "start_date")
    private LocalDateTime startDateTime;
    @Column(name = "end_date")
    private LocalDateTime endDateTime;

    public Passenger(){}
    public Passenger(User user,Trip trip, LocalDateTime startDateTime, LocalDateTime endDateTime){
        this.user = user;
        this.trip = trip;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }
    public Passenger(User user, LocalDateTime startDateTime, LocalDateTime endDateTime){
        this.user = user;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    @Override
    public String toString() {
        return String.format("Passenger { userId: %d, startDateTime: '%s', endDateTime: '%s' }",
                user.getUserId(), startDateTime, endDateTime);
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public String getStartDateString(){
        return startDateTime.format(Format.getDateFormatter());
    }
    public String getEndDateString(){
        return endDateTime.format(Format.getDateFormatter());
    }
    public String getStartTimeString(){
        return startDateTime.format(Format.getTimeFormatter());
    }
    public String getEndTimeString(){
        return endDateTime.format(Format.getTimeFormatter());
    }
    public boolean getRecurrent(){
        return !startDateTime.equals(endDateTime);
    }

    public Trip getTrip() {
        return trip;
    }

    public User getUser() {
        return user;
    }

    public String getRole() {
        return user.getRole();
    }

    public String getEmail() {
        return user.getEmail();
    }

    public String getPhone() {
        return user.getPhone();
    }

    public long getUserId() {
        return user.getUserId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Passenger user = (Passenger) o;
        return user.getUserId() == getUserId() && user.getTrip().getTripId() == getTrip().getTripId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(user.getUserId(),trip.getTripId());
    }

    public City getBornCity() {
        return user.getBornCity();
    }

    public String getSurname() {
        return user.getSurname();
    }

    public Locale getMailLocale() {
        return user.getMailLocale();
    }

    public String getName() {
        return user.getName();
    }

    public long getUserImageId() { return user.getUserImageId(); }

    public boolean isTripEnded() {
        return LocalDateTime.now().isAfter(endDateTime);
    }
}
