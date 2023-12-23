package ar.edu.itba.paw.webapp.dto.output.user;


import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.controller.utils.UrlHolder;

import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.time.LocalDateTime;

//TODO: preguntar si hacer esto esta bien como para tener distintos tipos
//TODO: preguntar por type en la respuesta
//TODO: si puedo poner campos condicionalmente dependiendo de quien pregunta, pasar esto a un unico usuario
public class PrivateUserDto extends PublicUserDto{

    private String email;

    private String phone;

    private long cityId; //TODO: ver que hacer con esto, vale la pena un controller para /city

    private URI cityUri;

    private String mailLocale;

    private URI roleUri;

    private URI recommendedTripsUri;

    private URI pastCreatedTripsUri;

    private URI futureCreatedTripsUri;

    private URI pastReservedTripsUri;

    private URI futureReservedTripsUri;

    public PrivateUserDto(){}

    protected PrivateUserDto(final UriInfo uriInfo, final User user){
        super(uriInfo,user);
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.cityId = user.getBornCity().getId();
        this.cityUri = uriInfo.getBaseUriBuilder().path(UrlHolder.CITY_BASE).path(String.valueOf(user.getBornCity().getId())).build();
        this.mailLocale = user.getMailLocale().getLanguage();
        this.roleUri = uriInfo.getBaseUriBuilder().path(UrlHolder.USER_BASE).path(String.valueOf(user.getUserId())).path("role/").build();
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

    public long getCityId() {
        return cityId;
    }

    public void setCityId(long cityId) {
        this.cityId = cityId;
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

    public URI getRoleUri() {
        return roleUri;
    }

    public void setRoleUri(URI roleUri) {
        this.roleUri = roleUri;
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
}
