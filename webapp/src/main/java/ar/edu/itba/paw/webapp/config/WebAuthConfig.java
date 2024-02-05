package ar.edu.itba.paw.webapp.config;

import ar.edu.itba.paw.models.UserRole;
import ar.edu.itba.paw.webapp.auth.*;
import ar.edu.itba.paw.webapp.controller.utils.UrlHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan({"ar.edu.itba.paw.webapp.auth"})
@PropertySource("classpath:application.properties")
public class WebAuthConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private Environment environment;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private EmailAuthenticationProvider emailAuthenticationProvider;

    @Autowired
    private BasicAuthFilter basicAuthFilter;

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private UnauthorizedRequestHandler unauthorizedRequestHandler;

    @Autowired
    private ForbiddenRequestHandler forbiddenRequestHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
        auth.authenticationProvider(emailAuthenticationProvider);
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
                .and().headers().cacheControl().disable()
                .and().authorizeRequests()
                //--------Users--------
                //Modify user
                .antMatchers(HttpMethod.PATCH,UrlHolder.USER_BASE+"/{id:\\d+}")
                    .access("@authValidator.checkIfWantedIsSelf(#id)")
                //Modify image
                .antMatchers(HttpMethod.PUT,UrlHolder.USER_BASE+"/{id:\\d+}/image")
                    .access("@authValidator.checkIfWantedIsSelf(#id)")
                //--------Trips--------
                //Create trip
                .antMatchers(HttpMethod.POST,UrlHolder.TRIPS_BASE)
                    .hasRole(UserRole.DRIVER.getText())
                //Delete trip
                .antMatchers(HttpMethod.DELETE, UrlHolder.TRIPS_BASE+"/{id:\\d+}")
                    .access("@authValidator.checkIfUserIsTripCreator(#id)")//if it's not a driver, then it fails
                //Add passenger
                .antMatchers(HttpMethod.POST,UrlHolder.TRIPS_BASE+"/{id:\\d+}"+UrlHolder.TRIPS_PASSENGERS)
                    .authenticated()
                //Get single passenger
                .antMatchers(HttpMethod.GET,UrlHolder.TRIPS_BASE+"/{id:\\d+}"+UrlHolder.TRIPS_PASSENGERS+"/{userId:\\d+}")
                    .access("@authValidator.checkIfUserIsTripCreator(#id) or @authValidator.checkIfWantedIsSelf(#userId)")
                //Accept or reject passenger
                .antMatchers(HttpMethod.PATCH,UrlHolder.TRIPS_BASE+"/{id:\\d+}"+UrlHolder.TRIPS_PASSENGERS+"/{userId:\\d+}")
                    .access("@authValidator.checkIfUserIsTripCreator(#id)")
                //Cancel trip as passenger
                .antMatchers(HttpMethod.DELETE, UrlHolder.TRIPS_BASE+"/{id:\\d+}"+UrlHolder.TRIPS_PASSENGERS+"/{userId:\\d+}")
                    .access("@authValidator.checkIfWantedIsSelf(#userId)")
                //--------Reports--------
                //Accept or reject report
                .antMatchers(HttpMethod.PATCH,UrlHolder.REPORT_BASE+"/{id:\\d+}")
                    .hasRole(UserRole.ADMIN.getText())
                .antMatchers(UrlHolder.REPORT_BASE+"/**")
                    .authenticated()
                //--------Passenger reviews--------
                .antMatchers(HttpMethod.POST,UrlHolder.PASSENGER_REVIEWS_BASE)
                    .authenticated()
                //--------Driver reviews--------
                .antMatchers(HttpMethod.POST,UrlHolder.DRIVER_REVIEWS_BASE)
                    .authenticated()
                //--------Cars--------
                .antMatchers(HttpMethod.POST,UrlHolder.CAR_BASE)
                    .hasRole(UserRole.DRIVER.getText())
                .antMatchers(HttpMethod.POST,UrlHolder.CAR_BASE+"/{id:\\d+}"+UrlHolder.REVIEWS_ENTITY+"/**")
                    .authenticated()
                //--------Cities, Car brands, Car features, Base, others--------
                .antMatchers(UrlHolder.BASE+"/**")
                    .permitAll()
                .and().exceptionHandling()
                    .accessDeniedHandler(forbiddenRequestHandler)
                    .authenticationEntryPoint(unauthorizedRequestHandler)
                .and().authorizeRequests()
                    .antMatchers("/**").permitAll()
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

//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        final CorsConfiguration configuration = new CorsConfiguration();
//
////        final List<String> allowedOrigins = environment.getProperty("CORS_ALLOWED_ORIGINS") != null ?
////                Arrays.asList(environment.getProperty("CORS_ALLOWED_ORIGINS").split(",")) :
////                Collections.emptyList();
//
//        configuration.setAllowedOrigins(Collections.singletonList(CorsConfiguration.ALL));
//        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
////        configuration.setAllowCredentials(true);
//        configuration.addAllowedHeader(CorsConfiguration.ALL);
////        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
//        configuration.setExposedHeaders(Arrays.asList(BasicAuthFilter.JWT_HEADER, BasicAuthFilter.JWT_REFRESH_HEADER, BasicAuthFilter.VERIFICATION_HEADER,"Accept","Accept-Language", "X-Total-Pages", "Link", "Location","Cache-Control","Vary","Content-type"));
//
//        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//
//        return source;
//    }
}
