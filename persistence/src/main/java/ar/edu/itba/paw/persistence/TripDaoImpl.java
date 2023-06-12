package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.CarDao;
import ar.edu.itba.paw.interfaces.persistence.CityDao;
import ar.edu.itba.paw.interfaces.persistence.TripDao;
import ar.edu.itba.paw.interfaces.persistence.UserDao;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.trips.Trip;
import ar.edu.itba.paw.models.trips.TripInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

/*
//@Repository
public class TripDaoImpl implements TripDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(TripDaoImpl.class);

    private static final RowMapper<Integer> COUNT_ROW_MAPPER = (resultSet,rowNum)-> resultSet.getInt("count");

    private final RowMapper<Trip> ROW_MAPPER = (resultSet,rowNum)-> {
        User user = new User(resultSet.getLong("user_id"),resultSet.getString("user_username"),
                resultSet.getString("user_surname"),resultSet.getString("user_email"),
                resultSet.getString("user_phone"),resultSet.getString("user_password"),
                new City(resultSet.getLong("user_city_id"),resultSet.getString("user_city_name"), resultSet.getLong("user_city_province_id")),
                new Locale(resultSet.getString("user_mail_locale")),
                resultSet.getString("user_role"),resultSet.getLong("user_image_id"));
        return new Trip(
                resultSet.getLong("trip_id"),
                new City(resultSet.getLong("origin_city_id"),resultSet.getString("origin_city_name"),resultSet.getLong("origin_province_id")),
                resultSet.getString("origin_address"),
                new City(resultSet.getLong("destination_city_id"),resultSet.getString("destination_city_name"),resultSet.getLong("destination_province_id")),
                resultSet.getString("destination_address"),
                resultSet.getTimestamp("start_date_time").toLocalDateTime(),
                resultSet.getTimestamp("end_date_time").toLocalDateTime(),
                resultSet.getInt("max_passengers"),
                user,
                new Car(resultSet.getLong("car_id"),resultSet.getString("car_plate"),resultSet.getString("car_info_car"),user,resultSet.getLong("car_image_id")),
                resultSet.getInt("occupied_seats"),
                resultSet.getDouble("trip_price")
        );
    };
    private final RowMapper<Trip> PASSENGER_TRIPS_ROW_MAPPER = (resultSet,rowNum)-> {
        User user = new User(resultSet.getLong("user_id"),resultSet.getString("user_username"),
                resultSet.getString("user_surname"),resultSet.getString("user_email"),
                resultSet.getString("user_phone"),resultSet.getString("user_password"),
                new City(resultSet.getLong("user_city_id"),resultSet.getString("user_city_name"), resultSet.getLong("user_city_province_id")),
                new Locale(resultSet.getString("user_mail_locale")),
                resultSet.getString("user_role"),resultSet.getLong("user_image_id"));
        return new Trip(
                resultSet.getLong("trip_id"),
                new City(resultSet.getLong("origin_city_id"),resultSet.getString("origin_city_name"),resultSet.getLong("origin_province_id")),
                resultSet.getString("origin_address"),
                new City(resultSet.getLong("destination_city_id"),resultSet.getString("destination_city_name"),resultSet.getLong("destination_province_id")),
                resultSet.getString("destination_address"),
                resultSet.getTimestamp("start_date_time").toLocalDateTime(),
                resultSet.getTimestamp("end_date_time").toLocalDateTime(),
                resultSet.getInt("max_passengers"),
                user,
                new Car(resultSet.getLong("car_id"),resultSet.getString("car_plate"),resultSet.getString("car_info_car"),user,resultSet.getLong("car_image_id")),
                resultSet.getInt("occupied_seats"),
                resultSet.getDouble("trip_price"),
                resultSet.getTimestamp("passenger_start_date").toLocalDateTime(),
                resultSet.getTimestamp("passenger_end_date").toLocalDateTime()
        );
    };

    private final RowMapper<Passenger> PASSENGER_ROW_MAPPER = (resultSet, rowNum) ->{
      User user = UserDaoImpl.ROW_MAPPER.mapRow(resultSet,rowNum);
      return new Passenger(user,
              resultSet.getTimestamp("start_date").toLocalDateTime(),
              resultSet.getTimestamp("end_date").toLocalDateTime());
    };
    private static final LocalTime MIN_TIME = LocalTime.of(0,0);
    private static final LocalTime MAX_TIME = LocalTime.of(23,59);
    private final JdbcTemplate jdbcTemplate;

    private final SimpleJdbcInsert tripsInsert;
    private final SimpleJdbcInsert driverCarInsert;
    private final SimpleJdbcInsert passengerInsert;
    @Autowired
    public TripDaoImpl(final DataSource tripDataSource, CityDao cityDao, UserDao userDao, CarDao carDao){
        this.jdbcTemplate = new JdbcTemplate(tripDataSource);
        this.tripsInsert = new SimpleJdbcInsert(tripDataSource)
                .usingGeneratedKeyColumns("trip_id")
                .withTableName("trips");
        this.driverCarInsert = new SimpleJdbcInsert(tripDataSource)
                .withTableName("trips_cars_drivers");
        this.passengerInsert = new SimpleJdbcInsert(tripDataSource)
                .withTableName("passengers");
    }
    private RowMapper<TripInstance> getTripInstanceRowMapper(final Trip trip) {
        return (resultSet, rowNum) ->
            new TripInstance(
                    resultSet.getTimestamp("trip_date_time").toLocalDateTime(),
                    trip,
                    resultSet.getInt("trip_passenger_count")
            );
    }
    private RowMapper<Trip> getTripRowMapper(final Optional<LocalDateTime> queryStartDateTime, final Optional<LocalDateTime> queryEndDateTime){
        return (resultSet, rowNum)->{
            User user = new User(resultSet.getLong("user_id"),resultSet.getString("user_username"),
                    resultSet.getString("user_surname"),resultSet.getString("user_email"),
                    resultSet.getString("user_phone"),resultSet.getString("user_password"),
                    new City(resultSet.getLong("user_city_id"),resultSet.getString("user_city_name"), resultSet.getLong("user_city_province_id")),
                    new Locale(resultSet.getString("user_mail_locale")),
                    resultSet.getString("user_role"),resultSet.getLong("user_image_id"));
            LocalDateTime startDateTime = resultSet.getTimestamp("start_date_time").toLocalDateTime();
            LocalDateTime endDateTime = resultSet.getTimestamp("end_date_time").toLocalDateTime();
            return new Trip(
                    resultSet.getLong("trip_id"),
                    new City(resultSet.getLong("origin_city_id"),resultSet.getString("origin_city_name"),resultSet.getLong("origin_province_id")),
                    resultSet.getString("origin_address"),
                    new City(resultSet.getLong("destination_city_id"),resultSet.getString("destination_city_name"),resultSet.getLong("destination_province_id")),
                    resultSet.getString("destination_address"),
                    resultSet.getTimestamp("start_date_time").toLocalDateTime(),
                    resultSet.getTimestamp("end_date_time").toLocalDateTime(),
                    resultSet.getInt("max_passengers"),
                    user,
                    new Car(resultSet.getLong("car_id"),resultSet.getString("car_plate"),resultSet.getString("car_info_car"),user,resultSet.getLong("car_image_id")),
                    resultSet.getInt("occupied_seats"),
                    resultSet.getDouble("trip_price"),
                    queryStartDateTime.orElse(startDateTime).toLocalDate().atTime(startDateTime.toLocalTime()),
                    queryEndDateTime.orElse(endDateTime).toLocalDate().atTime(endDateTime.toLocalTime())
            );
        };
    }

    @Override
    public Trip create(final City originCity, final String originAddress, final City destinationCity, final String destinationAddress, final Car car, final LocalDateTime startDateTime, final LocalDateTime endDateTime, final boolean isRecurrent,final double price, final int max_passengers, final User driver) {
        //Insertamos los datos en la tabla trip
        Map<String, Object> tripData = new HashMap<>();
        tripData.put("max_passengers",max_passengers);
        tripData.put("origin_address",originAddress);
        tripData.put("destination_address",destinationAddress);
        tripData.put("price",price);
        tripData.put("start_date_time", Timestamp.valueOf(startDateTime));
        tripData.put("end_date_time",Timestamp.valueOf(endDateTime));
        tripData.put("day_of_week",startDateTime.getDayOfWeek().getValue());
        tripData.put("destination_city_id",destinationCity.getId());
        tripData.put("origin_city_id",originCity.getId());
        LOGGER.debug("Adding new trip from '{}' to '{}', with startDate '{}' and endDate '{}', and created by driver with id {} to the database",originCity,destinationCity,startDateTime,endDateTime,driver.getUserId());
        Number tripKey = tripsInsert.executeAndReturnKey(tripData);
        LOGGER.info("Trip added to the database with id {}",tripKey);
        final Trip trip = new Trip(tripKey.longValue(),originCity,originAddress,destinationCity,destinationAddress,startDateTime,endDateTime,max_passengers,driver,car,0,price,startDateTime,endDateTime);
        LOGGER.debug("New {}", trip);
        //Insertamos el auto y usuario en la tabla trips_cars_drivers
        Map<String,Object> driverCarData = new HashMap<>();
        driverCarData.put("trip_id",tripKey.longValue());
        driverCarData.put("user_id",driver.getUserId());
        driverCarData.put("car_id",car.getCarId());
        LOGGER.debug("Adding new trip_car_driver relation with tripId {}, driverId {} and carId {} to the database",tripKey.longValue(),driver.getUserId(),car.getCarId());
        driverCarInsert.execute(driverCarData);
        LOGGER.info("Trip_car_driver relation with tripId {}, driverId {} and carId {} added to the database",tripKey.longValue(),driver.getUserId(),car.getCarId());
        return trip;
    }

    @Override
    public boolean addPassenger(Trip trip,User user,LocalDateTime startDateTime,LocalDateTime endDateTime){
        Map<String,Object> passengerData = new HashMap<>();
        passengerData.put("user_id",user.getUserId());
        passengerData.put("trip_id",trip.getTripId());
        passengerData.put("start_date",Timestamp.valueOf(startDateTime));
        passengerData.put("end_date",Timestamp.valueOf(endDateTime));
        LOGGER.debug("Adding new passenger with id {} to the trip with id {} in the database",user.getUserId(),trip.getTripId());
        final boolean result = passengerInsert.execute(passengerData)>0;
        if(result) {
            LOGGER.info("Passenger with id {} added to the trip with id {} in the database",user.getUserId(),trip.getTripId());
        } else {
            LOGGER.warn("Passenger with id {} could not be added to the trip with id {} in the database",user.getUserId(),trip.getTripId());
        }
        return result;
    }
    @Override
    public boolean deleteTrip(final Trip trip){
        int tripCarsDriversRows = jdbcTemplate.update("DELETE FROM trips_cars_drivers WHERE trip_id = ?",trip.getTripId());
        jdbcTemplate.update("DELETE FROM passengers WHERE trip_id = ?",trip.getTripId());
        int tripRows = jdbcTemplate.update("DELETE FROM trips WHERE trip_id = ?",trip.getTripId());
        LOGGER.debug("Deleting trip with id {} from the database",trip.getTripId());
        final boolean result = tripRows>0 && tripCarsDriversRows>0;
        if(result) {
            LOGGER.info("Trip with id {} deleted from the database",trip.getTripId());
        } else {
            LOGGER.warn("Trip with id {} could not be deleted from the database",trip.getTripId());
        }
        return result;
    }

    @Override
    public boolean markTripAsDeleted(Trip trip) {
        return false;
    }

    @Override
    public void truncatePassengerEndDateTime(Passenger passenger, LocalDateTime newLastDateTime) {

    }

    @Override
    public boolean removePassenger(final Trip trip, final Passenger passenger){
        LOGGER.debug("Removing passenger with id {} from the trip with id {} in the database",passenger.getUserId(),trip.getTripId());
        final boolean result = jdbcTemplate.update("DELETE FROM passengers WHERE trip_id = ? AND user_id = ?",trip.getTripId(), passenger.getUserId())>0;
        if(result) {
            LOGGER.info("Passenger with id {} removed from the trip with id {} in the database",passenger.getUserId(),trip.getTripId());
        } else {
            LOGGER.warn("Passenger with id {} could not be removed from the trip with id {} in the database",passenger.getUserId(),trip.getTripId());
        }
        return result;
    }
    @Override
    public List<Passenger> getPassengers(final TripInstance tripInstance){
        return getPassengers(tripInstance.getTrip(),tripInstance.getDateTime());
    }
    @Override
    public List<Passenger> getPassengers(final Trip trip, final LocalDateTime dateTime){
        return getPassengers(trip,dateTime,dateTime);
    }
    @Override
    public List<Passenger> getPassengers(final Trip trip, final LocalDateTime startDateTime, final LocalDateTime endDateTime){
        LOGGER.debug("Looking for the passengers of the trip with id {}, between '{}' and '{}', in the database",trip.getTripId(),startDateTime,endDateTime);
        final List<Passenger> result = jdbcTemplate.query("SELECT * FROM passengers NATURAL JOIN users NATURAL JOIN cities " +
                        "WHERE trip_id = ? AND ((passengers.start_date<=? AND passengers.end_date>=?) OR (passengers.start_date<=? AND passengers.end_date>=?)) "
                ,PASSENGER_ROW_MAPPER,trip.getTripId(),Timestamp.valueOf(startDateTime),Timestamp.valueOf(startDateTime),Timestamp.valueOf(endDateTime),Timestamp.valueOf(endDateTime));
        LOGGER.debug("Found {} in the database", result);
        return result;
    }
    @Override
    public Optional<Passenger> getPassenger(final Trip trip, final User user){
        return getPassenger(trip.getTripId(),user);
    }
    @Override
    public Optional<Passenger> getPassenger(final long tripId, final User user){
        LOGGER.debug("Looking for the passenger with id {} of the trip with id {} in the database",user.getUserId(),tripId);
        final Optional<Passenger> result = jdbcTemplate.query("SELECT * FROM passengers NATURAL JOIN users NATURAL JOIN cities " +
                        "WHERE trip_id = ? AND user_id = ?"
                ,PASSENGER_ROW_MAPPER,tripId,user.getUserId()).stream().findFirst();
        LOGGER.debug("Found {} in the database", result.isPresent() ? result.get() : "nothing");
        return result;
    }
    @Override
    public PagedContent<TripInstance> getTripInstances(final Trip trip,int page, int pageSize){
        return getTripInstances(trip,page,pageSize,trip.getStartDateTime(),trip.getEndDateTime());
    }
    @Override
    public PagedContent<TripInstance> getTripInstances(final Trip trip, int page, int pageSize, LocalDateTime start, LocalDateTime end){
        LOGGER.debug("Looking for the trip instances of the trip with id {}, between '{}' and '{}', in page {} with size {} in the database",trip.getTripId(),start,end,page,pageSize);
        String query = "FROM generate_series(?,?, '7 day'::interval) days LEFT OUTER JOIN passengers " +
                "ON passengers.start_date<=days.days AND passengers.end_date>=days.days AND passengers.trip_id=? " +
                "WHERE days.days>=? AND days.days<=? " +
                "GROUP BY days.days ";
        List<TripInstance> ans =  jdbcTemplate.query("SELECT days as trip_date_time, count(passengers.user_id) as  trip_passenger_count " +
                query +
                "OFFSET ? " +
                "LIMIT ?",getTripInstanceRowMapper(trip),trip.getStartDateTime(),trip.getEndDateTime(),trip.getTripId(),start,end,page*pageSize,pageSize);
        int total = jdbcTemplate.query("SELECT sum(aux) as count FROM ( SELECT count(*) as aux " + query +") t",COUNT_ROW_MAPPER,trip.getStartDateTime(),trip.getEndDateTime(),trip.getTripId(),start,end).stream().findFirst().orElse(0);
        final PagedContent<TripInstance> result = new PagedContent<>(ans,page,pageSize,total);
        LOGGER.debug("Found {} in the database", result);
        return result;
    }
    @Override
    public PagedContent<Trip> getTripsCreatedByUser(final User user,Optional<LocalDateTime> minDateTime, Optional<LocalDateTime> maxDateTime,int page, int pageSize){
        LOGGER.debug("Looking for the trips created by the user with id {}, between '{}' and '{}', in page {} with size {} in the database",user.getUserId(),minDateTime,maxDateTime,page,pageSize);
        QueryBuilder queryBuilder = new QueryBuilder()
                .withWhere(QueryBuilder.DbField.USER_ID, QueryBuilder.DbComparator.EQUALS,user.getUserId());
        minDateTime.ifPresent(dateTime->
                queryBuilder.withWhere(QueryBuilder.DbField.END_DATE_TIME, QueryBuilder.DbComparator.GREATER_OR_EQUALS,dateTime)
        );
        maxDateTime.ifPresent(dateTime->
                queryBuilder.withWhere(QueryBuilder.DbField.END_DATE_TIME, QueryBuilder.DbComparator.LESS_OR_EQUAL,dateTime)
        );
        int total = jdbcTemplate.query(queryBuilder.getCountString(),COUNT_ROW_MAPPER,queryBuilder.getArguments().toArray()).stream().findFirst().orElse(0);
        queryBuilder.withOffset(page*pageSize)
                    .withLimit(pageSize);
        List<Trip> ans = jdbcTemplate.query(queryBuilder.getString(),ROW_MAPPER,queryBuilder.getArguments().toArray());
        final PagedContent<Trip> result = new PagedContent<>(ans,page,pageSize,total);
        LOGGER.debug("Found {} in the database", result);
        return result;
    }

    @Override
    public PagedContent<Trip> getTripsWhereUserIsPassenger(final User user,Optional<LocalDateTime> minDateTime, Optional<LocalDateTime> maxDateTime, int page, int pageSize) {
        LOGGER.debug("Looking for the trips where the user with id {} is passenger, between '{}' and '{}', in page {} with size {} in the database",user.getUserId(),minDateTime,maxDateTime,page,pageSize);
        String totalQuery = "SELECT count(*)"+
                "FROM passengers NATURAL JOIN trips "+
                "WHERE passengers.user_id = ?";
        int total = jdbcTemplate.query(totalQuery, COUNT_ROW_MAPPER, user.getUserId()).stream().findFirst().orElse(0);
        List<Object> arguments = new ArrayList<>();
        String q = "SELECT trips.trip_id, trips.max_passengers, trips.start_date_time,trips.end_date_time, trips.origin_address, origin.name as origin_city_name, origin.city_id as origin_city_id, origin.province_id as origin_province_id, trips.destination_address, destination.name as destination_city_name, destination.city_id as destination_city_id, destination.province_id as destination_province_id, users.email as user_email, users.user_id as user_id, users.phone as user_phone, users.user_role as user_role, users.password as user_password, users.username as user_username, users.surname as user_surname, users.user_image_id as user_image_id, user_city.city_id as user_city_id, user_city.name as user_city_name, user_city.province_id as user_city_province_id, users.mail_locale as user_mail_locale, cars.car_id as car_id, cars.plate  as car_plate, cars.info_car as car_info_car, cars.image_id as car_image_id, COALESCE(max(aux.count),0) as occupied_seats, trips.price as trip_price, p.start_date as passenger_start_date, p.end_date as passenger_end_date "+
                    "FROM trips trips NATURAL JOIN passengers p LEFT OUTER JOIN LATERAL( "+
                    "SELECT trips.trip_id as trip_id,days.days, count(passengers.user_id) as count "+
                    "FROM generate_series(p.start_date,p.end_date,'7 day'::interval) days LEFT OUTER JOIN passengers ON passengers.trip_id = trips.trip_id AND passengers.start_date<=days.days AND passengers.end_date>=days.days "+
                    "GROUP BY days.days,passengers.trip_id "+
                    ")aux ON aux.trip_id = trips.trip_id JOIN trips_cars_drivers drivers on drivers.trip_id = trips.trip_id JOIN users ON drivers.user_id = users.user_id JOIN cars ON cars.car_id = drivers.car_id JOIN cities origin ON trips.origin_city_id = origin.city_id JOIN cities destination ON trips.destination_city_id=destination.city_id JOIN cities user_city ON users.city_id = user_city.city_id "+
                    "WHERE p.user_id = ? ";
        arguments.add(user.getUserId());
        if(minDateTime.isPresent()){
            q += " AND p.end_date >= ? ";
            arguments.add(minDateTime.get());
        }
        if(maxDateTime.isPresent()){
            q += " AND p.end_date <= ? ";
            arguments.add(maxDateTime.get());
        }
        q += "GROUP BY trips.trip_id,trips.start_date_time, trips.end_date_time, trips.max_passengers, trips.origin_address, origin.name, origin.city_id, origin.province_id, destination_address, destination.name, destination.city_id, destination.province_id, users.email, users.user_id, users.phone, users.user_role, users.password, users.username, users.surname, user_city.city_id, user_city.name, user_city.province_id, cars.car_id, cars.plate, cars.info_car, cars.image_id, p.start_date, p.end_date "+
                    "OFFSET ? "+
                    "LIMIT ?";
        arguments.add(page * pageSize);
        arguments.add(pageSize);
        List<Trip> ans =  jdbcTemplate.query(q,PASSENGER_TRIPS_ROW_MAPPER,arguments.toArray());
        final PagedContent<Trip> result = new PagedContent<>(ans,page,pageSize,total);
        LOGGER.debug("Found {} in the database", result);
        return result;
    }

    private QueryBuilder.DbField getSortField(final Trip.SortType sortType){
        if(sortType.equals(Trip.SortType.TIME)){
            return QueryBuilder.DbField.TIME;
        }
        return QueryBuilder.DbField.TRIP_PRICE;
    }

    @Override
    public PagedContent<Trip> getTripsByOriginAndStart(long origin_city_id, LocalDateTime startDateTime, int page, int pageSize){
        LOGGER.debug("Looking for the trips with originCity with id {} and startDateTime '{}' in page {} with size {} in the database",origin_city_id,startDateTime,page,pageSize);
        QueryBuilder queryBuilder = new QueryBuilder()
                .withWhere(QueryBuilder.DbField.ORIGIN_CITY_ID, QueryBuilder.DbComparator.EQUALS,origin_city_id)
                .withWhere(QueryBuilder.DbField.END_DATE_TIME, QueryBuilder.DbComparator.GREATER_OR_EQUALS,startDateTime)
                .withWhere(QueryBuilder.DbField.DAY_OF_WEEK, QueryBuilder.DbComparator.EQUALS,startDateTime.getDayOfWeek().getValue())
                .withHaving(QueryBuilder.DbField.OCCUPIED_SEATS, QueryBuilder.DbComparator.LESS, QueryBuilder.DbField.MAX_PASSENGERS)
                .withOrderBy(QueryBuilder.DbField.END_DATE_TIME, QueryBuilder.DbOrder.ASC);
        int total = jdbcTemplate.query(queryBuilder.getCountString(),COUNT_ROW_MAPPER,queryBuilder.getArguments().toArray()).stream().findFirst().orElse(0);
        queryBuilder.withOffset(page*pageSize)
                .withLimit(pageSize);
        List<Trip> ans = jdbcTemplate.query(queryBuilder.getString(),getTripRowMapper(Optional.of(startDateTime),Optional.empty()),queryBuilder.getArguments().toArray());
        final PagedContent<Trip> result = new PagedContent<>(ans,page,pageSize,total);
        LOGGER.debug("Found {} in the database", result);
        return result;
    }
    @Override
    public PagedContent<Trip> getTripsWithFilters(
            long origin_city_id, long destination_city_id,
            LocalDateTime startDateTime, DayOfWeek dayOfWeek, LocalDateTime endDateTime, int minutes,
            Optional<BigDecimal> minPrice, Optional<BigDecimal> maxPrice, Trip.SortType sortType, boolean descending,
            int page, int pageSize){
        return getTripsWithFilters(origin_city_id, destination_city_id, startDateTime,Optional.of(dayOfWeek), Optional.of(endDateTime), minutes,  minPrice, maxPrice, sortType,descending, page, pageSize);
    }
    @Override
    public PagedContent<Trip> getTripsWithFilters(
            long origin_city_id, long destination_city_id,
            LocalDateTime startDateTime, Optional<DayOfWeek> dayOfWeek, Optional<LocalDateTime> endDateTime, int minutes,
            Optional<BigDecimal> minPrice, Optional<BigDecimal> maxPrice, Trip.SortType sortType, boolean descending,
            int page, int pageSize){

        LOGGER.debug("Looking for the trips with originCity with id {}, destinationCity with id {}, startDateTime '{}',{}{} range of {} minutes,{}{} sortType '{}' ({}) page {} and size {} in the database",
                origin_city_id, destination_city_id, startDateTime, dayOfWeek.map(day -> " dayOfWeek '" + day + "',").orElse(""),
                endDateTime.map(endDateTimeValue -> " endDateTime '" + endDateTimeValue + "',").orElse(""), minutes,
                minPrice.map(price -> " minPrice $" + price + ",").orElse(""), maxPrice.map(price -> " maxPrice $" + price + ","),
                sortType, descending ? "descending" : "ascending", page, pageSize);

        LocalTime minTime = startDateTime.toLocalTime();
        if (startDateTime.minusMinutes(minutes).getDayOfWeek().equals(startDateTime.getDayOfWeek())){
            minTime = minTime.minusMinutes(minutes);
        }else{
            minTime = MIN_TIME;
        }
        LocalTime maxTime = startDateTime.toLocalTime();
        if(startDateTime.plusMinutes(minutes).getDayOfWeek().equals(startDateTime.getDayOfWeek())){
            maxTime = maxTime.plusMinutes(minutes);
        }else{
            maxTime = MAX_TIME;
        }
        QueryBuilder queryBuilder = new QueryBuilder()
                //Busco a las ocurrencias de ese viaje que sean desde ahora
                .withWhere(QueryBuilder.DbField.TRIPS_DAYS, QueryBuilder.DbComparator.GREATER_OR_EQUALS,startDateTime.minusMinutes(minutes))
                .withWhere(QueryBuilder.DbField.END_DATE_TIME, QueryBuilder.DbComparator.GREATER_OR_EQUALS,startDateTime.minusMinutes(minutes))
                .withWhere(QueryBuilder.DbField.ORIGIN_CITY_ID, QueryBuilder.DbComparator.EQUALS,origin_city_id)
                .withWhere(QueryBuilder.DbField.TIME, QueryBuilder.DbComparator.GREATER_OR_EQUALS,minTime)
                .withWhere(QueryBuilder.DbField.TIME, QueryBuilder.DbComparator.LESS_OR_EQUAL,maxTime)
                .withWhere(QueryBuilder.DbField.DESTINATION_CITY_ID, QueryBuilder.DbComparator.EQUALS,destination_city_id)
                .withHaving(QueryBuilder.DbField.OCCUPIED_SEATS, QueryBuilder.DbComparator.LESS, QueryBuilder.DbField.MAX_PASSENGERS)
                .withOrderBy(getSortField(sortType), descending? QueryBuilder.DbOrder.DESC : QueryBuilder.DbOrder.ASC);
        if(startDateTime.toLocalDate().equals(LocalDate.now())){
            //Si es en el mismo dia, entonces da solo los viajes que no pasaron (para no traer inconsistencias con los minutos
            queryBuilder.withWhere(QueryBuilder.DbField.TIME, QueryBuilder.DbComparator.GREATER_OR_EQUALS,LocalTime.now());
        }
        endDateTime.ifPresent(localDateTime -> queryBuilder.withWhere(QueryBuilder.DbField.TRIPS_DAYS, QueryBuilder.DbComparator.LESS_OR_EQUAL,localDateTime.plusMinutes(minutes))
                                .withWhere(QueryBuilder.DbField.END_DATE_TIME, QueryBuilder.DbComparator.GREATER_OR_EQUALS,localDateTime.minusMinutes(minutes)));
        dayOfWeek.ifPresent(dayOfWeek1 -> queryBuilder.withWhere(QueryBuilder.DbField.DAY_OF_WEEK, QueryBuilder.DbComparator.EQUALS,dayOfWeek1.getValue()));
        minPrice.ifPresent(price -> queryBuilder.withWhere(QueryBuilder.DbField.TRIP_PRICE, QueryBuilder.DbComparator.GREATER_OR_EQUALS,price.doubleValue()));
        maxPrice.ifPresent(price -> queryBuilder.withWhere(QueryBuilder.DbField.TRIP_PRICE, QueryBuilder.DbComparator.LESS_OR_EQUAL,price.doubleValue()));
        int total = jdbcTemplate.query(queryBuilder.getCountString(),COUNT_ROW_MAPPER,queryBuilder.getArguments().toArray()).stream().findFirst().orElse(0);
        queryBuilder.withOffset(page*pageSize)
                .withLimit(pageSize);
        List<Trip> ans =  jdbcTemplate.query(queryBuilder.getString(),getTripRowMapper(Optional.of(startDateTime),endDateTime),queryBuilder.getArguments().toArray());
        final PagedContent<Trip> result = new PagedContent<>(ans,page,pageSize,total);
        LOGGER.debug("Found {} in the database", result);
        return result;
    }

    @Override
    public Optional<Trip> findById(long tripId) {
        LOGGER.debug("Looking for the trip with id {} in the database", tripId);
        QueryBuilder queryBuilder = new QueryBuilder()
                .withWhere(QueryBuilder.DbField.TRIP_ID, QueryBuilder.DbComparator.EQUALS, tripId);
        final Optional<Trip> result = jdbcTemplate.query(queryBuilder.getString(),ROW_MAPPER,queryBuilder.getArguments().toArray()).stream().findFirst();
        LOGGER.debug("Found {} in the database", result.isPresent() ? result.get() : "nothing");
        return result;
    }
    @Override
    public Optional<Trip> findById(long tripId, LocalDateTime start, LocalDateTime end){
        LOGGER.debug("Looking for the trip with id {}, between '{}' and '{}', in the database", tripId, start, end);
        QueryBuilder queryBuilder = new QueryBuilder()
                .withWhere(QueryBuilder.DbField.TRIP_ID, QueryBuilder.DbComparator.EQUALS,tripId)
                .withWhere(QueryBuilder.DbField.TRIPS_DAYS, QueryBuilder.DbComparator.GREATER_OR_EQUALS,start)
                .withWhere(QueryBuilder.DbField.TRIPS_DAYS, QueryBuilder.DbComparator.LESS_OR_EQUAL,end);
        final Optional<Trip> result = jdbcTemplate.query(queryBuilder.getString(),getTripRowMapper(Optional.of(start),Optional.of(end)),queryBuilder.getArguments().toArray()).stream().findFirst();
        LOGGER.debug("Found {} in the database", result.isPresent() ? result.get() : "nothing");
        return result;
    }

    private static class QueryBuilder{
        private static final String select = "SELECT trips.trip_id, trips.max_passengers, trips.start_date_time,trips.end_date_time, trips.origin_address, origin.name as origin_city_name, origin.city_id as origin_city_id, origin.province_id as origin_province_id, trips.destination_address, destination.name as destination_city_name, destination.city_id as destination_city_id, destination.province_id as destination_province_id, users.email as user_email, users.user_id as user_id, users.phone as user_phone, users.user_role as user_role, users.password as user_password, users.username as user_username, users.surname as user_surname, users.user_image_id as user_image_id, user_city.city_id as user_city_id, user_city.name as user_city_name, user_city.province_id as user_city_province_id, users.mail_locale as user_mail_locale, cars.car_id as car_id, cars.plate  as car_plate, cars.info_car as car_info_car, cars.image_id as car_image_id, COALESCE(max(aux.count),0) as occupied_seats, trips.price as trip_price";
        private static final String selectCount = "select sum(count) as count FROM (select count(distinct trip_id) as count ";
        private static final String from = "FROM trips trips NATURAL LEFT OUTER JOIN LATERAL(\n" +
                "    SELECT trips.trip_id as trip_id,days.days, count(passengers.user_id) as count\n" +
                "    FROM generate_series(trips.start_date_time,trips.end_date_time,'7 day'::interval) days LEFT OUTER JOIN passengers ON passengers.trip_id = trips.trip_id AND passengers.start_date<=days.days AND passengers.end_date>=days.days\n" +
                "    GROUP BY days.days,passengers.trip_id\n" +
                ") aux NATURAL JOIN trips_cars_drivers NATURAL JOIN users NATURAL JOIN cars JOIN cities origin ON trips.origin_city_id = origin.city_id JOIN cities destination ON trips.destination_city_id=destination.city_id JOIN cities user_city ON users.city_id = user_city.city_id";
        private final StringBuilder where = new StringBuilder();
        private static final String groupBy ="GROUP BY trips.trip_id,trips.start_date_time, trips.end_date_time, trips.max_passengers, trips.origin_address, origin.name, origin.city_id, origin.province_id, destination_address, destination.name, destination.city_id, destination.province_id, users.email, users.user_id, users.phone, users.user_role, users.password, users.username, users.surname, user_city.city_id, user_city.name, user_city.province_id, cars.car_id, cars.plate, cars.info_car, cars.image_id";

        private final StringBuilder having = new StringBuilder();
        private final StringBuilder orderBy = new StringBuilder();
        private final StringBuilder limit = new StringBuilder();
        private final StringBuilder offset = new StringBuilder();
        List<Object> whereArguments = new ArrayList<>();
        List<Object> havingArguments = new ArrayList<>();
        List<Object> limitArguments = new ArrayList<>();

        List<Object> offsetArguments = new ArrayList<>();

        public String getString(){
            StringBuilder ans = new StringBuilder();
            return ans.append(select).append('\n')
                        .append(from).append('\n')
                        .append(where).append('\n')
                        .append(groupBy).append('\n')
                        .append(having).append('\n')
                        .append(orderBy).append('\n')
                        .append(offset).append('\n')
                        .append(limit)
                        .toString();
        }
        public String getCountString(){
            StringBuilder ans = new StringBuilder();
            return ans.append(selectCount).append('\n')
                    .append(from).append('\n')
                    .append(where).append('\n')
                    .append(groupBy).append('\n')
                    .append(having).append('\n')
                    .append(orderBy).append('\n')
                    .append(offset).append('\n')
                    .append(limit)
                    .append(") aux").append(';')
                    .toString();
        }
        public List<Object> getArguments(){
            List<Object> ans = new ArrayList<>();
            ans.addAll(whereArguments);
            ans.addAll(havingArguments);
            ans.addAll(offsetArguments);
            ans.addAll(limitArguments);
            return ans;
        }
        public QueryBuilder withWhere(DbField field, DbComparator comparator, Object value){
            if(this.where.length()>0){
                this.where.append(" AND ");
            }else{
                this.where.append("WHERE ");
            }
            this.where.append(field.dbName).append(' ').append(comparator.dbName).append(" ?");
            this.whereArguments.add(value);
            return this;
        }
        public QueryBuilder withWhere(DbField field, DbComparator comparator, DbField field2){
            if(this.where.length()>0){
                this.where.append(" AND ");
            }else{
                this.where.append("WHERE ");
            }
            this.where.append(field.dbName).append(' ').append(comparator.dbName).append(' ').append(field2.dbName);
            return this;
        }
        public QueryBuilder withHaving(DbField field, DbComparator comparator, DbField field2){
            if(this.having.length()>0){
                this.having.append(" AND ");
            }else{
                this.having.append("HAVING ");
            }
            this.having.append(field.dbName).append(' ').append(comparator.dbName).append(' ').append(field2.dbName);
            return this;
        }
        public QueryBuilder withHaving(DbField field, DbComparator comparator, Object value){
            if(this.having.length()>0){
                this.having.append(" AND ");
            }else{
                this.having.append("HAVING ");
            }
            this.having.append(field.dbName).append(' ').append(comparator.dbName).append(" ?");
            this.havingArguments.add(value);
            return this;
        }
        public QueryBuilder withOrderBy(DbField field, DbOrder order){
            if(this.orderBy.length()>0){
                this.orderBy.append(",");
            }else{
                this.orderBy.append(" ORDER BY ");
            }
            this.orderBy.append(field.dbName).append(' ').append(order.dbName).append(' ');
            return this;
        }
        public QueryBuilder withLimit(Object limit){
            if(this.limit.length()>0){
                return this;
            }
            this.limit.append("LIMIT ?");
            this.limitArguments.add(limit);
            return this;
        }

        public QueryBuilder withOffset(Object offset){
            if(this.offset.length()>0){
                return this;
            }
            this.offset.append("OFFSET ?");
            this.offsetArguments.add(offset);
            return this;
        }

        private enum DbField{
            TRIP_ID("trips.trip_id"),
            MAX_PASSENGERS("trips.max_passengers"),
            START_DATE_TIME("trips.start_date_time"),
            TIME("trips.start_date_time::time"),
            END_DATE_TIME("trips.end_date_time"),
            DAY_OF_WEEK("trips.day_of_week"),
            ORIGIN_ADDRESS("trips.origin_address"),
            ORIGIN_NAME("origin.name"),
            ORIGIN_CITY_ID("origin.city_id"),
            ORIGIN_PROVINCE_ID("origin.province_id"),
            DESTINATION_ADDRESS("trips.destination_address"),
            DESTINATION_NAME("destination.name"),
            DESTINATION_CITY_ID("destination.city_id"),
            DESTINATION_PROVINCE_ID("destination.province_id"),
            USER_EMAIL("users.email"),
            USER_ID("users.user_id"),
            USER_PHONE("users.phone"),
            CAR_ID("cars.car_id"),
            CAR_PLATE("cars.plate"),
            CAR_INFO_CAR("cars.info_car"),
            OCCUPIED_SEATS("coalesce(max(aux.count),0)"),
            PASSENGER_ID("passengers.user_id"),
            PASSENGER_START_DATE("passengers.start_date"),
            PASSENGER_END_DATE("passengers.end_date"),
            TRIPS_DAYS("aux.days"),
            TRIP_PRICE("trips.price"),
            NOW("NOW()");
            private final String dbName;
            private DbField(String dbName){
                this.dbName = dbName;
            }

            public String getDbName() {
                return dbName;
            }
        }
        private enum DbComparator{
            EQUALS("="),
            GREATER(">"),
            LESS("<"),
            GREATER_OR_EQUALS(">="),
            LESS_OR_EQUAL("<=");
            private final String dbName;
            private DbComparator(String dbName){
                this.dbName = dbName;
            }

            public String getDbName() {
                return dbName;
            }
        }
        private enum DbOrder{
            ASC("ASC"),
            DESC("DESC");
            private final String dbName;
            private DbOrder(String dbName){
                this.dbName = dbName;
            }

            public String getDbName() {
                return dbName;
            }
        }
    }
}


 */