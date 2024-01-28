package ar.edu.itba.paw.webapp.dto.output.user;

import ar.edu.itba.paw.models.User;

import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "")
public class DriverUserDto extends PublicUserDto{
    private String email;
    private String phone;

    public DriverUserDto(){}
    protected DriverUserDto(final UriInfo uriInfo, final User user){
        super(uriInfo,user);
        this.email = user.getEmail();
        this.phone = user.getPhone();
    }
    public static DriverUserDto fromUser(final UriInfo uriInfo, final User user){
        return new DriverUserDto(uriInfo,user);
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
}
