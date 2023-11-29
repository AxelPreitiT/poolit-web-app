package ar.edu.itba.paw.webapp.controller.utils;

import javax.ws.rs.core.Response;
import java.util.function.Function;
import java.util.function.Supplier;

public class ControllerUtils {

    public static <T> Supplier<T> notFoundExceptionOf(Function<Integer,T> constructor){
        return () -> constructor.apply(Response.Status.NOT_FOUND.getStatusCode());
    }
}
