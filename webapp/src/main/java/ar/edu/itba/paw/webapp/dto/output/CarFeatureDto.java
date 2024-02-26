package ar.edu.itba.paw.webapp.dto.output;

import ar.edu.itba.paw.models.FeatureCar;
import ar.edu.itba.paw.webapp.controller.utils.UrlHolder;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import javax.ws.rs.core.UriInfo;
import java.net.URI;

public class CarFeatureDto {

    private String id;

    private String name;

    private URI selfUri;

    public static CarFeatureDto fromCarFeature(final UriInfo uriInfo, final FeatureCar featureCar, final MessageSource messageSource){
        CarFeatureDto ans = new CarFeatureDto();
        ans.id = featureCar.name();
        ans.name = messageSource.getMessage(featureCar.getCode(),null, LocaleContextHolder.getLocale());
        ans.selfUri = uriInfo.getBaseUriBuilder().path(UrlHolder.CAR_FEATURE_BASE).path(featureCar.name()).build();
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
