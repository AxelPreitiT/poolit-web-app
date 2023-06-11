package ar.edu.itba.paw.models;

import javax.persistence.*;
import java.sql.CallableStatement;
import java.util.Date;

@Entity
@Table(name = "tokens")
public class VerificationToken {

    @Id
    @GeneratedValue(strategy =  GenerationType.SEQUENCE, generator ="tokens_token_id_seq" )
    @SequenceGenerator(sequenceName = "tokens_token_id_seq" , name = "tokens_token_id_seq", allocationSize = 1)
    @Column(name = "token_id")
    private long tokenId;

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

    public void setTokenId(long tokenId) {
        this.tokenId = tokenId;
    }

    public long getTokenId() {
        return tokenId;
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

    public void setDate(Date date) {
        this.date = date;
    }
}
