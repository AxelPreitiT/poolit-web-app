package ar.edu.itba.paw.webapp.dto.output.user;


import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserRole;
import ar.edu.itba.paw.webapp.controller.utils.UrlHolder;

import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlType;
import java.net.URI;

//Avoid 'type' field from being added to response on JSON
@XmlType(name = "")
public class PrivateUserDto extends PublicUserDto{
    private long userId;

    private String email;

    private String phone;

    private String role;

    private URI cityUri;

    private String mailLocale;

    private URI recommendedTripsUri;

    private URI pastCreatedTripsUri;

    private URI futureCreatedTripsUri;

    private URI pastReservedTripsUri;

    private URI futureReservedTripsUri;

    private URI carsUri;

    public PrivateUserDto(){}

    protected PrivateUserDto(final UriInfo uriInfo, final User user){
        super(uriInfo,user);
        this.userId = user.getUserId();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.cityUri = uriInfo.getBaseUriBuilder().path(UrlHolder.CITY_BASE).path(String.valueOf(user.getBornCity().getId())).build();
        this.mailLocale = user.getMailLocale().getLanguage();
        this.role = user.getRole();
        this.carsUri = uriInfo.getBaseUriBuilder().path(UrlHolder.CAR_BASE).queryParam("fromUser",user.getUserId()).build();
        //We use recommendedFor and not pass the filters here to maintain the recommendation logic in the service
        this.recommendedTripsUri = uriInfo.getBaseUriBuilder().path(UrlHolder.TRIPS_BASE).queryParam("recommendedFor",user.getUserId()).build();
        this.pastCreatedTripsUri = uriInfo.getBaseUriBuilder().path(UrlHolder.TRIPS_BASE).queryParam("createdBy",user.getUserId()).queryParam("past",true).build();
        this.futureCreatedTripsUri = uriInfo.getBaseUriBuilder().path(UrlHolder.TRIPS_BASE).queryParam("createdBy",user.getUserId()).queryParam("past",false).build();
        this.pastReservedTripsUri = uriInfo.getBaseUriBuilder().path(UrlHolder.TRIPS_BASE).queryParam("reservedBy",user.getUserId()).queryParam("past",true).build();
        this.futureReservedTripsUri = uriInfo.getBaseUriBuilder().path(UrlHolder.TRIPS_BASE).queryParam("reservedBy",user.getUserId()).queryParam("past",false).build();
    }
    public static PrivateUserDto fromUser(final UriInfo uriInfo, final User user){
        return new PrivateUserDto(uriInfo,user);
    }

    @Override
    public long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public URI getCityUri() {
        return cityUri;
    }

    public void setCityUri(URI cityUri) {
        this.cityUri = cityUri;
    }

    public String getMailLocale() {
        return mailLocale;
    }

    public void setMailLocale(String mailLocale) {
        this.mailLocale = mailLocale;
    }
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public URI getRecommendedTripsUri() {
        return recommendedTripsUri;
    }

    public void setRecommendedTripsUri(URI recommendedTripsUri) {
        this.recommendedTripsUri = recommendedTripsUri;
    }

    public URI getPastCreatedTripsUri() {
        return pastCreatedTripsUri;
    }

    public void setPastCreatedTripsUri(URI pastCreatedTripsUri) {
        this.pastCreatedTripsUri = pastCreatedTripsUri;
    }

    public URI getFutureCreatedTripsUri() {
        return futureCreatedTripsUri;
    }

    public void setFutureCreatedTripsUri(URI futureCreatedTripsUri) {
        this.futureCreatedTripsUri = futureCreatedTripsUri;
    }

    public URI getPastReservedTripsUri() {
        return pastReservedTripsUri;
    }

    public void setPastReservedTripsUri(URI pastReservedTripsUri) {
        this.pastReservedTripsUri = pastReservedTripsUri;
    }

    public URI getFutureReservedTripsUri() {
        return futureReservedTripsUri;
    }

    public void setFutureReservedTripsUri(URI futureReservedTripsUri) {
        this.futureReservedTripsUri = futureReservedTripsUri;
    }

    public URI getCarsUri() { return carsUri; }

    public void setCarsUri(URI carsUri) { this.carsUri = carsUri; }


}
