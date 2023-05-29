package ar.edu.itba.paw.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Passenger extends User{

    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;
    public Passenger(long userId, String username, String surname, String email, String phone, String password, City bornCity, Locale mailLocale, String role, long imageId, LocalDateTime startDateTime, LocalDateTime endDateTime){
        super(userId, username, surname, email, phone, password, bornCity, mailLocale, role,imageId);
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }
    public Passenger(User user, LocalDateTime startDateTime, LocalDateTime endDateTime){
        super(user.getUserId(),user.getName(),user.getSurname(),user.getEmail(),user.getPhone(),user.getPassword(),user.getBornCity(),user.getMailLocale(),user.getRole(),user.getUserImageId());
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    @Override
    public String toString() {
        return String.format("Passenger { userId: %d, startDateTime: '%s', endDateTime: '%s' }",
                getUserId(), startDateTime, endDateTime);
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
}
