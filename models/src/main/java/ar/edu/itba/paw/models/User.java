package ar.edu.itba.paw.models;

import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
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
    @Column(nullable = false, name = "enabled", columnDefinition = "BOOLEAN DEFAULT true")
    private boolean enabled;

    @Column(nullable = false, name = "banned", columnDefinition = "BOOLEAN DEFAULT false")
    private boolean banned;

    @Formula("(SELECT coalesce(avg(user_reviews.rating),0) FROM user_reviews WHERE user_reviews.reviewed_id = user_id AND user_reviews.review_id IN (SELECT passenger_reviews.review_id FROM passenger_reviews))")
    private double passengerRating;

    @Formula("(SELECT coalesce(avg(user_reviews.rating),0) FROM user_reviews WHERE user_reviews.reviewed_id = user_id AND user_reviews.review_id IN (SELECT driver_reviews.review_id FROM driver_reviews))")
    private double driverRating;

    //TODO: agregar viajes creados como conductor
    @Formula("(SELECT coalesce(count(reports.report_id),0) FROM reports WHERE reports.reporter_id = user_id)")
    private int reportsPublished;

    @Formula("(SELECT coalesce(count(reports.report_id),0) FROM reports WHERE reports.reported_id = user_id)")
    private int reportsReceived;

    @Formula("(SELECT coalesce(count(reports.report_id),0) FROM reports WHERE reports.reporter_id = user_id AND reports.status = 'APPROVED')")
    private int reportsApproved;

    @Formula("(SELECT coalesce(count(reports.report_id),0) FROM reports WHERE reports.reporter_id = user_id AND reports.status = 'REJECTED')")
    private int reportsRejected;
    @Formula("(SELECT coalesce(count(trips.trip_id),0) FROM trips WHERE trips.driver_id = user_id)")
    private int tripCount;

    @ManyToOne(fetch=FetchType.EAGER,optional=false)
    @JoinColumn( name = "city_id")
    private City bornCity;
    @Column(name = "mail_locale")
    //Si no funciona, ir aca
    //https://www.baeldung.com/jpa-attribute-converters
    private Locale mailLocale;
    @Column(name = "user_role")
    private String role;

    @Column(name="user_image_id")
    private Long userImageId;

    protected User(){

    }

    public User(final String name, final String surname, final String email,
                final String phone, String password, final City bornCity, final Locale mailLocale, final String role,long userImageId) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.bornCity = bornCity;
        this.mailLocale = mailLocale;
        this.role = role;
        this.userImageId = userImageId;
        this.enabled = false;
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
        this.enabled = false;
        this.banned = false;
    }

    @Override
    public String toString() {
        return String.format("User { id: %d, name: '%s', surname: '%s', email: '%s', phone: '%s', bornCity: '%s', mailLocale: '%s', role: '%s', userImageId: %d }",
                userId, name, surname, email, phone, bornCity, mailLocale, role, userImageId);
    }

    public boolean isBanned() {
        return banned;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
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
    public final boolean equals(Object o) {
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

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBornCity(City bornCity) {
        this.bornCity = bornCity;
    }

    public void setMailLocale(Locale mailLocale) {
        this.mailLocale = mailLocale;
    }

    public double getPassengerRating() {
        return passengerRating;
    }

    public double getDriverRating() {
        return driverRating;
    }

    public int getReportsPublished() {
        return reportsPublished;
    }

    public int getReportsReceived() {
        return reportsReceived;
    }

    public int getReportsApproved() {
        return reportsApproved;
    }

    public int getReportsRejected() {
        return reportsRejected;
    }

    public int getTripCount(){ return tripCount; }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    public boolean isEnabled() {
        return enabled;
    }
    public boolean getIsDriver() {
        return this.role.equals(UserRole.DRIVER.getText()) || this.role.equals(UserRole.ADMIN.getText());
    }
    public boolean getIsUser() {
        return this.role.equals(UserRole.USER.getText());
    }
    public boolean getIsAdmin() {
        return this.role.equals(UserRole.ADMIN.getText());
    }
}
