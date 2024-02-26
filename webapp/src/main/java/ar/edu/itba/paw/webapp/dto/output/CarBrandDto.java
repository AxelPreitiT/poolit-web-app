package ar.edu.itba.paw.webapp.dto.output;

import ar.edu.itba.paw.models.CarBrand;
import ar.edu.itba.paw.webapp.controller.utils.UrlHolder;

import javax.ws.rs.core.UriInfo;
import java.net.URI;

public class CarBrandDto {

    private String id;

    private String name;

    private URI selfUri;

    public static CarBrandDto fromCarBrand(final UriInfo uriInfo, final CarBrand carBrand){
        CarBrandDto ans = new CarBrandDto();
        ans.id = carBrand.name();
        ans.name = carBrand.toString();
        ans.selfUri = uriInfo.getBaseUriBuilder().path(UrlHolder.CAR_BRAND_BASE).path(carBrand.name()).build();
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
