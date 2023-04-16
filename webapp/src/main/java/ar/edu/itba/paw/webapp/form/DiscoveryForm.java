package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.form.annotations.DateAndTime;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Past;

@DateAndTime
public class DiscoveryForm {
    private int originCityId;

    private int destinationCityId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private String date;
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private String time;

    public int getOriginCityId() {
        return originCityId;
    }

    public void setOriginCityId(int originCityId) {
        this.originCityId = originCityId;
    }

    public int getDestinationCityId() {
        return destinationCityId;
    }

    public void setDestinationCityId(int destinationCityId) {
        this.destinationCityId = destinationCityId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
