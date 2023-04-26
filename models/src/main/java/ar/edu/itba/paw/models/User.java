package ar.edu.itba.paw.models;

import java.util.Objects;

public class User {
    private final String email;
    private final long userId;
    private String phone;

    //En el metodo, final es para que no pueda cambiar a las variables
    public User(long userId ,final String email, final String phone) {
        this.userId = userId;
        this.email = email;
        this.phone = phone;
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
}
