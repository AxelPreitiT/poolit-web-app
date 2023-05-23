package ar.edu.itba.paw.models;

import javax.persistence.*;
import java.util.Locale;
import java.util.Objects;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy =  GenerationType.SEQUENCE, generator ="users_user_id_seq" )
    @SequenceGenerator(sequenceName = "users_user_id_seq" , name = "users_user_id_seq", allocationSize = 1)
    @Column(name = "user_id")
    private long userId;
    @Column(name = "username")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "email")
    private String email;
    @Column(name = "phone")
    private String phone;
    @Column(name = "password")
    private String password;

    //TODO: mapear relaciones
    private City bornCity;
    @Column(name = "mail_locale")
    //Si no funciona, ir aca
    //https://www.baeldung.com/jpa-attribute-converters
    private Locale mailLocale;
    @Column(name = "user_role")
    private String role;
    @Column(name = "user_image_id")
    private long userImageId;

    protected User(){

    }

    public User(long userId, final String name, final String surname, final String email,
                final String phone, String password, final City bornCity, final Locale mailLocale, final String role,long userImageId) {
        this.userId = userId;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.bornCity = bornCity;
        this.mailLocale = mailLocale;
        this.role = role;
        this.userImageId = userImageId;
    }

    @Override
    public String toString() {
        return String.format("User { id: %d, name: '%s', surname: '%s', email: '%s', phone: '%s', bornCity: '%s', mailLocale: '%s', role: '%s', userImageId: %d }",
                userId, name, surname, email, phone, bornCity, mailLocale, role, userImageId);
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

    public Locale getMailLocale() {
        return mailLocale;
    }

    public String getName() {
        return name;
    }

    public long getUserImageId() { return userImageId; }

    public void setUserImageId(long userImageId) { this.userImageId = userImageId; }
}
