package ar.edu.itba.paw.webapp.config;

import ar.edu.itba.paw.models.UserRole;
import ar.edu.itba.paw.webapp.auth.AuthValidator;
import ar.edu.itba.paw.webapp.auth.BasicAuthFilter;
import ar.edu.itba.paw.webapp.auth.JwtFilter;
import ar.edu.itba.paw.webapp.auth.PawUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.FileCopyUtils;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

@EnableWebSecurity
@ComponentScan({"ar.edu.itba.paw.webapp.auth"})
@Configuration
public class WebAuthConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private BasicAuthFilter basicAuthFilter;

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        String hierarchy = String.format("%s > %s and %s > %s",
                UserRole.ADMIN_ROLE.getText(),
                UserRole.DRIVER_ROLE.getText(),
                UserRole.ADMIN_ROLE.getText(),
                UserRole.USER_ROLE.getText());
        roleHierarchy.setHierarchy(hierarchy);
        return roleHierarchy;
    }

    @Value("classpath:rememberMeKey.pem")
    private Resource rememberMeKey;
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests()
                .antMatchers("/api/users/{id}").access("@authValidator.checkIfWantedIsSelf(request,#id)")
                    //.antMatchers("/admin", "/admin/*").hasRole(UserRole.ADMIN.getText())
                    //.antMatchers("/users/login", "/users/create", "/users/sendToken").anonymous()
                    //.antMatchers("/trips/{id:\\d+$}/delete").access("@authValidator.checkIfUserIsTripCreator(request)")
                    //.antMatchers("/cars/{id:\\d+$}").permitAll()
                    //.antMatchers("/changeRole").hasRole(UserRole.USER.getText())
                    //.antMatchers("/trips/create", "/cars/**", "/users/created, /trips/created/**").hasRole(UserRole.DRIVER.getText())
                    //.antMatchers("/trips/reserved/**").authenticated()
                    //.antMatchers("/trips", "/trips/", "/trips/{id:\\d+$}").permitAll()
                    //.antMatchers(  "/users/**", "/trips/{id:\\d+$}/join", "/trips/{id:\\d+$}/cancel", "/trips/{id:\\d+$}/review").authenticated()
                    .antMatchers("/**").permitAll()
                .and().exceptionHandling()
                .accessDeniedHandler((req, res, ex) -> { //TODO: ver si vale la pena llevarlos a otro archivo
                    res.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    res.setContentType(MediaType.APPLICATION_JSON);
                    res.getWriter().write(String.format("{\n \"message:\" : \"%s\"\n}",ex.getMessage()));
                })
                .authenticationEntryPoint((req, res, ex) -> {
                    res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    res.setContentType(MediaType.APPLICATION_JSON);
                    res.getWriter().write(String.format("{\n \"message:\" : \"%s\"\n}",ex.getMessage()));
                })
//                    .accessDeniedPage("/static/403")
                .and().cors()
                .and().csrf().disable()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(basicAuthFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "favicon.ico", "/static/403");
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
