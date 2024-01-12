package ar.edu.itba.paw.webapp.dto.output;

import ar.edu.itba.paw.models.City;
import ar.edu.itba.paw.webapp.controller.utils.UrlHolder;

import javax.ws.rs.core.UriInfo;
import java.net.URI;

public class CityDto {

    private String name;

    private long id;

    private URI selfUri;

    public static CityDto fromCity(final UriInfo uriInfo, final City city){
        CityDto ans = new CityDto();
        ans.id = city.getId();
        ans.name = city.getName();
        ans.selfUri = uriInfo.getBaseUriBuilder().path(UrlHolder.CITY_BASE).path(String.valueOf(city.getId())).build();
        return ans;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public URI getSelfUri() {
        return selfUri;
    }

    public void setSelfUri(URI selfUri) {
        this.selfUri = selfUri;
    }
}
