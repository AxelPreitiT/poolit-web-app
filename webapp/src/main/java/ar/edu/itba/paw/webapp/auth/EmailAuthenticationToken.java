package ar.edu.itba.paw.webapp.auth;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class EmailAuthenticationToken extends AbstractAuthenticationToken {

    private UserDetails userDetails;
    private String email;

    public EmailAuthenticationToken(final String email){
        super(Collections.emptyList());
        this.email = email;
        this.setAuthenticated(false);//needs to go through the auth provider
    }

    public EmailAuthenticationToken(final UserDetails userDetails, final Collection<? extends GrantedAuthority> authorities){
        super(authorities);
        this.userDetails = userDetails;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return email;
    }

    @Override
    public Object getPrincipal() {
        return userDetails;
    }

    @Override
    public Object getDetails() {
        return userDetails;
    }
}
