package ar.edu.itba.paw.models;

import ar.edu.itba.paw.models.trips.Trip;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
    @Column(name = "passenger_state",columnDefinition = "TEXT DEFAULT 'ACCEPTED'")
    @Enumerated(EnumType.STRING)
    private PassengerState passengerState;

    public Passenger(){}
    public Passenger(User user,Trip trip, LocalDateTime startDateTime, LocalDateTime endDateTime){
        this.user = user;
        this.trip = trip;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.passengerState = PassengerState.PENDING;
    }
    public Passenger(User user, LocalDateTime startDateTime, LocalDateTime endDateTime){
        this.user = user;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.passengerState = PassengerState.PENDING;
    }

    @Override
    public String toString() {
        return String.format("Passenger { userId: %d, startDateTime: '%s', endDateTime: '%s' }",
                user.getUserId(), startDateTime, endDateTime);
    }

    public PassengerState getPassengerState() {
        if(passengerState.equals(PassengerState.PENDING) && startDateTime.compareTo(LocalDateTime.now())<0){
            return PassengerState.UNCONFIRMED;
        }
        return passengerState;
    }

    public void setPassengerState(PassengerState passengerState) {
        this.passengerState = passengerState;
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

    //OJO con usar esto y que ya se haya buscado el mismo trip en la sesion
    //  si hago cambios en el trip, entonces me va a hacer los cambios en la unica instancia que me da Hibernate
    public Trip getTrip() {
        final Trip ans =  trip;
        trip.setQueryStartDateTime(startDateTime);
        trip.setQueryEndDateTime(endDateTime);
        return ans;
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

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public boolean isTripEnded() {
        return LocalDateTime.now().isAfter(endDateTime);
    }

    public boolean isTripStarted(){
        return !LocalDateTime.now().isBefore(startDateTime);
    }

    public boolean isAccepted(){
        return passengerState.equals(PassengerState.ACCEPTED);
    }

    public boolean isRejected(){
        return passengerState.equals(PassengerState.REJECTED);
    }
    public boolean isWaiting(){
        return passengerState.equals(PassengerState.PENDING);
    }


    public int getQueryTotalTrips(){
        return (int) startDateTime.until(endDateTime, ChronoUnit.DAYS) / 7 + 1;
    }

    public double getTotalPrice(){
        return getQueryTotalTrips() * trip.getPrice();
    }

    public enum PassengerState{
        ACCEPTED("passengerState.accepted"),
        REJECTED("passengerState.rejected"),
        PENDING("passengerState.pending"),
        UNCONFIRMED("passengerState.unconfirmed");

        private final String messageCode;
        PassengerState(String messageCode){
            this.messageCode = messageCode;
        }
        public String getMessageCode(){
            return messageCode;
        }
    }
}
