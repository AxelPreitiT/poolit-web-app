package ar.edu.itba.paw.webapp.controller.utils;

public class UrlHolder {

    //No hago un enum porque quiero tenerlo como variables estáticas, y no puedo acceder a las variables en el constructor del enum
    private static final String BASE = "/api";
    public static final String CITY_BASE = BASE + "/cities";
    public static final String USER_BASE = BASE + "/users";
    public static final String CAR_BASE = BASE + "/cars";
    public static final String TRIPS_BASE = BASE + "/trips";

}
