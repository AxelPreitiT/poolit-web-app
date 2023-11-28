package ar.edu.itba.paw.webapp.dto.input;

import ar.edu.itba.paw.webapp.dto.validation.annotations.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@MaxSeatsLessThanCarSeats
@LastDateNotNullIfMultitrip
@SameWeekDay
@LastDateIsAfterDate
@NowOrLater
public class CreateTripDto {

    @CityId
    private int originCityId;

    @Size(min = 5, max = 50)
    @NotNull
    private String originAddress;

    @CityId
    private int destinationCityId;

    @NotNull
    @Size(min = 5, max = 50)
    private String destinationAddress;

    @Min(value = 1)
    @CarFromUser
    private long carId;

    @NotNull
    @Min(value = 1)
    private Integer maxSeats;

    @NotNull
    @Min(value = 0)
    private BigDecimal price;


//    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull(message = "{dto.validation.dateFormat}")
    private LocalDate date;

//    private String date;

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @NotNull(message = "{dto.validation.timeFormat}")
    private LocalTime time;

//    private String time;
    private boolean multitrip;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate lastDate;

//    private String lastDate;
    public int getOriginCityId() {
        return originCityId;
    }

    public void setOriginCityId(int originCityId) {
        this.originCityId = originCityId;
    }

    public String getOriginAddress() {
        return originAddress;
    }

    public void setOriginAddress(String originAddress) {
        this.originAddress = originAddress;
    }

    public int getDestinationCityId() {
        return destinationCityId;
    }

    public void setDestinationCityId(int destinationCityId) {
        this.destinationCityId = destinationCityId;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public long getCarId() {
        return carId;
    }

    public void setCarId(long carId) {
        this.carId = carId;
    }

    public Integer getMaxSeats() {
        return maxSeats;
    }

    public void setMaxSeats(Integer maxSeats) {
        this.maxSeats = maxSeats;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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

    public boolean isMultitrip() {
        return multitrip;
    }

    public void setMultitrip(boolean multitrip) {
        this.multitrip = multitrip;
    }

    public LocalDate getLastDate() {
        return lastDate;
    }

    public void setLastDate(LocalDate lastDate) {
        this.lastDate = lastDate;
    }
}
