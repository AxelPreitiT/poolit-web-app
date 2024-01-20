package ar.edu.itba.paw.webapp.dto.output;

import ar.edu.itba.paw.webapp.controller.utils.UrlHolder;
import org.springframework.web.util.UriComponentsBuilder;

import javax.ws.rs.Path;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

//https://datatracker.ietf.org/doc/html/rfc6570
//https://datatracker.ietf.org/doc/html/rfc6570#section-3.2.8
public class BaseDto {

    private String citiesUri;
    private String carsUri;
    private String usersUri;
    private String reportsUri;
    private String driverReviewsUri;
    private String passengerReviewsUri;
    private String tripsUri;
    public static BaseDto fromUriInfo(final UriInfo uriInfo){
        //use Spring's builder because of path() method implementation (jersey adds a / all the time)
        UriComponentsBuilder builder = UriComponentsBuilder.fromUri(uriInfo.getBaseUriBuilder().build());
        BaseDto ans = new BaseDto();
        //No ponemos todos los query parameters, si no queda más confuso
        //Lo ponemos en la documentación (como acá: https://docs.github.com/en/rest/issues/issues?apiVersion=2022-11-28#list-issues-assigned-to-the-authenticated-user)
//        ans.citiesUri = uriInfo.getBaseUriBuilder().path(UrlHolder.CITY_BASE).toTemplate() + "{/cityId}"; //prevent builder from adding '/' in front of variable
        ans.citiesUri = builder.cloneBuilder().path(UrlHolder.CITY_BASE).path("{/carId}").build().toString();
//        ans.carsUri = uriInfo.getBaseUriBuilder().path(UrlHolder.CAR_BASE).toTemplate() + "{/carId}";
        ans.carsUri = builder.cloneBuilder().path(UrlHolder.CAR_BASE).path("{/carId}").build().toString();
//        ans.usersUri = uriInfo.getBaseUriBuilder().path(UrlHolder.USER_BASE).path("{userId}").toTemplate();
        ans.usersUri = builder.cloneBuilder().path(UrlHolder.USER_BASE).pathSegment("{userId}").build().toString();
//        ans.reportsUri = uriInfo.getBaseUriBuilder().path(UrlHolder.REPORT_BASE).toTemplate() + "{/reportId}";
        ans.reportsUri = builder.cloneBuilder().path(UrlHolder.REPORT_BASE).path("{/reportId}").build().toString();
//        ans.driverReviewsUri = uriInfo.getBaseUriBuilder().path(UrlHolder.DRIVER_REVIEWS_BASE).toTemplate() + "{/reviewId}";
        ans.driverReviewsUri = builder.cloneBuilder().path(UrlHolder.DRIVER_REVIEWS_BASE).path("{/reviewId}").build().toString();
//        ans.passengerReviewsUri = uriInfo.getBaseUriBuilder().path(UrlHolder.PASSENGER_REVIEWS_BASE).toTemplate() + "{/reviewId}";
        ans.passengerReviewsUri = builder.cloneBuilder().path(UrlHolder.PASSENGER_REVIEWS_BASE).path("{/reviewId}").build().toString();
//        ans.tripsUri = uriInfo.getBaseUriBuilder().path(UrlHolder.TRIPS_BASE).toTemplate() + "{/tripId}";
        ans.tripsUri = builder.cloneBuilder().path(UrlHolder.TRIPS_BASE).path("{/tripId}").build().toString();
        return ans;
    }

    public String getCitiesUri() {
        return citiesUri;
    }

    public void setCitiesUri(String citiesUri) {
        this.citiesUri = citiesUri;
    }

    public String getCarsUri() {
        return carsUri;
    }

    public void setCarsUri(String carsUri) {
        this.carsUri = carsUri;
    }

    public String getUsersUri() {
        return usersUri;
    }

    public void setUsersUri(String usersUri) {
        this.usersUri = usersUri;
    }

    public String getReportsUri() {
        return reportsUri;
    }

    public void setReportsUri(String reportsUri) {
        this.reportsUri = reportsUri;
    }

    public String getDriverReviewsUri() {
        return driverReviewsUri;
    }

    public void setDriverReviewsUri(String driverReviewsUri) {
        this.driverReviewsUri = driverReviewsUri;
    }

    public String getPassengerReviewsUri() {
        return passengerReviewsUri;
    }

    public void setPassengerReviewsUri(String passengerReviewsUri) {
        this.passengerReviewsUri = passengerReviewsUri;
    }

    public String getTripsUri() {
        return tripsUri;
    }

    public void setTripsUri(String tripsUri) {
        this.tripsUri = tripsUri;
    }
}
