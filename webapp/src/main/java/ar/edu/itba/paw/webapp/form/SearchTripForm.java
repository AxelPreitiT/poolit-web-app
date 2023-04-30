package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.form.annotations.DateAndTime;
import ar.edu.itba.paw.webapp.form.annotations.Multitrip;
import ar.edu.itba.paw.webapp.form.annotations.Price;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;

@DateAndTime
@Multitrip
@Price
public class SearchTripForm {

    @Min(value = 0)
    private long originCityId;

    @Min(value = 0)
    private long destinationCityId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private String date;

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private String time;

    private boolean multitrip;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private String lastDate;

    private int minPrice;

    private int maxPrice;


    public long getOriginCityId() {
        return originCityId;
    }

    public void setOriginCityId(long originCityId) {
        this.originCityId = originCityId;
    }

    public long getDestinationCityId() {
        return destinationCityId;
    }

    public void setDestinationCityId(long destinationCityId) {
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

    public boolean isMultitrip() {
        return multitrip;
    }

    public void setMultitrip(boolean multitrip) {
        this.multitrip = multitrip;
    }

    public String getLastDate() {
        return lastDate;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

    public int getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(int minPrice) {
        this.minPrice = minPrice;
    }

    public int getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;
    }
}
