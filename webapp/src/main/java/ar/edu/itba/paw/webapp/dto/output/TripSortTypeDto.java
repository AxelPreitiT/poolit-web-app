package ar.edu.itba.paw.webapp.dto.output;

import ar.edu.itba.paw.models.trips.Trip;
import ar.edu.itba.paw.webapp.controller.utils.UrlHolder;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import javax.ws.rs.core.UriInfo;
import java.net.URI;

public class TripSortTypeDto {

    private String id;

    private String name;

    private URI selfUri;

    public static TripSortTypeDto fromTripSortType(final UriInfo uriInfo, final Trip.SortType sortType, final MessageSource messageSource){
        TripSortTypeDto ans = new TripSortTypeDto();
        ans.id = sortType.name();
        ans.name = messageSource.getMessage(sortType.getCode(),null, LocaleContextHolder.getLocale());
        ans.selfUri = uriInfo.getBaseUriBuilder().path(UrlHolder.TRIP_SORT_TYPE_BASE).path(sortType.name()).build();
        return ans;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public URI getSelfUri() {
        return selfUri;
    }

    public void setSelfUri(URI selfUri) {
        this.selfUri = selfUri;
    }
}
