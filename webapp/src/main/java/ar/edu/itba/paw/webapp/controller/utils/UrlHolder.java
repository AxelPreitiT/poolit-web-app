package ar.edu.itba.paw.webapp.controller.utils;

public class UrlHolder {

    //No hago un enum porque quiero tenerlo como variables estáticas, y no puedo acceder a las variables en el constructor del enum
    public static final String BASE = "/api";

    //    --------Cities--------
    public static final String CITY_BASE = BASE + "/cities";
    //    --------Users--------
    public static final String USER_BASE = BASE + "/users";
    //    --------Cars--------
    public static final String CAR_BASE = BASE + "/cars";
    //    --------Car brands--------
    public static final String CAR_BRAND_BASE = BASE + "/car-brands";
    //    --------Car features--------
    public static final String CAR_FEATURE_BASE = BASE + "/car-features";
    //    --------Trips--------
    public static final String TRIPS_BASE = BASE + "/trips";
    public static final String TRIPS_PASSENGERS = "/passengers";

    //    --------Reviews--------
    public static final String DRIVER_REVIEWS_BASE = BASE + "/driver-reviews";

    public static final String PASSENGER_REVIEWS_BASE = BASE + "/passenger-reviews";

    //    --------Reports--------

    public static final String REPORT_BASE = BASE + "/reports";

    //    --------SortType--------
    public static final String TRIP_SORT_TYPE_BASE = BASE + "/trip-sort-types";

    //    --------Others--------
    public static final String IMAGE_ENTITY = "/image";

    public static final String REVIEWS_ENTITY = "/reviews";

}
