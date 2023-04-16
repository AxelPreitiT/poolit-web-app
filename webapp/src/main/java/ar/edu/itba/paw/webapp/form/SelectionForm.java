package ar.edu.itba.paw.webapp.form;

import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

public class SelectionForm {
    @Email
    private String email;

    @Pattern(regexp = "^\\d{2,3}\\s?\\d{4}\\s?\\d{4}")
    private String phone;

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
}
