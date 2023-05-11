package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.form.annotations.LastDateIsAfterDate;
import ar.edu.itba.paw.webapp.form.annotations.Price;
import ar.edu.itba.paw.webapp.form.annotations.SameWeekDay;
import ar.edu.itba.paw.webapp.form.annotations.TodayOrLater;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@SameWeekDay
@LastDateIsAfterDate
@Price
public class SearchTripForm {

    @Min(value = 1)
    private long originCityId;

    @Min(value = 1)
    private long destinationCityId;

    @NotNull
    @TodayOrLater
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate date;

    @NotNull
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime time;

    private boolean multitrip;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate lastDate;

    @Min(value = 0)
    private BigDecimal minPrice;

    @Min(value = 0)
    private BigDecimal maxPrice;


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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public void setLastDate(LocalDate lastDate) {
        this.lastDate = lastDate;
    }

    public LocalDate getLastDate() {
        return lastDate;
    }

    public boolean isMultitrip() {
        return multitrip;
    }

    public void setMultitrip(boolean multitrip) {
        this.multitrip = multitrip;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
    }
}
