package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.CarDao;
import ar.edu.itba.paw.interfaces.persistence.CityDao;
import ar.edu.itba.paw.interfaces.persistence.TripDao;
import ar.edu.itba.paw.interfaces.persistence.UserDao;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.trips.Trip;
import ar.edu.itba.paw.models.trips.TripInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.*;

//Timestamp en SQL
//LocalDateTime para Java
//En Java no usar los SQL

@Repository
public class TripDaoImpl implements TripDao {
    private static final RowMapper<Integer> COUNT_ROW_MAPPER = (resultSet,rowNum)-> resultSet.getInt("count");

    private final RowMapper<Trip> ROW_MAPPER = (resultSet,rowNum)-> {
        User user = new User(resultSet.getLong("user_id"),resultSet.getString("user_username"),
                resultSet.getString("user_surname"),resultSet.getString("user_email"),
                resultSet.getString("user_phone"),resultSet.getString("user_password"),
                resultSet.getTimestamp("user_birthdate").toLocalDateTime(),
                new City(resultSet.getLong("user_city_id"),resultSet.getString("user_city_name"), resultSet.getLong("user_city_province_id")),
                resultSet.getString("user_role"));
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
                resultSet.getTimestamp("user_birthdate").toLocalDateTime(),
                new City(resultSet.getLong("user_city_id"),resultSet.getString("user_city_name"), resultSet.getLong("user_city_province_id")),
                resultSet.getString("user_role"));
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
                    resultSet.getTimestamp("user_birthdate").toLocalDateTime(),
                    new City(resultSet.getLong("user_city_id"),resultSet.getString("user_city_name"), resultSet.getLong("user_city_province_id")),
                    resultSet.getString("user_role"));
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
                    queryStartDateTime.orElse(resultSet.getTimestamp("start_date_time").toLocalDateTime()),
                    queryEndDateTime.orElse(resultSet.getTimestamp("end_date_time").toLocalDateTime())
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
        return new Trip(tripKey.longValue(),originCity,originAddress,destinationCity,destinationAddress,startDateTime,endDateTime,max_passengers,driver,car,0,price,startDateTime,endDateTime);
    }

    @Override
    public boolean addPassenger(final Trip trip, final Passenger passenger){
        Map<String,Object> passengerData = new HashMap<>();
        passengerData.put("user_id",passenger.getUserId());
        passengerData.put("trip_id",trip.getTripId());
        passengerData.put("start_date",passenger.getStartDateTime());
        passengerData.put("end_date",passenger.getEndDateTime());
        return passengerInsert.execute(passengerData)>0;
    }
    @Override
    public boolean deleteTrip(final Trip trip){
        int tripCarsDriversRows = jdbcTemplate.update("DELETE FROM trips_cars_drivers WHERE trip_id = ?",trip.getTripId());
        jdbcTemplate.update("DELETE FROM passengers WHERE trip_id = ?",trip.getTripId());
        int tripRows = jdbcTemplate.update("DELETE FROM trips WHERE trip_id = ?",trip.getTripId());
        return tripRows>0 && tripCarsDriversRows>0;
    }
    private static void validatePageAndSize(int page, int pageSize){
        if(page<0 || pageSize<0) throw new IllegalArgumentException();
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
        return jdbcTemplate.query("SELECT * FROM passengers NATURAL JOIN users NATURAL JOIN cities " +
                        "WHERE trip_id = ? AND passengers.start_date<=? AND passengers.end_date>=? "
                ,PASSENGER_ROW_MAPPER,trip.getTripId(),startDateTime,endDateTime);
    }
    @Override
    public PagedContent<TripInstance> getTripInstances(final Trip trip,int page, int pageSize){
        return getTripInstances(trip,page,pageSize,trip.getStartDateTime(),trip.getEndDateTime());
    }
    @Override
    public PagedContent<TripInstance> getTripInstances(final Trip trip, int page, int pageSize, LocalDateTime start, LocalDateTime end){
//        validatePageAndSize(page,pageSize);
        String query = "FROM generate_series(?,?, '7 day'::interval) days LEFT OUTER JOIN passengers " +
                "ON passengers.start_date<=days.days AND passengers.end_date>=days.days AND passengers.trip_id=? " +
                "WHERE days.days>=? AND days.days<=? " +
                "GROUP BY days.days ";
        List<TripInstance> ans =  jdbcTemplate.query("SELECT days as trip_date_time, count(passengers.user_id) as  trip_passenger_count " +
                query +
                "OFFSET ? " +
                "LIMIT ?",getTripInstanceRowMapper(trip),trip.getStartDateTime(),trip.getEndDateTime(),trip.getTripId(),start,end,page*pageSize,pageSize);
        int total = jdbcTemplate.query("SELECT sum(aux) as count FROM ( SELECT count(*) as aux " + query +") t",COUNT_ROW_MAPPER,trip.getStartDateTime(),trip.getEndDateTime(),trip.getTripId(),start,end).stream().findFirst().orElse(0);
        return new PagedContent<>(ans,page,pageSize,total);
    }
    @Override
    public PagedContent<Trip> getTripsCreatedByUser(final User user,int page, int pageSize){
        QueryBuilder queryBuilder = new QueryBuilder()
                .withWhere(QueryBuilder.DbField.USER_ID, QueryBuilder.DbComparator.EQUALS,user.getUserId());
        int total = jdbcTemplate.query(queryBuilder.getCountString(),COUNT_ROW_MAPPER,queryBuilder.getArguments().toArray()).stream().findFirst().orElse(0);
        queryBuilder.withOffset(page*pageSize)
                    .withLimit(pageSize);
        List<Trip> ans = jdbcTemplate.query(queryBuilder.getString(),ROW_MAPPER,queryBuilder.getArguments().toArray());
        return new PagedContent<>(ans,page,pageSize,total);
    }

    //TODO: chequear
    @Override
    public PagedContent<Trip> getTripsWhereUserIsPassenger(final User user, int page, int pageSize) {
        List<Object> args = new ArrayList<>();
        args.add(user.getUserId());
        QueryBuilder queryBuilder = new QueryBuilder()
                .withWhereIn(QueryBuilder.DbField.TRIP_ID, "SELECT trip_id FROM passengers WHERE user_id = ?", args);
        int total = jdbcTemplate.query(queryBuilder.getCountString(), COUNT_ROW_MAPPER, queryBuilder.getArguments().toArray()).stream().findFirst().orElse(0);
        queryBuilder.withOffset(page * pageSize)
                .withLimit(pageSize);
        //Quiero traer para cada trip, la fecha donde esta el usuario que estoy buscando
        //No lo puedo traer en la anterior, porque tengo que agrupar por fecha, no por pasajero, para tener la cantidad de asientos en esa fecha (que no solo sean del pasajero)
        String query = "SELECT trips.trip_id, trips.max_passengers, trips.start_date_time,trips.end_date_time, trips.origin_address, origin_city_name, origin_city_id, origin_province_id, destination_address, destination_city_name, destination_city_id, destination_province_id, user_email, trips.user_id as user_id, user_phone,  user_birthdate, user_role, user_password,  user_username, user_surname,  user_city_id,  user_city_name,  user_city_province_id , car_id, car_plate, car_info_car,  car_image_id, occupied_seats, trip_price, p.start_date as passenger_start_date, p.end_date as passenger_end_date "
                + "FROM passengers p JOIN ( " + queryBuilder.getString() + " ) trips ON trips.trip_id = p.trip_id "
                + "WHERE p.user_id = ?";
        List<Object> arguments = queryBuilder.getArguments();
        arguments.add(user.getUserId());
        List<Trip> ans =  jdbcTemplate.query(query,PASSENGER_TRIPS_ROW_MAPPER,arguments.toArray());
        return new PagedContent<>(ans,page,pageSize,total);
    }

    @Override
    public PagedContent<Trip> getIncomingTrips(int page, int pageSize){
        validatePageAndSize(page,pageSize);
        QueryBuilder queryBuilder = new QueryBuilder()
                .withWhere(QueryBuilder.DbField.END_DATE_TIME, QueryBuilder.DbComparator.GREATER_OR_EQUALS,LocalDateTime.now())
                .withHaving(QueryBuilder.DbField.OCCUPIED_SEATS, QueryBuilder.DbComparator.LESS, QueryBuilder.DbField.MAX_PASSENGERS)
                .withOrderBy(QueryBuilder.DbField.END_DATE_TIME, QueryBuilder.DbOrder.ASC);
        int total = jdbcTemplate.query(queryBuilder.getCountString(),COUNT_ROW_MAPPER,queryBuilder.getArguments().toArray()).stream().findFirst().orElse(0);
        queryBuilder
                .withOffset(page * pageSize)
                .withLimit(pageSize);
        List<Trip> ans =  jdbcTemplate.query(queryBuilder.getString(),ROW_MAPPER,queryBuilder.getArguments().toArray());
        return new PagedContent<>(ans,page,pageSize,total);
    }
    @Override
    public PagedContent<Trip> getTripsWithFilters(
            long origin_city_id, long destination_city_id,
            LocalDateTime startDateTime, Optional<DayOfWeek> dayOfWeek, Optional<LocalDateTime> endDateTime,
            Optional<Double> minPrice, Optional<Double> maxPrice,
            int page, int pageSize){
        validatePageAndSize(page,pageSize);
        QueryBuilder queryBuilder = new QueryBuilder()
                //Busco a las ocurrencias de ese viaje que sean desde ahora
                .withWhere(QueryBuilder.DbField.TRIPS_DAYS, QueryBuilder.DbComparator.GREATER_OR_EQUALS,startDateTime)
                .withWhere(QueryBuilder.DbField.END_DATE_TIME, QueryBuilder.DbComparator.GREATER_OR_EQUALS,startDateTime)
                .withWhere(QueryBuilder.DbField.ORIGIN_CITY_ID, QueryBuilder.DbComparator.EQUALS,origin_city_id)
                .withWhere(QueryBuilder.DbField.DESTINATION_CITY_ID, QueryBuilder.DbComparator.EQUALS,destination_city_id)
                .withHaving(QueryBuilder.DbField.OCCUPIED_SEATS, QueryBuilder.DbComparator.LESS, QueryBuilder.DbField.MAX_PASSENGERS)
                .withOrderBy(QueryBuilder.DbField.END_DATE_TIME, QueryBuilder.DbOrder.ASC);
        endDateTime.ifPresent(localDateTime -> queryBuilder.withWhere(QueryBuilder.DbField.TRIPS_DAYS, QueryBuilder.DbComparator.LESS_OR_EQUAL,localDateTime)
                                .withWhere(QueryBuilder.DbField.END_DATE_TIME, QueryBuilder.DbComparator.GREATER_OR_EQUALS,localDateTime));
        dayOfWeek.ifPresent(dayOfWeek1 -> queryBuilder.withWhere(QueryBuilder.DbField.DAY_OF_WEEK, QueryBuilder.DbComparator.EQUALS,dayOfWeek1.getValue()));
        minPrice.ifPresent(price -> queryBuilder.withWhere(QueryBuilder.DbField.TRIP_PRICE, QueryBuilder.DbComparator.GREATER_OR_EQUALS,price));
        maxPrice.ifPresent(price -> queryBuilder.withWhere(QueryBuilder.DbField.TRIP_PRICE, QueryBuilder.DbComparator.LESS_OR_EQUAL,price));
        int total = jdbcTemplate.query(queryBuilder.getCountString(),COUNT_ROW_MAPPER,queryBuilder.getArguments().toArray()).stream().findFirst().orElse(0);
        queryBuilder.withOffset(page*pageSize)
                .withLimit(pageSize);
        List<Trip> ans =  jdbcTemplate.query(queryBuilder.getString(),getTripRowMapper(Optional.of(startDateTime),endDateTime),queryBuilder.getArguments().toArray());
        return new PagedContent<>(ans,page,pageSize,total);
    }

    @Override
    public Optional<Trip> findById(long tripId) {
        QueryBuilder queryBuilder = new QueryBuilder()
                .withWhere(QueryBuilder.DbField.TRIP_ID, QueryBuilder.DbComparator.EQUALS, tripId);
        return jdbcTemplate.query(queryBuilder.getString(),ROW_MAPPER,queryBuilder.getArguments().toArray()).stream().findFirst();
    }
    @Override
    public Optional<Trip> findById(long tripId, LocalDateTime start, LocalDateTime end){
        QueryBuilder queryBuilder = new QueryBuilder()
                .withWhere(QueryBuilder.DbField.TRIP_ID, QueryBuilder.DbComparator.EQUALS,tripId)
                .withWhere(QueryBuilder.DbField.TRIPS_DAYS, QueryBuilder.DbComparator.GREATER_OR_EQUALS,start)
                .withWhere(QueryBuilder.DbField.TRIPS_DAYS, QueryBuilder.DbComparator.LESS_OR_EQUAL,end);
        return jdbcTemplate.query(queryBuilder.getString(),getTripRowMapper(Optional.of(start),Optional.of(end)),queryBuilder.getArguments().toArray()).stream().findFirst();
    }

    private static class QueryBuilder{
        private static final String select = "SELECT trips.trip_id, trips.max_passengers, trips.start_date_time,trips.end_date_time, trips.origin_address, origin.name as origin_city_name, origin.city_id as origin_city_id, origin.province_id as origin_province_id, trips.destination_address, destination.name as destination_city_name, destination.city_id as destination_city_id, destination.province_id as destination_province_id, users.email as user_email, users.user_id as user_id, users.phone as user_phone, users.birthdate as user_birthdate, users.user_role as user_role, users.password as user_password, users.username as user_username, users.surname as user_surname, user_city.city_id as user_city_id, user_city.name as user_city_name, user_city.province_id as user_city_province_id , cars.car_id as car_id, cars.plate  as car_plate, cars.info_car as car_info_car, cars.image_id as car_image_id, COALESCE(max(aux.count),0) as occupied_seats, trips.price as trip_price";
        private static final String selectCount = "select sum(count) as count FROM (select count(distinct trip_id) as count ";
        private static final String from = "FROM trips trips NATURAL LEFT OUTER JOIN LATERAL(\n" +
                "    SELECT trips.trip_id as trip_id,days.days, count(passengers.user_id) as count\n" +
                "    FROM generate_series(trips.start_date_time,trips.end_date_time,'7 day'::interval) days LEFT OUTER JOIN passengers ON passengers.trip_id = trips.trip_id AND passengers.start_date<=days.days AND passengers.end_date>=days.days\n" +
                "    GROUP BY days.days,passengers.trip_id\n" +
                ") aux NATURAL JOIN trips_cars_drivers NATURAL JOIN users NATURAL JOIN cars JOIN cities origin ON trips.origin_city_id = origin.city_id JOIN cities destination ON trips.destination_city_id=destination.city_id JOIN cities user_city ON users.city_id = user_city.city_id";
        private final StringBuilder where = new StringBuilder();
        private static final String groupBy ="GROUP BY trips.trip_id,trips.start_date_time, trips.end_date_time, trips.max_passengers, trips.origin_address, origin.name, origin.city_id, origin.province_id, destination_address, destination.name, destination.city_id, destination.province_id, users.email, users.user_id, users.phone,  users.birthdate, users.user_role, users.password, users.username, users.surname, user_city.city_id, user_city.name, user_city.province_id, cars.car_id, cars.plate, cars.info_car, cars.image_id";

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
