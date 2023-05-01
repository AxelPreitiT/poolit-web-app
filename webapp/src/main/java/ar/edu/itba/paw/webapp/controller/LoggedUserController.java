package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.auth.AuthUser;
import ar.edu.itba.paw.webapp.exceptions.UserNotFoundException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ModelAttribute;

public class LoggedUserController {

    private final UserService userService;

    public LoggedUserController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("loggedUser")
    public User getLoggedUser() {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // Checks if the user is logged in
        if(auth == null || auth instanceof AnonymousAuthenticationToken) {
            return null;
        }
        final AuthUser authUser = (AuthUser) auth.getPrincipal();
        return userService.findByEmail(authUser.getUsername()).orElseThrow(UserNotFoundException::new);
    }

}
