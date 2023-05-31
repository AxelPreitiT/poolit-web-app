package ar.edu.itba.paw.models;

import javax.persistence.*;
import java.sql.CallableStatement;
import java.util.Date;

@Entity
@Table(name = "tokens")
public class VerificationToken {

    @Id
    @Column(length = 100, nullable = false)
    private String token;

    @OneToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, name = "date")
    private Date date;

    public VerificationToken(User user, String token, Date date) {
        this.user = user;
        this.token = token;
        this.date = date;
    }

    VerificationToken(){

    }

    public User getUser() {
        return user;
    }

    public String getToken() {
        return token;
    }


    public void setExpiryDate(Date date) {
        this.date = date;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getDate() {
        return date;
    }
}
