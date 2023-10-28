package ar.edu.itba.paw.webapp.dto.output.user;


import ar.edu.itba.paw.models.User;

import javax.ws.rs.core.UriInfo;
import java.net.URI;

//TODO: preguntar si hacer esto esta bien como para tener distintos tipos
//TODO: preguntar por type en la respuesta
public class PrivateUserDto extends PublicUserDto{

    private String email;

    private String phone;

    private long cityId; //TODO: ver que hacer con esto, vale la pena un controller para /city

    private URI cityUri;

    private String mailLocale;

    private URI roleUri;

    public PrivateUserDto(){}

    protected PrivateUserDto(final UriInfo uriInfo, final User user){
        super(uriInfo,user);
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.cityId = user.getBornCity().getId();
        this.cityUri = uriInfo.getBaseUriBuilder().path("/api/cities/").path(String.valueOf(user.getBornCity().getId())).build();
        this.mailLocale = user.getMailLocale().getLanguage();
        this.roleUri = uriInfo.getBaseUriBuilder().path("/api/users/").path(String.valueOf(user.getUserId())).path("role/").build();
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
}
