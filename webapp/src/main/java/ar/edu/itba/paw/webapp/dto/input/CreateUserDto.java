package ar.edu.itba.paw.webapp.dto.input;

import ar.edu.itba.paw.webapp.dto.validation.annotations.Email;
import ar.edu.itba.paw.webapp.dto.validation.annotations.Locale;
import ar.edu.itba.paw.webapp.dto.validation.annotations.Phone;

import javax.validation.constraints.*;
public class CreateUserDto {

    @Size(min = 2, max = 20)
    @NotNull
    private String username;

    @Size(min = 2, max = 30)
    @NotNull
    private String surname;

    @NotNull
    @Size(max = 50)
    @Email
    private String email;

    @NotNull
    @Phone
    private String phone;

    @NotNull
    @Size(min = 3, max = 20)
    private String password;

    @Min(value = 1)
    private int bornCityId; //TODO: revisar si conviene hacerlo con un stirng

    @NotNull
    @Locale
    private String mailLocale;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getBornCityId() {
        return bornCityId;
    }

    public void setBornCityId(int bornCityId) {
        this.bornCityId = bornCityId;
    }

    public String getMailLocale() {
        return mailLocale;
    }

    public void setMailLocale(String mailLocale) {
        this.mailLocale = mailLocale;
    }
}

