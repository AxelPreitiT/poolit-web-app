package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
public class ErrorsController extends LoggedUserController {

    private final MessageSource messageSource;
    private final Map<Integer,ErrorMessage> errorMessages;
    @Autowired
    public ErrorsController(final UserService userService, final MessageSource messageSource){
        super(userService);
        this.messageSource = messageSource;
        this.errorMessages = new HashMap<>();
        errorMessages.put(404, new ErrorMessage("errors.404","errors.404.description"));
        errorMessages.put(500, new ErrorMessage("errors.500","errors.500.description"));
        errorMessages.put(403, new ErrorMessage("errors.403","errors.403.description"));
    }

    @RequestMapping("/static/403")
    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    public ModelAndView error403(){
        return new ModelAndView("static/403");
    }

    //TODO: ver por que se llama todo el tiempo y preguntarlo
    @RequestMapping(value = "/errors", method = RequestMethod.GET)
    public ModelAndView runtimeError(HttpServletRequest servletRequest){
        ModelAndView mav = new ModelAndView("/errors/default");
        Integer httpErrorCode = (Integer) servletRequest.getAttribute("javax.servlet.error.status_code");
        mav.addObject("errorCode",httpErrorCode);
        ErrorMessage message = errorMessages.getOrDefault(httpErrorCode,new ErrorMessage("errors.default","errors.default.description"));
        mav.addObject("errorMessage",messageSource.getMessage(message.getError(),null, LocaleContextHolder.getLocale()));
        mav.addObject("errorDescription",messageSource.getMessage(message.getDescription(),null,LocaleContextHolder.getLocale()));
        return mav;
    }

    private static class ErrorMessage{
        private final String error,description;
        public ErrorMessage(final String error, final String description){
            this.error = error;
            this.description = description;
        }

        public String getError() {
            return error;
        }

        public String getDescription() {
            return description;
        }
    }
}
