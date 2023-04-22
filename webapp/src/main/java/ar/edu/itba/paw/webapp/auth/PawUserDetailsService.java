package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.User;
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

@Component
public class PawUserDetailsService implements UserDetailsService {

    private final UserService us;

    @Autowired
    public PawUserDetailsService(final UserService us){
        this.us=us;
    }

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        final User user = us.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No user for email " + email));

        //TODO: implementar logica de roles
        final Collection<GrantedAuthority> authorities = new HashSet<>();
        //authorities.add(new SimpleGrantedAuthority("ROLE_DRIVER"));
        authorities.add(new SimpleGrantedAuthority("ROLE_USER_ADMIN"));

        return new AuthUser(email, user.getPassword(), authorities);
    }

    public void chengePepe(){
        final AuthUser authUser = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //serviceAuth
        final Collection<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_DRIVER"));
        authorities.add(new SimpleGrantedAuthority("ROLE_USER_ADMIN"));
        Authentication reAuth = new UsernamePasswordAuthenticationToken(authUser.getUsername(),authUser.getPassword(),authorities);
        SecurityContextHolder.getContext().setAuthentication(reAuth);
    }
}
