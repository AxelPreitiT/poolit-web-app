package ar.edu.itba.paw.webapp.dto.output;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class UserRoleDto {

    @Pattern(regexp = "DRIVER|USER",message = "{dto.validation.role}")
    @NotNull
    private String role;

    public static UserRoleDto fromString(String role){
        UserRoleDto ans = new UserRoleDto();
        ans.role = role;
        return ans;
    }
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
