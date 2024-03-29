package ar.edu.itba.paw.webapp.dto.output;

import ar.edu.itba.paw.models.trips.Trip;
import ar.edu.itba.paw.webapp.controller.utils.UrlHolder;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import javax.ws.rs.core.UriInfo;
import java.net.URI;

public class TripSortTypeDto {

    private String id;

    private String ascName;
    private String descName;

    private URI selfUri;

    public static TripSortTypeDto fromTripSortType(final UriInfo uriInfo, final Trip.SortType sortType, final MessageSource messageSource){
        TripSortTypeDto ans = new TripSortTypeDto();
        ans.id = sortType.name();
        ans.ascName = sortType.hasAscendingSort() ? messageSource.getMessage(sortType.getAscendingCode(),null, LocaleContextHolder.getLocale()) : null;
        ans.descName = sortType.hasDescendingSort() ? messageSource.getMessage(sortType.getDescendingCode(),null, LocaleContextHolder.getLocale()) : null;
        ans.selfUri = uriInfo.getBaseUriBuilder().path(UrlHolder.TRIP_SORT_TYPE_BASE).path(sortType.name()).build();
        return ans;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public URI getSelfUri() {
        return selfUri;
    }

    public void setSelfUri(URI selfUri) {
        this.selfUri = selfUri;
    }

    public String getAscName() {
        return ascName;
    }

    public void setAscName(String ascName) {
        this.ascName = ascName;
    }

    public String getDescName() {
        return descName;
    }

    public void setDescName(String descName) {
        this.descName = descName;
    }
}
