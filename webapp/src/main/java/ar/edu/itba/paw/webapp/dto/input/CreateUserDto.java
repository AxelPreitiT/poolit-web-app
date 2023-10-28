package ar.edu.itba.paw.webapp.dto.input;

import ar.edu.itba.paw.webapp.form.annotations.FieldMatches;
import ar.edu.itba.paw.webapp.form.annotations.MPFile;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

//@FieldMatches(first = "password", second = "repeatPassword")
public class CreateUserDto {

    @Pattern(regexp = ".+")
    @Size(min = 2, max = 20)
    @NotNull
    private String username;

    @Size(min = 2, max = 30)
    @Pattern(regexp = ".+")
    @NotNull
    private String surname;

    @NotNull
    @Size(max = 50)
    @Pattern(regexp = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")
    private String email;

    @NotNull
    @Pattern(regexp = "^(\\+\\d{1,3}\\s?)?\\d{2,4}\\s?\\d{4}\\s?\\d{4}$")
    private String phone;

    @NotNull
    @Size(min = 3, max = 20)
    private String password;

//    private String repeatPassword;

    @Min(value = 1)
    private int bornCityId; //TODO: revisar si conviene hacerlo con un stirng

    @NotNull
    @Pattern(regexp = "en|es")
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

//    public String getRepeatPassword() {
//        return repeatPassword;
//    }
//
//    public void setRepeatPassword(String repeatPassword) {
//        this.repeatPassword = repeatPassword;
//    }

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

