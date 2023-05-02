package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.form.annotations.DateAndTimeSearch;
import ar.edu.itba.paw.webapp.form.annotations.MultitripSearch;
import ar.edu.itba.paw.webapp.form.annotations.Price;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

@DateAndTimeSearch
@MultitripSearch
@Price
public class SearchTripForm {

    @Min(value = 1)
    private long originCityId;

    @Min(value = 1)
    private long destinationCityId;

    @Pattern(regexp = "(^(((0[1-9]|1[0-9]|2[0-8])[\\/](0[1-9]|1[012]))|((29|30|31)[\\/](0[13578]|1[02]))|((29|30)[\\/](0[4,6,9]|11)))[\\/](19|[2-9][0-9])\\d\\d$)|(^29[\\/]02[\\/](19|[2-9][0-9])(00|04|08|12|16|20|24|28|32|36|40|44|48|52|56|60|64|68|72|76|80|84|88|92|96)$)")
    private String date;

    @Pattern(regexp = "^([01]?[0-9]|2[0-3]):[0-5][0-9]$")
    private String time;

    private boolean multitrip;

    @Pattern(regexp = "(^(((0[1-9]|1[0-9]|2[0-8])[\\/](0[1-9]|1[012]))|((29|30|31)[\\/](0[13578]|1[02]))|((29|30)[\\/](0[4,6,9]|11)))[\\/](19|[2-9][0-9])\\d\\d$)|(^29[\\/]02[\\/](19|[2-9][0-9])(00|04|08|12|16|20|24|28|32|36|40|44|48|52|56|60|64|68|72|76|80|84|88|92|96)$)")
    private String lastDate;


    private Double minPrice;

    private Double maxPrice;


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

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }
}
