package ar.edu.itba.paw.webapp.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

/*
@Controller
public class ErrorsController {

    private final static Logger LOGGER = LoggerFactory.getLogger(ErrorsController.class);

    private final MessageSource messageSource;
    private final Map<Integer,ErrorMessage> errorMessages;
    @Autowired
    public ErrorsController(final MessageSource messageSource){
        this.messageSource = messageSource;
        this.errorMessages = new HashMap<>();
        errorMessages.put(404, new ErrorMessage("errors.404","errors.404.description"));
        errorMessages.put(500, new ErrorMessage("errors.500","errors.500.description"));
        errorMessages.put(403, new ErrorMessage("errors.403","errors.403.description"));
        errorMessages.put(405, new ErrorMessage("errors.405","errors.405.description"));
    }

    @RequestMapping("/static/403")
    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    public ModelAndView error403(){
        LOGGER.debug("GET Request to /static/403");
        return new ModelAndView("static/403");
    }

    @RequestMapping(value = "/errors", method = RequestMethod.GET)
    public ModelAndView runtimeError(HttpServletRequest servletRequest){
        LOGGER.debug("GET Request to /errors");
        final ModelAndView mav = new ModelAndView("/errors/default");
        final Integer httpErrorCode = (Integer) servletRequest.getAttribute("javax.servlet.error.status_code");
        mav.addObject("errorCode",httpErrorCode);
        LOGGER.warn("Raised error with code {} in controller", httpErrorCode);
        final ErrorMessage message = errorMessages.getOrDefault(httpErrorCode,new ErrorMessage("errors.default","errors.default.description"));
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

 */
