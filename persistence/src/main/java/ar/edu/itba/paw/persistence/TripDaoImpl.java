package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.CarDao;
import ar.edu.itba.paw.interfaces.persistence.CityDao;
import ar.edu.itba.paw.interfaces.persistence.TripDao;
import ar.edu.itba.paw.interfaces.persistence.UserDao;
import ar.edu.itba.paw.models.Car;
import ar.edu.itba.paw.models.City;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.*;

//Timestamp en SQL
//LocalDateTime para Java
//En Java no usar los SQL

@Repository
public class TripDaoImpl implements TripDao {

    //NO anidar DAO's, son muchos llamados a la BD
    //Hacer los NATURAL JOIN

    //TODO: preguntar si usamos metodos de otros DAO's o hacemos Natural Join
    private RowMapper<Trip> ROW_MAPPER = (resultSet,rowNum)-> {
        return new Trip(
                resultSet.getLong("trip_id"),
                new City(resultSet.getLong("origin_city_id"),resultSet.getString("origin_city_name"),resultSet.getLong("origin_province_id")),
                resultSet.getString("origin_address"),
                new City(resultSet.getLong("destination_city_id"),resultSet.getString("destination_city_name"),resultSet.getLong("destination_province_id")),
                resultSet.getString("destination_address"),
                resultSet.getTimestamp("origin_date_time").toLocalDateTime(),
                resultSet.getInt("max_passengers"),
                new User(resultSet.getLong("user_id"),resultSet.getString("user_email"),resultSet.getString("user_phone")),
                new Car(resultSet.getLong("car_id"),resultSet.getString("car_plate"),resultSet.getString("car_info_car"),new User(resultSet.getLong("user_id"),resultSet.getString("user_email"),resultSet.getString("user_phone"))),
                resultSet.getInt("occupied_seats")
        );
    };
//
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

    @Override
    public Trip create(final City originCity, final String originAddress, final City destinationCity, final String destinationAddress, final Car car, final LocalDateTime originDateTime, final double price, final int max_passengers, final User driver) {
        //Insertamos los datos en la tabla trip
        Map<String, Object> tripData = new HashMap<>();
        tripData.put("max_passengers",max_passengers);
        tripData.put("origin_address",originAddress);
        tripData.put("destination_address",destinationAddress);
        tripData.put("price",price);
        tripData.put("origin_date_time",originDateTime);
        tripData.put("destination_city_id",destinationCity.getId());
        tripData.put("origin_city_id",originCity.getId());
        Number tripKey = tripsInsert.executeAndReturnKey(tripData);
        //Insertamos el auto y usuario en la tabla trips_cars_drivers
        Map<String,Object> driverCarData = new HashMap<>();
        driverCarData.put("trip_id",tripKey.longValue());
        driverCarData.put("user_id",driver.getUserId());
        driverCarData.put("car_id",car.getCarId());
        driverCarInsert.execute(driverCarData);
        return new Trip(tripKey.longValue(),originCity,originAddress,destinationCity,destinationAddress,originDateTime,max_passengers,driver,car, 0);
    }

    @Override
    public boolean addPassenger(final Trip trip, final User passenger){
        if(trip == null || passenger == null){
            return false;
        }
        Map<String,Object> passengerData = new HashMap<>();
        passengerData.put("user_id",passenger.getUserId());
        passengerData.put("trip_id",trip.getTripId());
        //TODO: ver si es 1
        return passengerInsert.execute(passengerData)>0;
    }

    @Override
    public List<User> getPassengers(final long tripId){
        return jdbcTemplate.query("SELECT * FROM passengers NATURAL JOIN users WHERE trip_id=?",UserDaoImpl.ROW_MAPPER,tripId);
    }

    @Override
    public List<Trip> getFirstNTrips(long n){
        QueryBuilder queryBuilder = new QueryBuilder()
                .withOrderBy(QueryBuilder.DbField.ORIGIN_DATE_TIME, QueryBuilder.DbOrder.ASC)
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
    public List<Trip> getTripsByDateTimeAndOriginAndDestination(long origin_city_id, long destination_city_id, LocalDateTime dateTime){
        QueryBuilder queryBuilder = new QueryBuilder()
                .withWhere(QueryBuilder.DbField.ORIGIN_CITY_ID, QueryBuilder.DbComparator.EQUALS,origin_city_id)
                .withWhere(QueryBuilder.DbField.DESTINATION_CITY_ID, QueryBuilder.DbComparator.EQUALS,destination_city_id)
                .withWhere(QueryBuilder.DbField.ORIGIN_DATE_TIME, QueryBuilder.DbComparator.EQUALS,dateTime)
                .withOrderBy(QueryBuilder.DbField.ORIGIN_DATE_TIME, QueryBuilder.DbOrder.ASC)
                .withLimit(10);
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
        private final StringBuilder orderBy = new StringBuilder();
        private final StringBuilder limit = new StringBuilder();
        List<Object> whereArguments = new ArrayList<>();
        List<Object> limitArguments = new ArrayList<>();

        public String getString(){
            StringBuilder ans = new StringBuilder();
            return ans.append(select).append('\n').append(from).append('\n').append(where).append('\n').append(groupBy).append('\n').append(orderBy).append('\n').append(limit).append(';').toString();
        }
        public List<Object> getArguments(){
            List<Object> ans = new ArrayList<>();
            ans.addAll(whereArguments);
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
            ORIGIN_DATE_TIME("trips.origin_date_time"),
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
            OCCUPIED_SEATS(" count(passengers.user_id)");

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
