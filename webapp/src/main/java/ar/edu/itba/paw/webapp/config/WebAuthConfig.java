package ar.edu.itba.paw.webapp.config;

import ar.edu.itba.paw.webapp.auth.AuthValidator;
import ar.edu.itba.paw.webapp.auth.PawUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.FileCopyUtils;

import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

@EnableWebSecurity
@ComponentScan({"ar.edu.itba.paw.webapp.auth"})
@Configuration
public class WebAuthConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }


    @Value("classpath:rememberMeKey.pem")
    private Resource rememberMeKey;
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.sessionManagement()
                    .invalidSessionUrl("/users/login")
                .and().authorizeRequests()
                    .antMatchers("/users/login", "/users/create").anonymous()
                    .antMatchers("/trips/{id:\\d+$}/delete").access("@authValidator.checkIfUserIsTripCreator(request)")
                    .antMatchers("/changeRole").hasRole("USER")
                    .antMatchers("/trips/create", "/cars/**", "/users/created").hasRole("DRIVER")
                    .antMatchers("/trips/created/**").hasRole("DRIVER")
                    .antMatchers("/trips/reserved/**").authenticated()
                    .antMatchers("/trips", "/trips/", "/trips/{id:\\d+$}").permitAll()
                    .antMatchers(  "/users/**", "/trips/{id:\\d+$}/join", "/trips/{id:\\d+$}/cancel", "/trips/{id:\\d+$}/review").authenticated()
                    .antMatchers("/**").permitAll()
                .and().formLogin()
                    .loginPage("/users/login")
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .defaultSuccessUrl("/", false)
                .and().rememberMe()
                    .rememberMeParameter("rememberme")
                    .userDetailsService(userDetailsService)
                    .key(FileCopyUtils.copyToString(new InputStreamReader(rememberMeKey.getInputStream())))
                    .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(300))
                .and().logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/users/login")
                .and().exceptionHandling()
                    .accessDeniedPage("/static/403")
                .and().csrf().disable();
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
