package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;

@Component
public class PawUserDetailsService implements UserDetailsService {

    private final static Logger LOGGER = LoggerFactory.getLogger(PawUserDetailsService.class);

    private final UserService us;

    @Autowired
    public PawUserDetailsService(final UserService us){
        this.us=us;
    }

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        final Optional<User> userOptional = us.findByEmail(email);
        if(!userOptional.isPresent()){
            final UsernameNotFoundException e = new UsernameNotFoundException("No user found for email " + email);
            LOGGER.error("No user found for email {}", email, e);
            throw e;
        }
        final Collection<GrantedAuthority> authorities = new HashSet<>();

        final User user = userOptional.get();

        if(Objects.equals(user.getRole(), UserRole.ADMIN.getText())){
            authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN_ROLE.getText()));
        }else{
            if(Objects.equals(user.getRole(), UserRole.DRIVER.getText())){
                authorities.add(new SimpleGrantedAuthority(UserRole.DRIVER_ROLE.getText()));
            } else {
                authorities.add(new SimpleGrantedAuthority(UserRole.USER_ROLE.getText()));
            }
        }
        return new org.springframework.security.core.userdetails.User(email, user.getPassword(), user.isEnabled(), true, true, !user.isBanned(), authorities);
    }


    public void update(User user) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        final Collection<GrantedAuthority> authorities = new HashSet<>();
        if(Objects.equals(user.getRole(), UserRole.USER.getText())){
            authorities.add(new SimpleGrantedAuthority(UserRole.DRIVER_ROLE.getText()));
        }else {
            authorities.add(new SimpleGrantedAuthority(UserRole.USER_ROLE.getText()));
        }

        Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(), authorities);
        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }
}
