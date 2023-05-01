package ar.edu.itba.paw.models;

import java.util.Objects;

import java.time.LocalDateTime;

public class User {
    private final long userId;
    private final String name;
    private final String surname;
    private final String email;
    private String phone;
    private final String password;
    private final LocalDateTime birthdate;
    private final City bornCity;
    private String role;
    private long userImageId;

    public User(long userId, final String name, final String surname, final String email,
                final String phone, String password, final LocalDateTime birthdate, final City bornCity, final String role,long userImageId) {
        this.userId = userId;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.birthdate = birthdate;
        this.bornCity = bornCity;
        this.role = role;
        this.userImageId = userImageId;
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

    public String getName() {
        return name;
    }

    public long getUserImageId() { return userImageId; }

    public void setUserImageId(long userImageId) { this.userImageId = userImageId; }
}
