package ar.edu.itba.paw.models;

import java.util.Objects;

import java.time.LocalDateTime;

public class User {
    private final long userId;
    private final String username;
    private final String surname;
    private final String email;
    private String phone;
    private final String password;
    private final LocalDateTime birthdate;
    private final City bornCity;
    private String role;

    public User(long userId, final String username, final String surname, final String email,
                final String phone, final String password, final LocalDateTime birthdate, final City bornCity, final String role) {
        this.userId = userId;
        this.username = username;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.birthdate = birthdate;
        this.bornCity = bornCity;
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

    public City getBornCity() {
        return bornCity;
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
