package ar.edu.itba.paw.models;

public class User {
    private final String email;
    private final long userId;
    private String phone;
    private final String password;

    //En el metodo, final es para que no pueda cambiar a las variables
    public User(long userId ,final String email, final String phone, final String password) {
        this.userId = userId;
        this.email = email;
        this.phone = phone;
        this.password = password;
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
}
