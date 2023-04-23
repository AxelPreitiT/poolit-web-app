package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.CarDao;
import ar.edu.itba.paw.interfaces.persistence.CityDao;
import ar.edu.itba.paw.interfaces.persistence.TripDao;
import ar.edu.itba.paw.interfaces.persistence.UserDao;
import ar.edu.itba.paw.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.*;

//Timestamp en SQL
//LocalDateTime para Java
//En Java no usar los SQL

@Repository
public class TripDaoImpl implements TripDao {
    private final RowMapper<Trip> ROW_MAPPER = (resultSet,rowNum)-> {
        return new Trip(
                resultSet.getLong("trip_id"),
                new City(resultSet.getLong("origin_city_id"),resultSet.getString("origin_city_name"),resultSet.getLong("origin_province_id")),
                resultSet.getString("origin_address"),
                new City(resultSet.getLong("destination_city_id"),resultSet.getString("destination_city_name"),resultSet.getLong("destination_province_id")),
                resultSet.getString("destination_address"),
                resultSet.getTimestamp("start_date_time").toLocalDateTime(),
                resultSet.getTimestamp("end_date_time").toLocalDateTime(),
                resultSet.getInt("max_passengers"),
                new User(resultSet.getLong("user_id"),resultSet.getString("user_email"),resultSet.getString("user_phone")),
                new Car(resultSet.getLong("car_id"),resultSet.getString("car_plate"),resultSet.getString("car_info_car"),new User(resultSet.getLong("user_id"),resultSet.getString("user_email"),resultSet.getString("user_phone"))),
                resultSet.getInt("occupied_seats")
        );
    };
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
    private RowMapper<TripInstance> getTripInstanceRowMapper(Trip trip) {
        return (resultSet, rowNum) -> {
            return new TripInstance(
                    resultSet.getTimestamp("trip_date_time").toLocalDateTime(),
                    trip,
                    resultSet.getInt("trip_passenger_count")
            );
        };
    }

    @Override
    public Trip create(final City originCity, final String originAddress, final City destinationCity, final String destinationAddress, final Car car, final LocalDateTime startDateTime, final LocalDateTime endDateTime, final boolean isRecurrent,final double price, final int max_passengers, final User driver) {
        if( startDateTime==null || endDateTime==null
            || (isRecurrent && ( !startDateTime.isBefore(endDateTime) || !startDateTime.getDayOfWeek().equals(endDateTime.getDayOfWeek())))
            || (!isRecurrent && !startDateTime.equals(endDateTime))
            || originCity==null || destinationCity==null || car == null || driver == null
            || max_passengers<=0 || price<0){
            throw  new IllegalArgumentException();
        }
        //Insertamos los datos en la tabla trip
        Map<String, Object> tripData = new HashMap<>();
        tripData.put("max_passengers",max_passengers);
        tripData.put("origin_address",originAddress);
        tripData.put("destination_address",destinationAddress);
        tripData.put("price",price);
        tripData.put("start_date_time",startDateTime);
        tripData.put("end_date_time",endDateTime);
        tripData.put("day_of_week",startDateTime.getDayOfWeek().getValue());
        tripData.put("destination_city_id",destinationCity.getId());
        tripData.put("origin_city_id",originCity.getId());
        Number tripKey = tripsInsert.executeAndReturnKey(tripData);
        //Insertamos el auto y usuario en la tabla trips_cars_drivers
        Map<String,Object> driverCarData = new HashMap<>();
        driverCarData.put("trip_id",tripKey.longValue());
        driverCarData.put("user_id",driver.getUserId());
        driverCarData.put("car_id",car.getCarId());
        driverCarInsert.execute(driverCarData);
        return new Trip(tripKey.longValue(),originCity,originAddress,destinationCity,destinationAddress,startDateTime,endDateTime,max_passengers,driver,car,0);
    }

