package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.form.annotations.MPFile;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UpdateUserForm {
    @Pattern(regexp = ".+")
    @Size(min = 2, max = 20)
    private String username;

    @Size(min = 2, max = 30)
    @Pattern(regexp = ".+")
    private String surname;

    @Pattern(regexp = "^(\\+\\d{1,3}\\s?)?\\d{2,4}\\s?\\d{4}\\s?\\d{4}$")
    private String phone;

    @Min(value = 1)
    private long bornCityId;

    @Pattern(regexp = "en|es")
    private String mailLocale;

    @MPFile
    private MultipartFile imageFile;

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

    public long getBornCityId() {
        return bornCityId;
    }

    public void setBornCityId(long bornCityId) {
        this.bornCityId = bornCityId;
    }

    public String getMailLocale() {
        return mailLocale;
    }

    public void setMailLocale(String mailLocale) {
        this.mailLocale = mailLocale;
    }

    public MultipartFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }
}
