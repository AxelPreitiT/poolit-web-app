package ar.edu.itba.paw.models;

import java.time.LocalDateTime;

public class User {
    private final long userId;
    private final String username;
    private final String surname;
    private final String email;
    private String phone;
    private final String password;
    private final LocalDateTime birthdate;
    private final City bornCityId;

    //En el metodo, final es para que no pueda cambiar a las variables
    public User(long userId, final String username, final String surname, final String email,
                final String phone, final String password, final LocalDateTime birthdate, final City bornCityId) {
        this.userId = userId;
        this.username = username;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.birthdate = birthdate;
        this.bornCityId = bornCityId;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public long getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public City getBornCityId() {
        return bornCityId;
    }

    public String getSurname() {
        return surname;
    }

    public LocalDateTime getBirthdate() {
        return birthdate;
    }

    public String getUsername() {
        return username;
    }
}