    @Override
    public boolean addPassenger(final Trip trip, final User passenger, final LocalDateTime startDateTime, final LocalDateTime endDateTime){
        //StartDateTime of passenger must be in a day that the trip happens
        //EndDateTime of passenger must be in a day that the trip happens
        if(trip == null || passenger == null
            || startDateTime==null || endDateTime == null
            || startDateTime.isAfter(endDateTime)
            || trip.getStartDateTime().isAfter(startDateTime)
            || trip.getEndDateTime().isBefore(endDateTime)
            || Period.between(trip.getStartDateTime().toLocalDate(),startDateTime.toLocalDate()).getDays()%7!=0
            || Period.between(trip.getEndDateTime().toLocalDate(),endDateTime.toLocalDate()).getDays()%7!=0){
            throw new IllegalArgumentException();
        }
        Map<String,Object> passengerData = new HashMap<>();
        passengerData.put("user_id",passenger.getUserId());
        passengerData.put("trip_id",trip.getTripId());
        passengerData.put("start_date",startDateTime);
        passengerData.put("end_date",endDateTime);
        return passengerInsert.execute(passengerData)>0;
    }
    @Override

    public List<User> getPassengers(final TripInstance tripInstance){
        return jdbcTemplate.query("SELECT * FROM passengers NATURAL JOIN users " +
                "WHERE trip_id = ? AND passengers.start_date<=? AND passengers.end_date>=?",UserDaoImpl.ROW_MAPPER,tripInstance.getTrip().getTripId(),tripInstance.getDateTime());
    }
    @Override
    public List<User> getPassengers(final Trip trip, final LocalDateTime dateTime){
        if( trip.getStartDateTime().isAfter(dateTime)
                || trip.getEndDateTime().isBefore(dateTime)
                || Period.between(trip.getStartDateTime().toLocalDate(),dateTime.toLocalDate()).getDays()%7!=0
        ){
            throw new IllegalArgumentException();
        }
        return getPassengers(new TripInstance(dateTime,trip,0));
    }

    //TODO: preguntar si es mejor tener un ROW_MAPPER y despues hacer un foreach para asignarle trip
    //O si conviene hacer esto
    @Override
    public List<TripInstance> getTripInstances(final Trip trip){
        return jdbcTemplate.query("SELECT days as trip_date_time, count(passengers.user_id) as  trip_passenger_count " +
                "FROM generate_series(?,?, '7 day'::interval) days, passengers " +
                "WHERE passengers.trip_id = ?  AND passengers.start_date<=days.days AND passengers.end_date>=days.days " +
                "GROUP BY days.days",getTripInstanceRowMapper(trip),trip.getStartDateTime(),trip.getEndDateTime(),trip.getTripId());
    }
    @Override
    public List<Trip> getTripsCreatedByUser(final User user){
        QueryBuilder queryBuilder = new QueryBuilder()
                .withWhere(QueryBuilder.DbField.USER_ID, QueryBuilder.DbComparator.EQUALS,user.getUserId());
        return jdbcTemplate.query(queryBuilder.getString(),ROW_MAPPER,queryBuilder.getArguments().toArray());
    }
    @Override
    public List<Trip> getTripsWhereUserIsPassenger(final User user){
        List<Object> args = new ArrayList<>();
        args.add(user.getUserId());
        QueryBuilder queryBuilder = new QueryBuilder()
                .withWhereIn(QueryBuilder.DbField.TRIP_ID,"SELECT trip_id FROM passengers WHERE user_id = ?",args);
        return jdbcTemplate.query(queryBuilder.getString(),ROW_MAPPER,queryBuilder.getArguments());
    }

