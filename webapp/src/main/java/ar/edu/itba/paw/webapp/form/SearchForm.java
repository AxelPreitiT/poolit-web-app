package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.form.annotations.CityId;
import org.springframework.format.annotation.DateTimeFormat;

//@DateAndTime
public class SearchForm {
    @CityId
    private int originCityId;
    @CityId
    private int destinationCityId;


    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private String dateTime = "";
//    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
//    private String time = "";

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

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
