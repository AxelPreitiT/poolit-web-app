package ar.edu.itba.paw.webapp.controller.utils.queryBeans;

import ar.edu.itba.paw.models.FeatureCar;
import ar.edu.itba.paw.models.trips.Trip;
import ar.edu.itba.paw.webapp.dto.validation.annotations.CityId;
import ar.edu.itba.paw.webapp.dto.validation.annotations.Exclusive;
import ar.edu.itba.paw.webapp.dto.validation.annotations.NotAllNull;
import ar.edu.itba.paw.webapp.dto.validation.annotations.NotNullTogether;

import javax.validation.constraints.Min;
import javax.ws.rs.QueryParam;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@NotAllNull
@NotNullTogether(fields = {"originCityId","destinationCityId","startDateTime"})
//Cuando se usa alguno de los que referencia a usuarios, no se puede usar el resto
@Exclusive(group1 = {"recommendedFor","createdBy","reservedBy","past"},group2 = {"originCityId","destinationCityId","startDateTime","endDateTime","minPrice","maxPrice","carFeatures","sortType","descending"})
//Cuando se usa el de recomendación, no se pueden usar tampoco los que miran creados, reservados y si están en el pasado
@Exclusive(group1 = {"recommendedFor"},group2 = {"createdBy","reservedBy","past"})
//Cuando se usa el de creados, no se pueden usar el de reservados
@Exclusive(group1 = {"createdBy"},group2 = {"reservedBy"})
//No quiero que se use la intersección de los grupos, y no ponemos los casos análogos (g1=g2, g2=g1) porque con eso se repite el error
//TODO: add validations
//@SameWeekDay
//@LastDateIsAfterDate
//@MinPriceLessThanMaxPrice (@Price)
public class TripsQuery extends PagedQuery{
    @QueryParam("originCityId")
    @CityId
    private Integer originCityId;

    @QueryParam("destinationCityId")
    @CityId
    private Integer destinationCityId;

    @QueryParam("startDateTime")
    private LocalDateTime startDateTime;

    @QueryParam("endDateTime")
    private LocalDateTime endDateTime;

    @QueryParam("minPrice")
    @Min(0)
    private BigDecimal minPrice;

    @QueryParam("maxPrice")
    @Min(0)
    private BigDecimal maxPrice;

    @QueryParam("carFeatures")
    List<FeatureCar> carFeatures;

    @QueryParam("sortType")
    private Trip.SortType sortType;

    @QueryParam("createdBy")
    private Integer createdBy;

    @QueryParam("reservedBy")
    private Integer reservedBy;

    @QueryParam("recommendedFor")
    private Integer recommendedFor;

    @QueryParam("past")
    private Boolean past;

    @QueryParam("descending")
    private Boolean descending;

    public Integer getOriginCityId() {
        return originCityId;
    }

    public Integer getDestinationCityId() {
        return destinationCityId;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public List<FeatureCar> getCarFeatures() {
        return carFeatures;
    }

    public Trip.SortType getSortType() {
        //agregamos los defaults acá para que funcione el annotation de Exclusive para los filtros
        return sortType!=null?sortType: Trip.SortType.PRICE;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public Integer getReservedBy() {
        return reservedBy;
    }

    public Integer getRecommendedFor() {
        return recommendedFor;
    }

    public boolean isPast() {
        return past != null? past:false;
    }

    public boolean isDescending() {
        return descending != null? descending:false;
    }
}
