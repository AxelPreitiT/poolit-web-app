package ar.edu.itba.paw.webapp.controller.mediaType;

import javax.el.MethodNotFoundException;

public class VndType {

    public VndType(){
        throw new MethodNotFoundException();
    }

    private static final String BASE = "application/vnd.";

    //    --------Users--------
    public static final String APPLICATION_USER_PUBLIC = BASE + "user.public.v1+json";
    public static final String APPLICATION_USER_DRIVER = BASE + "user.driver.v1+json";
    public static final String APPLICATION_USER_PRIVATE = BASE + "user.private.v1+json";
    public static final String APPLICATION_USER = BASE + "user.v1+json";
    //    --------Cities--------
    public static final String APPLICATION_CITY_LIST = BASE + "city.list.v1+json";
    public static final String APPLICATION_CITY = BASE + "city.v1+json";
    //    --------Cars--------
    public static final String APPLICATION_CAR_LIST = BASE + "car.list.v1+json";
    public static final String APPLICATION_CAR = BASE + "car.v1+json";
    public static final String APPLICATION_CAR_REVIEW_LIST = BASE + "car.review.list.v1+json";
    public static final String APPLICATION_CAR_REVIEW = BASE + "car.review.v1+json";
    //    --------Car brands--------
    public static final String APPLICATION_CAR_BRAND_LIST = BASE + "car-brand.list.v1+json";
    public static final String APPLICATION_CAR_BRAND = BASE + "car-brand.v1+json";

    //    --------Car features--------
    public static final String APPLICATION_CAR_FEATURE_LIST = BASE + "car-feature.list.v1+json";
    public static final String APPLICATION_CAR_FEATURE = BASE + "car-feature.v1+json";
    //    --------Reviews--------
    public static final String APPLICATION_REVIEW_DRIVER_LIST = BASE + "driver-review.list.v1+json";
    public static final String APPLICATION_REVIEW_DRIVER = BASE + "driver-review.v1+json";
    public static final String APPLICATION_REVIEW_PASSENGER_LIST = BASE + "passenger-review.list.v1+json";
    public static final String APPLICATION_REVIEW_PASSENGER = BASE + "passenger-review.v1+json";
    //    --------Trips--------
    public static final String APPLICATION_TRIP_LIST = BASE + "trip.list.v1+json";
    public static final String APPLICATION_TRIP = BASE + "trip.v1+json";
    public static final String APPLICATION_TRIP_EARNINGS = BASE + "trip.earnings.v1+json";
    public static final String APPLICATION_TRIP_PASSENGER_LIST = BASE + "trip.passenger.list.v1+json";
    public static final String APPLICATION_TRIP_PASSENGER = BASE + "trip.passenger.v1+json";
    public static final String APPLICATION_TRIP_PASSENGER_SEAT_COUNT = BASE + "trip.passenger.seat-count.v1+json";
    public static final String APPLICATION_TRIP_PASSENGER_STATE = BASE + "trip.passenger.state.v1+json";
    //    --------Reports--------
    public static final String APPLICATION_REPORT = BASE + "report.v1+json";
    public static final String APPLICATION_REPORT_PUBLIC_LIST = BASE + "report.public.list.v1+json";
    public static final String APPLICATION_REPORT_PUBLIC = BASE + "report.public.v1+json";
    public static final String APPLICATION_REPORT_PRIVATE_LIST = BASE + "report.private.list.v1+json";
    public static final String APPLICATION_REPORT_PRIVATE = BASE + "report.private.v1+json";
    public static final String APPLICATION_REPORT_DECISION = BASE + "report.decision.v1+json";

    //    --------SortType--------
    public static final String APPLICATION_TRIP_SORT_TYPE_LIST = BASE + "trip-sort-type.list.v1+json";
    public static final String APPLICATION_TRIP_SORT_TYPE = BASE + "trip-sort-type.v1+json";

}

