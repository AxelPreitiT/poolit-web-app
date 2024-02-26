package ar.edu.itba.paw.webapp.dto.input;

import ar.edu.itba.paw.webapp.dto.validation.annotations.CityId;
import ar.edu.itba.paw.webapp.dto.validation.annotations.Locale;
import ar.edu.itba.paw.webapp.dto.validation.annotations.Phone;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UpdateUserDto {
    @Size(min = 2, max = 20)
    private String username;

    @Size(min = 2, max = 30)
    private String surname;

    @Phone
    private String phone;

    @Min(value = 1)
    @CityId
    private Integer bornCityId;

    @Locale
    private String mailLocale;

    @Pattern(regexp = "DRIVER|USER",message = "{dto.validation.role}")
    private String role;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getBornCityId() {
        return bornCityId;
    }
    public void setBornCityId(Integer bornCityId) {
        this.bornCityId = bornCityId;
    }

    public String getMailLocale() {
        return mailLocale;
    }

    public void setMailLocale(String mailLocale) {
        this.mailLocale = mailLocale;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
