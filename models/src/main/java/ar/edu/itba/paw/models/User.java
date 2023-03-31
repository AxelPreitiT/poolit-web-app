package ar.edu.itba.paw.models;

public class User {
    private final String email;

    private String password;

    //En el metodo, final es para que no pueda cambiar a las variables
    public User(final String email, final String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
