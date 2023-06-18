package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.interfaces.exceptions.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ModelAttribute;

public class LoggedUserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggedUserController.class);

    private final UserService userService;

    public LoggedUserController(UserService userService) {
        this.userService = userService;
    }

    //TODO: revisar
    @ModelAttribute("loggedUser")
    public User getLoggedUser() throws UserNotFoundException {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // Checks if the user is logged in
        if(auth == null || auth instanceof AnonymousAuthenticationToken) {
            return null;
        }
        final org.springframework.security.core.userdetails.User authUser = (org.springframework.security.core.userdetails.User) auth.getPrincipal();
        return userService.findByEmail(authUser.getUsername()).orElseThrow(UserNotFoundException::new);
    }

}
