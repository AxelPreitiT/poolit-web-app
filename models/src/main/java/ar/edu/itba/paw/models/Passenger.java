package ar.edu.itba.paw.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Passenger extends User{

    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;
    public Passenger(long userId, String username, String surname, String email, String phone, String password, LocalDateTime birthdate, City bornCity, String role, LocalDateTime startDateTime,LocalDateTime endDateTime){
        super(userId, username, surname, email, phone, password, birthdate, bornCity, role);
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }
    public Passenger(User user, LocalDateTime startDateTime, LocalDateTime endDateTime){
        super(user.getUserId(),user.getUsername(),user.getSurname(),user.getEmail(),user.getPhone(),user.getPassword(),user.getBirthdate(),user.getBornCity(),user.getRole());
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public String getStartDateString(){
        return startDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }
    public String getEndDateString(){
        return endDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }
    public String getStartTimeString(){
        return String.format("%02d:%02d",startDateTime.getHour(),startDateTime.getMinute());
    }
    public String getEndTimeString(){
        return String.format("%02d:%02d",endDateTime.getHour(),endDateTime.getMinute());
    }
    public boolean getRecurrent(){
        return !startDateTime.equals(endDateTime);
    }
}
