package ar.edu.itba.paw.webapp.controller.mediaType;

import javax.el.MethodNotFoundException;

public class VndType {

    public VndType(){
        throw new MethodNotFoundException();
    }

    private static final String BASE = "application/vnd.";

    //    --------Users--------
    public static final String APPLICATION_USER_PUBLIC = BASE + "user.public.v1+json";
    public static final String APPLICATION_USER_PRIVATE = BASE + "user.private.v1+json";
    public static final String APPLICATION_USER = BASE + "user.v1+json";
    public static final String APPLICATION_USER_ROLE = BASE + "user.role.v1+json";
    //    --------Cities--------
    public static final String APPLICATION_CITY = BASE + "city.v1+json";
    //    --------Cars--------
    public static final String APPLICATION_CAR = BASE + "car.v1+json";
    public static final String APPLICATION_CAR_REVIEW = BASE + "car.review.v1+json";
    //    --------Reviews--------
    public static final String APPLICATION_REVIEW_DRIVER = BASE + "driver-review.v1+json";
    public static final String APPLICATION_REVIEW_PASSENGER = BASE + "passenger-review.v1+json";
    //    --------Trips--------
    public static final String APPLICATION_TRIP = BASE + "trip.v1+json";
    public static final String APPLICATION_TRIP_PASSENGER = BASE + "trip.passenger.v1+json";
    public static final String APPLICATION_TRIP_PASSENGER_STATE = BASE + "trip.passenger.state.v1+json";

}