    @Override
    public List<Trip> getFirstNTrips(long n){
        QueryBuilder queryBuilder = new QueryBuilder()
                .withWhere(QueryBuilder.DbField.END_DATE_TIME, QueryBuilder.DbComparator.GREATER_OR_EQUALS,LocalDateTime.now())
                .withWhere(QueryBuilder.DbField.OCCUPIED_SEATS, QueryBuilder.DbComparator.LESS, QueryBuilder.DbField.MAX_PASSENGERS)
                .withOrderBy(QueryBuilder.DbField.END_DATE_TIME, QueryBuilder.DbOrder.ASC)
                .withLimit(n);
        return jdbcTemplate.query(queryBuilder.getString(),ROW_MAPPER,queryBuilder.getArguments().toArray());
    }
    //Preguntar:
    //reutilizar query -> concatenar string
    //pasar timestamp -> Probar
    //application properties condicional -> comentar el que no usamos (o crear perfiles)
    //ROWMAPPER globales -> hacer package protected
    //Backup BD (si no la semana que viene)
    @Override
    public List<Trip> getTripsByDateTimeAndOriginAndDestination(long origin_city_id, long destination_city_id, Optional<LocalDateTime> dateTime){
        QueryBuilder queryBuilder = new QueryBuilder()
                .withWhere(QueryBuilder.DbField.END_DATE_TIME, QueryBuilder.DbComparator.GREATER_OR_EQUALS,LocalDateTime.now())
                .withWhere(QueryBuilder.DbField.ORIGIN_CITY_ID, QueryBuilder.DbComparator.EQUALS,origin_city_id)
                .withWhere(QueryBuilder.DbField.DESTINATION_CITY_ID, QueryBuilder.DbComparator.EQUALS,destination_city_id)
                .withHaving(QueryBuilder.DbField.OCCUPIED_SEATS, QueryBuilder.DbComparator.LESS, QueryBuilder.DbField.MAX_PASSENGERS)
                .withOrderBy(QueryBuilder.DbField.END_DATE_TIME, QueryBuilder.DbOrder.ASC)
                .withLimit(10);
        //TODO: fix for recurrent trips
        dateTime.ifPresent(localDateTime -> queryBuilder.withWhere(QueryBuilder.DbField.END_DATE_TIME, QueryBuilder.DbComparator.EQUALS, localDateTime));
        return jdbcTemplate.query(queryBuilder.getString(),ROW_MAPPER,queryBuilder.getArguments().toArray());
    }
    @Override
    public Optional<Trip> findById(long tripId) {
        QueryBuilder queryBuilder = new QueryBuilder()
                .withWhere(QueryBuilder.DbField.TRIP_ID, QueryBuilder.DbComparator.EQUALS, tripId);
        return jdbcTemplate.query(queryBuilder.getString(),ROW_MAPPER,queryBuilder.getArguments().toArray()).stream().findFirst();
    }


    private static class QueryBuilder{
        private final String select = "SELECT trips.trip_id, trips.max_passengers, trips.origin_date_time, trips.origin_address, origin.name as origin_city_name, origin.city_id as origin_city_id, origin.province_id as origin_province_id, trips.destination_address, destination.name as destination_city_name, destination.city_id as destination_city_id, destination.province_id as destination_province_id, users.email as user_email, users.user_id as user_id, users.phone as user_phone, cars.car_id as car_id, cars.plate  as car_plate, cars.info_car as car_info_car, count(passengers.user_id) as occupied_seats";
        private final String from = "FROM trips NATURAL JOIN trips_cars_drivers NATURAL JOIN users NATURAL JOIN cars JOIN cities origin ON trips.origin_city_id = origin.city_id JOIN cities destination ON trips.destination_city_id=destination.city_id LEFT OUTER JOIN passengers ON passengers.trip_id = trips.trip_id";
        private final StringBuilder where = new StringBuilder();
        private final String groupBy = "GROUP BY trips.trip_id,trips.origin_date_time, trips.max_passengers, trips.origin_address, origin.name, origin.city_id, origin.province_id, destination_address, destination.name, destination.city_id, destination.province_id, users.email, users.user_id, users.phone, cars.car_id, cars.plate, cars.info_car";

        private final StringBuilder having = new StringBuilder();
        private final StringBuilder orderBy = new StringBuilder();
        private final StringBuilder limit = new StringBuilder();
        List<Object> whereArguments = new ArrayList<>();
        List<Object> havingArguments = new ArrayList<>();
        List<Object> limitArguments = new ArrayList<>();


        public String getString(){
            StringBuilder ans = new StringBuilder();
            return ans.append(select).append('\n')
                        .append(from).append('\n')
                        .append(where).append('\n')
                        .append(groupBy).append('\n')
                        .append(having).append('\n')
                        .append(orderBy).append('\n')
                        .append(limit).append(';')
                        .toString();
        }
        public List<Object> getArguments(){
            List<Object> ans = new ArrayList<>();
            ans.addAll(whereArguments);
            ans.addAll(havingArguments);
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
        //TODO: revisar esto, no es muy lindo
        public QueryBuilder withWhereIn(DbField field, String setSelect, List<Object> arguments){
            if(this.where.length()>0){
                this.where.append(" AND ");
            }else{
                this.where.append("WHERE ");
            }
            this.where.append(field.dbName).append(" IN ").append(" (").append(setSelect).append(") ");
            whereArguments.addAll(arguments);
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

        private enum DbField{
            TRIP_ID("trips.trip_id"),
            MAX_PASSENGERS("trips.max_passengers"),
            START_DATE_TIME("trips.start_date_time"),
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
            OCCUPIED_SEATS("count(passengers.user_id)"),
            PASSENGER_ID("passengers.user_id");

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
