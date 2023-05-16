package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.form.annotations.LastDateIsAfterDate;
import ar.edu.itba.paw.webapp.form.annotations.Price;
import ar.edu.itba.paw.webapp.form.annotations.SameWeekDay;

import javax.validation.constraints.Min;
import java.math.BigDecimal;

@SameWeekDay
@LastDateIsAfterDate
@Price
public class SearchTripForm extends RecurrentTripForm {

    @Min(value = 1)
    private long originCityId;

    @Min(value = 1)
    private long destinationCityId;

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
