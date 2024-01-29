package ar.edu.itba.paw.webapp.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

//https://docs.spring.io/spring-security/site/docs/4.0.x/apidocs/org/springframework/security/authentication/AuthenticationProvider.html
@Component
public class EmailAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    @Autowired
    public EmailAuthenticationProvider(final UserDetailsService userDetailsService){
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final String email = (String) authentication.getCredentials();
        final UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        if(!userDetails.isEnabled()){
            throw new DisabledException("Account is disabled");
        }
        if(!userDetails.isAccountNonLocked()){
            throw new LockedException("Account is locked");
        }
        return new EmailAuthenticationToken(userDetails,userDetails.getAuthorities());
    }

    //Use this provider for EmailAuthenticationToken, used to authenticate without password
    @Override
    public boolean supports(Class<?> authentication) {
        return (EmailAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
