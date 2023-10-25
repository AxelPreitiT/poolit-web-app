package ar.edu.itba.paw.webapp.controller.mediaType;

import javax.el.MethodNotFoundException;

public class VndType {

    public VndType(){
        throw new MethodNotFoundException();
    }

    private static final String BASE = "application/vnd.";

    public static final String APPLICATION_USER_PUBLIC = BASE + "user.public.v1+json";

    public static final String APPLICATION_USER_PASSENGER = BASE + "user.passenger.v1+json";

    public static final String APPLICATION_USER_PRIVATE = BASE + "user.private.v1+json";


}

