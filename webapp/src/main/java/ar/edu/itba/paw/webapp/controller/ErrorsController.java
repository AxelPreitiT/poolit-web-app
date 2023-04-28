package ar.edu.itba.paw.webapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
public class ErrorsController {
    private final Map<Integer,ErrorMessage> errorMessages;
    public ErrorsController(){
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

    @RequestMapping(value = "/errors", method = RequestMethod.GET)
    public ModelAndView runtimeError(HttpServletRequest servletRequest){
        ModelAndView mav = new ModelAndView("/errors/default");
        Integer httpErrorCode = (Integer) servletRequest.getAttribute("javax.servlet.error.status_code");
        mav.addObject("errorCode",httpErrorCode);
        ErrorMessage message = errorMessages.getOrDefault(httpErrorCode,new ErrorMessage("errors.default","errors.default.description"));
        mav.addObject("errorMessage",message.getError());
        mav.addObject("errorDescription",message.getDescription());
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
