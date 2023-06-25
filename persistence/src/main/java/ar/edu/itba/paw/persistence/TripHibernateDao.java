package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.TripDao;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.trips.Trip;
import ar.edu.itba.paw.models.trips.TripInstance;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;


@Repository
public class TripHibernateDao implements TripDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(TripHibernateDao.class);

    private static final LocalTime MIN_TIME = LocalTime.of(0,0);
    private static final LocalTime MAX_TIME = LocalTime.of(23,59);
    @PersistenceContext
    private EntityManager em;
    @Override
    public Trip create(City originCity, String originAddress, City destinationCity, String destinationAddress, Car car, LocalDateTime startDateTime, LocalDateTime endDateTime, boolean isRecurrent, double price, int max_passengers, User driver) {
        Trip trip = new Trip(originCity, originAddress, destinationCity, destinationAddress, startDateTime, endDateTime, max_passengers, driver, car, price);
        LOGGER.debug("Adding new trip from '{}' to '{}', with startDate '{}' and endDate '{}', and created by driver with id {} to the database", originCity, destinationCity, startDateTime, endDateTime, driver.getUserId());
        em.persist(trip);
        LOGGER.info("Trip added to the database with id {}", trip.getTripId());
        LOGGER.debug("New {}", trip);
        return trip;
    }

    @Override
    public boolean addPassenger(Trip trip,User user,LocalDateTime startDateTime,LocalDateTime endDateTime) {
        em.merge(trip);
        em.merge(user);
        Passenger aux = new Passenger(user,trip,startDateTime,endDateTime);
        LOGGER.debug("Adding new passenger with user id {} to the trip with id {} in the database",user.getUserId(),trip.getTripId());
        em.persist(aux);
        LOGGER.info("Passenger with user id {} added to the trip with id {} in the database",user.getUserId(),trip.getTripId());
        return aux.getTrip()!=null;//Es un return true, revisar
    }

    @Override
    public boolean removePassenger(Trip trip, Passenger passenger) {
        LOGGER.debug("Removing passenger with id {} from the trip with id {} in the database",passenger.getUserId(),trip.getTripId());
        em.merge(passenger);
        em.remove(passenger);
        return true;
    }

    @Override
    public boolean deleteTrip(Trip trip){
        em.merge(trip);
        em.remove(trip);
        LOGGER.info("Trip with id {} deleted from the database",trip.getTripId());
        return true;
    }

    @Override
    public boolean markTripAsDeleted(Trip trip, LocalDateTime lastOccurrence) {
        em.merge(trip);
        trip.setDeleted(true);
        trip.setLastOccurrence(lastOccurrence);
        em.persist(trip);
        LOGGER.info("Trip with id {} deleted from the database",trip.getTripId());
        return true;
    }

    @Override
    public List<Passenger> getPassengers(TripInstance tripInstance) {
        return getPassengers(tripInstance.getTrip(),tripInstance.getDateTime());
    }

    @Override
    public List<Passenger> getPassengers(Trip trip, LocalDateTime dateTime) {
        return getPassengers(trip,dateTime,dateTime);
    }
    @Override
    public List<Passenger> getPassengers(Trip trip, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        LOGGER.debug("Looking for the passengers of the trip with id {}, between '{}' and '{}', in the database",trip.getTripId(),startDateTime,endDateTime);
        TypedQuery<Passenger> query = em.createQuery("from Passenger p WHERE p.trip = :trip AND ((p.startDateTime<=:startDate AND p.endDateTime>=:startDate) OR (p.startDateTime<= :endDate AND p.endDateTime>= :endDate) OR (p.startDateTime >= :startDate AND p.endDateTime <= :endDate))",Passenger.class);
        query.setParameter("trip",trip);
        query.setParameter("startDate", startDateTime);
        query.setParameter("endDate",endDateTime);
        List<Passenger> result = query.getResultList();
        LOGGER.debug("Found {} in the database", result);
        return result;
    }

    @Override
    public PagedContent<Passenger> getPassengers(Trip trip, LocalDateTime startDateTime, LocalDateTime endDateTime, Optional<Passenger.PassengerState> passengerState,int page, int pageSize) {
        LOGGER.debug("Looking for the passengers of the trip with id {}, between '{}' and '{}', in the database",trip.getTripId(),startDateTime,endDateTime);
        String queryString = "FROM passengers p " +
                "WHERE p.trip_id = :tripId  AND ((p.start_date<=:startDate AND p.end_date>=:startDate) OR (p.start_date<= :endDate AND p.end_date>= :endDate) OR (p.start_date >= :startDate AND p.end_date <= :endDate)) "; //la ultima condicion es por si el pasajero esta adentro del intervalo buscado
        if(passengerState.isPresent()){
            queryString += "AND p.passenger_state = :passengerStateString ";
        }
        Query countQuery = em.createNativeQuery( "SELECT count(distinct user_id) "+ queryString);
        Query idQuery = em.createNativeQuery("SELECT user_id " + queryString);
        countQuery.setParameter("tripId",trip.getTripId());
        idQuery.setParameter("tripId",trip.getTripId());
        countQuery.setParameter("startDate", startDateTime);
        countQuery.setParameter("endDate",endDateTime);
        idQuery.setParameter("startDate", startDateTime);
        idQuery.setParameter("endDate",endDateTime);
        passengerState.ifPresent(passengerState1 -> {
            countQuery.setParameter("passengerStateString", passengerState1.toString());
            idQuery.setParameter("passengerStateString", passengerState1.toString());
        });
        idQuery.setMaxResults(pageSize);//Offset
        idQuery.setFirstResult(page*pageSize);//Limit
        @SuppressWarnings("unchecked")
        Integer total = ((List<Object>) countQuery.getResultList()).stream().map(elem -> ((Number) elem).intValue()).findFirst().orElseThrow(IllegalStateException::new);
        @SuppressWarnings("unchecked")
        List<Long> ids = ((List<Object>) idQuery.getResultList()).stream().map(elem -> ((Number) elem).longValue()).collect(Collectors.toList());
        if(ids.isEmpty()){
            return new PagedContent<>(new ArrayList<>(),page,pageSize,0);
        }
        TypedQuery<Passenger> query = em.createQuery("from Passenger p WHERE p.trip = :trip AND p.user.userId in :ids",Passenger.class);
        query.setParameter("trip",trip);
        query.setParameter("ids", ids);
        return new PagedContent<>(query.getResultList(),page,pageSize,total);
    }
    @Override
    public boolean acceptPassenger(Passenger passenger) {
        LOGGER.debug("Accepting passenger with id {}",passenger.getUserId());
        passenger.setPassengerState(Passenger.PassengerState.ACCEPTED);
        em.merge(passenger);
        return true;
    }

    @Override
    public boolean rejectPassenger(Passenger passenger) {
        LOGGER.debug("Rejecting passenger with id {}",passenger.getUserId());
        passenger.setPassengerState(Passenger.PassengerState.REJECTED);
        em.merge(passenger);
        return true;
    }

    @Override
    public List<Passenger> getAcceptedPassengers(TripInstance tripInstance) {
        return getAcceptedPassengers(tripInstance.getTrip(),tripInstance.getDateTime());
    }

    @Override
    public List<Passenger> getAcceptedPassengers(Trip trip, LocalDateTime dateTime) {
        return getAcceptedPassengers(trip,dateTime,dateTime);
    }

    @Override
    public List<Passenger> getAcceptedPassengers(Trip trip, LocalDateTime startDateTime, LocalDateTime endDateTime){
        LOGGER.debug("Looking for the passengers of the trip with id {}, between '{}' and '{}', in the database",trip.getTripId(),startDateTime,endDateTime);
        //TODO: nose si sera "true"
        TypedQuery<Passenger> query = em.createQuery("from Passenger p WHERE p.passengerState = 'ACCEPTED' AND p.trip = :trip AND ((p.startDateTime<=:startDate AND p.endDateTime>=:startDate) OR (p.startDateTime<= :endDate AND p.endDateTime= :endDate) OR (p.startDateTime >= :startDate AND p.endDateTime <= :endDate))",Passenger.class);
        query.setParameter("trip",trip);
        query.setParameter("startDate", startDateTime);
        query.setParameter("endDate",endDateTime);
        List<Passenger> result = query.getResultList();
        LOGGER.debug("Found {} in the database", result);
        return result;
    }

    @Override
    public Optional<Passenger> getPassenger(Trip trip, User user) {
        return getPassenger(trip.getTripId(),user);
    }

    @Override
    public Optional<Passenger> getPassenger(long tripId, User user) {
        LOGGER.debug("Looking for the passenger with id {} of the trip with id {} in the database",user.getUserId(),tripId);
        TypedQuery<Passenger> query = em.createQuery("from Passenger p WHERE p.trip.tripId = :tripId AND p.user.userId = :userId",Passenger.class);
        query.setParameter("tripId",tripId);
        query.setParameter("userId",user.getUserId());
        Optional<Passenger> result = query.getResultList().stream().findFirst();
        LOGGER.debug("Found {} in the database", result.isPresent() ? result.get() : "nothing");
        return result;
    }

    @Override
    public void truncatePassengerEndDateTime(Passenger passenger, LocalDateTime newLastDateTime){
        if(passenger.getEndDateTime().isBefore(newLastDateTime)){
            throw new IllegalArgumentException();
        }
        passenger.setEndDateTime(newLastDateTime);
        em.merge(passenger);
    }

    @Override
    public PagedContent<TripInstance> getTripInstances(Trip trip, int page, int pageSize) {
        return getTripInstances(trip,page,pageSize,trip.getStartDateTime(),trip.getEndDateTime());
    }

    @Override
    public PagedContent<TripInstance> getTripInstances(Trip trip, int page, int pageSize, LocalDateTime start, LocalDateTime end) {
        LOGGER.debug("Looking for the trip instances of the trip with id {}, between '{}' and '{}', in page {} with size {} in the database",trip.getTripId(),start,end,page,pageSize);
        Query countQuery = em.createNativeQuery("SELECT count(*)" +
                "FROM generate_series(:start,:stop, '7 day'::interval)");
        countQuery.setParameter("start", Timestamp.valueOf(start));
        countQuery.setParameter("stop",Timestamp.valueOf(end));
        @SuppressWarnings("unchecked")
        //Obtenemos el total
        Integer total = ((List<Object>) countQuery.getResultList()).stream().map(elem -> ((Number) elem).intValue()).findFirst().orElseThrow(IllegalStateException::new);
        Query query = em.createNativeQuery("SELECT days.days, count(user_id) as passenger_count " +
                "FROM generate_series(:start,:stop, '7 day'::interval) days LEFT OUTER JOIN passengers ON passengers.start_date<=days.days AND passengers.end_date>=days.days AND passengers.trip_id= :tripId " +
                "GROUP BY days.days");
        query.setParameter("tripId",trip.getTripId());
        query.setParameter("start",Timestamp.valueOf(start));
        query.setParameter("stop",Timestamp.valueOf(end));
        query.setMaxResults(pageSize);//Offset
        query.setFirstResult(page*pageSize);//Limit
        @SuppressWarnings("unchecked") //esto es propenso a explotar
        List<TripInstance> instances = ((List<Object[]>) query.getResultList()).stream().map(res -> new TripInstance((LocalDateTime) res[0],trip,( (Number) res[1]).intValue())).collect(Collectors.toList());
        return new PagedContent<>(instances,page,pageSize,total);
    }

    private List<Trip> getTripsWithIds(List<Long> tripsIds){
        if(tripsIds.isEmpty()){
            return new ArrayList<>();
        }
        TypedQuery<Trip> query = em.createQuery("from Trip WHERE tripId IN :ids ORDER BY endDateTime DESC, time ASC ",Trip.class);
        query.setParameter("ids",tripsIds);
        return query.getResultList();
    }
    private List<Trip> getTripsForPassengerWithIds(List<Long> tripsIds, User passenger){
        if(tripsIds.isEmpty()){
            return new ArrayList<>();
        }
        TypedQuery<Passenger> query = em.createQuery("from Passenger WHERE trip.tripId IN :ids and user = :passenger ORDER BY endDateTime desc , trip.time ASC ",Passenger.class);
        query.setParameter("ids",tripsIds);
        query.setParameter("passenger",passenger);
        //Obtenemos el viaje pero con los limites seteados para el pasajero
        return query.getResultList().stream().map(Passenger::getTrip).collect(Collectors.toList());
    }
    private PagedContent<Trip> getTripPagedContent(int page, int pageSize, Query countQuery, Query idQuery) {
        @SuppressWarnings("unchecked")
        Integer total = ((List<Object>) countQuery.getResultList()).stream().map(elem -> ((Number) elem).intValue()).findFirst().orElseThrow(IllegalStateException::new);
        @SuppressWarnings("unchecked")
        List<Long> ids = ((List<Object>) idQuery.getResultList()).stream().map(elem -> ((Number) elem).longValue()).collect(Collectors.toList());
        List<Trip> result = getTripsWithIds(ids);
        LOGGER.debug("Found {} in the database", result);
        return new PagedContent<>(result,page,pageSize,total);
    }
    @Override
    public PagedContent<Trip> getTripsCreatedByUser(User user, Optional<LocalDateTime> minDateTime, Optional<LocalDateTime> maxDateTime, int page, int pageSize) {
        LOGGER.debug("Looking for the trips created by the user with id {}, between '{}' and '{}', in page {} with size {} in the database",user.getUserId(),minDateTime,maxDateTime,page,pageSize);
        //String to get total
        String queryString = "FROM trips " +
                "WHERE driver_id = :driverId ";
        if(minDateTime.isPresent()){
            queryString += "AND ((deleted = false AND end_date_time >= :min) OR (deleted = true AND last_occurrence >= :min)) ";
        }
        if(maxDateTime.isPresent()){
            queryString += "AND ((deleted = false AND end_date_time <= :max ) OR (deleted = true AND last_occurrence < :max)) ";
        }
        queryString+= "ORDER BY end_date_time DESC, cast(start_date_time as time) ASC ";
        Query countQuery = em.createNativeQuery( "SELECT count(distinct trip_id) FROM(SELECT trip_id "+ queryString + ")aux ");
        Query idQuery = em.createNativeQuery("SELECT trip_id " + queryString);
        countQuery.setParameter("driverId",user.getUserId());
        idQuery.setParameter("driverId",user.getUserId());
        minDateTime.ifPresent(dateTime -> {
            countQuery.setParameter("min", dateTime);
            idQuery.setParameter("min", dateTime);
        });
        maxDateTime.ifPresent(dateTime -> {
            countQuery.setParameter("max",dateTime);
            idQuery.setParameter("max",dateTime);
        });
        idQuery.setMaxResults(pageSize);//Offset
        idQuery.setFirstResult(page*pageSize);//Limit
        return getTripPagedContent(page, pageSize, countQuery, idQuery);
    }

    @Override
    public PagedContent<Trip> getTripsWhereUserIsPassenger(User user, Optional<LocalDateTime> minDateTime, Optional<LocalDateTime> maxDateTime, Passenger.PassengerState passengerState, int page, int pageSize) {
        LOGGER.debug("Looking for the trips where the user with id {} is passenger, between '{}' and '{}', in page {} with size {} in the database",user.getUserId(),minDateTime,maxDateTime,page,pageSize);
        String queryString = " FROM passengers p NATURAL JOIN trips "+
                "WHERE p.user_id = :passengerId ";
        if(minDateTime.isPresent()){
            queryString += "AND p.end_date >= :min ";
        }
        if(maxDateTime.isPresent()){
            queryString += "AND p.end_date <= :max ";
        }
        if(passengerState != null) {
            queryString += "AND p.passenger_state = :state ";
        }
        queryString += "ORDER BY p.end_date DESC, cast(trips.start_date_time as time) ASC ";
        Query countQuery = em.createNativeQuery( "SELECT count(distinct trip_id) FROM (SELECT trip_id "+ queryString+ ")aux");
        Query idQuery = em.createNativeQuery("SELECT trip_id " + queryString);
        countQuery.setParameter("passengerId",user.getUserId());
        idQuery.setParameter("passengerId",user.getUserId());
        minDateTime.ifPresent(dateTime -> {
            countQuery.setParameter("min", dateTime);
            idQuery.setParameter("min", dateTime);
        });
        maxDateTime.ifPresent(dateTime -> {
            countQuery.setParameter("max",dateTime);
            idQuery.setParameter("max",dateTime);
        });
        if(passengerState != null) {
            countQuery.setParameter("state", passengerState.name());
            idQuery.setParameter("state", passengerState.name());
        }
        idQuery.setMaxResults(pageSize);//Offset
        idQuery.setFirstResult(page*pageSize);//Limit
        @SuppressWarnings("unchecked")
        Integer total = ((List<Object>) countQuery.getResultList()).stream().map(elem -> ((Number) elem).intValue()).findFirst().orElseThrow(IllegalStateException::new);
        @SuppressWarnings("unchecked")
        List<Long> ids = ((List<Object>) idQuery.getResultList()).stream().map(elem -> ((Number) elem).longValue()).collect(Collectors.toList());
        List<Trip> result = getTripsForPassengerWithIds(ids,user);
        LOGGER.debug("Found {} in the database", result);
        return new PagedContent<>(result,page,pageSize,total);
    }

    @Override
    public int getTripSeatCount(long tripId, LocalDateTime startDateTime, LocalDateTime endDateTime){
        int ans = 0;
        if(startDateTime.compareTo(endDateTime)>0){
            return ans;
        }
        for(LocalDateTime dateTime = startDateTime; dateTime.compareTo(endDateTime)<=0;dateTime = dateTime.plusDays(7)){
            Query countQuery = em.createNativeQuery("SELECT coalesce(count(user_id),0) as passenger_count FROM passengers WHERE trip_id= :tripId AND start_date <= :dateTime AND end_date>= :dateTime AND passenger_state = 'ACCEPTED'");
            countQuery.setParameter("tripId",tripId);
            countQuery.setParameter("dateTime",Timestamp.valueOf(dateTime));
            @SuppressWarnings("unchecked")
            int aux = ((List<Object>)countQuery.getResultList()).stream().map(elem-> ((Number) elem).intValue()).findFirst().orElse(0);
            ans = Math.max(aux,ans);
        }
        return ans;
    }
    @Override
    public Optional<Trip> findById(long tripId) {
        LOGGER.debug("Looking for the trip with id {} in the database", tripId);
        TypedQuery<Trip> query = em.createQuery("from Trip t WHERE t.tripId = :tripId",Trip.class);
        query.setParameter("tripId",tripId);
        final Optional<Trip> result = query.getResultList().stream().findFirst();
        LOGGER.debug("Found {} in the database", result.isPresent() ? result.get() : "nothing");
        if(result.isPresent()){
            int occupiedSeats = getTripSeatCount(tripId,result.get().getStartDateTime(),result.get().getEndDateTime());
            result.get().setOccupiedSeats(occupiedSeats);
        }
        return result;
    }

    @Override
    public Optional<Trip> findById(long tripId, LocalDateTime start, LocalDateTime end) {
        final Optional<Trip> result = findById(tripId);
        //Aprovechamos que el valor de los asientos ocupados se saca cuando se pide
        //Entonces lo cambiamos en la instancia para que se pida con los valores correctos
        result.ifPresent(res->{
            res.setQueryStartDateTime(start);
            res.setQueryEndDateTime(end);
            res.setOccupiedSeats(getTripSeatCount(tripId,start,end));
        });
        return result;
    }

    @Override
    public PagedContent<Trip> getTripsWithFilters(
            long origin_city_id, long destination_city_id,
            LocalDateTime startDateTime, Optional<DayOfWeek> dayOfWeek, Optional<LocalDateTime> endDateTime, int minutes,
            Optional<BigDecimal> minPrice, Optional<BigDecimal> maxPrice, Trip.SortType sortType, boolean descending,
            long searchUserId, List<FeatureCar> carFeatures, int page, int pageSize){
        return getTripsWithFilters(origin_city_id,destination_city_id,startDateTime,dayOfWeek.get(),endDateTime.get(),minutes,minPrice,maxPrice,sortType,descending,searchUserId,carFeatures,page,pageSize);
    }

    @Override
    public PagedContent<Trip> getTripsWithFilters(long origin_city_id, long destination_city_id,
                                                  LocalDateTime startDateTime, DayOfWeek dayOfWeek, LocalDateTime endDateTime, int minutes,
                                                  Optional<BigDecimal> minPrice, Optional<BigDecimal> maxPrice, Trip.SortType sortType, boolean descending,
                                                  long searchUserId, List<FeatureCar> carFeatures, int page, int pageSize) {
        LOGGER.debug("Looking for the trips with originCity with id {}, destinationCity with id {}, startDateTime '{}',dayOfWeek {}, endDateTime {} range of {} minutes,{}{} sortType '{}' ({}) page {} and size {} in the database",
                origin_city_id, destination_city_id, startDateTime, dayOfWeek,
                endDateTime, minutes,
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
        Map<String,Object> arguments = new HashMap<>();
        String queryString = " FROM trips trips NATURAL LEFT OUTER JOIN LATERAL( "+
                "SELECT trips.trip_id as trip_id,days.days, count(passengers.user_id) as passenger_count " +
                "FROM generate_series(trips.start_date_time,trips.end_date_time, interval'7 day') days LEFT OUTER JOIN passengers ON passengers.trip_id = trips.trip_id AND passengers.start_date<=days.days AND passengers.end_date>=days.days AND passengers.passenger_state = 'ACCEPTED' "+
                "GROUP BY days.days,passengers.trip_id) aux "+
                "LEFT JOIN (SELECT coalesce(avg(user_reviews.rating),0) as driver_rating, reviewed_id as driver_id FROM user_reviews JOIN driver_reviews ON user_reviews.review_id = driver_reviews.review_id GROUP BY user_reviews.reviewed_id ) driver_rating ON driver_rating.driver_id = trips.driver_id "+
                "LEFT JOIN (SELECT coalesce(avg(car_reviews.rating),0) as car_rating, car_id as car_id FROM car_reviews GROUP BY car_id) car_rating ON car_rating.car_id = trips.car_id "+
                "LEFT JOIN blocks AS b1 ON trips.driver_id = b1.blockedid AND b1.blockedbyid = :searchUserId " +
                "LEFT JOIN blocks AS b2 ON trips.driver_id = b2.blockedbyid AND b2.blockedid = :searchUserId " +
                "WHERE aux.days >= :startDateTime AND end_date_time >= :startDateTime AND  origin_city_id = :originCityId AND cast(trips.start_date_time as time) >= :minTime AND trips.deleted = false AND start_date_time <= :startMaximum " +
                "AND cast(trips.start_date_time as time) <= :maxTime AND destination_city_id = :destinationCityId AND aux.days <= :endDateTimePlus AND end_date_time >= :endDateTimeMinus "+
                "AND b1.blockedid IS NULL AND b2.blockedbyid IS NULL " +
                "AND trips.day_of_week = :dayOfWeek ";
        arguments.put("startMaximum",Timestamp.valueOf(startDateTime.plusMinutes(minutes)));
        arguments.put("startDateTime",Timestamp.valueOf(startDateTime.minusMinutes(minutes)));
        arguments.put("originCityId",origin_city_id);
        arguments.put("minTime",minTime);
        arguments.put("maxTime",maxTime);
        arguments.put("destinationCityId",destination_city_id);
        arguments.put("endDateTimePlus",Timestamp.valueOf(endDateTime.plusMinutes(minutes)));
        arguments.put("endDateTimeMinus", Timestamp.valueOf(endDateTime.minusMinutes(minutes)));
        arguments.put("dayOfWeek",dayOfWeek.getValue());
        arguments.put("searchUserId", searchUserId);
        if(startDateTime.toLocalDate().equals(LocalDate.now())){
            queryString += "AND cast (trips.start_date_time as time) >= :auxTime ";
            arguments.put("auxTime",Timestamp.valueOf(LocalDateTime.now()));
        }
        if(minPrice.isPresent()){
            queryString += "AND trips.price >= :minPrice ";
            arguments.put("minPrice",minPrice.get().doubleValue());
        }
        if(maxPrice.isPresent()){
            queryString += "AND trips.price <= :maxPrice ";
            arguments.put("maxPrice",maxPrice.get().doubleValue());
        }
        if(carFeatures != null && !carFeatures.isEmpty()) {
            for (int i = 0; i < carFeatures.size(); i++) {
                queryString += "AND trips.car_id IN (SELECT car_car_id FROM car_features WHERE features = :carFeature" + i + ") ";
                arguments.put("carFeature" + i, carFeatures.get(i).name());
            }
        }
        queryString += " GROUP BY trips.trip_id, trips.max_passengers, trips.price, driver_rating.driver_rating, car_rating.car_rating "+
                "HAVING coalesce(max(aux.passenger_count),0)<trips.max_passengers ";

        if(sortType.equals(Trip.SortType.PRICE)){
            queryString += "ORDER BY trips.price " + (descending?"DESC":"ASC") +", cast(trips.start_date_time as time) ASC ";
        }else if(sortType.equals(Trip.SortType.TIME)){
            queryString += "ORDER BY cast(trips.start_date_time as time) " + (descending?"DESC":"ASC") + ", trips.price ASC";
        }else if(sortType.equals(Trip.SortType.DRIVER_RATING)){
            queryString += "ORDER BY coalesce(driver_rating.driver_rating,0) DESC, trips.price DESC";
        }else if(sortType.equals(Trip.SortType.CAR_RATING)){
            queryString += "ORDER BY coalesce(car_rating.car_rating,0) DESC, trips.price DESC";
        }
        Query countQuery = em.createNativeQuery( "SELECT coalesce(sum(trip_count),0) FROM(SELECT count(distinct trip_id) as trip_count "+ queryString + ")aux ");
        Query idQuery = em.createNativeQuery("SELECT trip_id " + queryString);
        for(Map.Entry<String,Object> entry : arguments.entrySet()){
            countQuery.setParameter(entry.getKey(),entry.getValue());
            idQuery.setParameter(entry.getKey(),entry.getValue());
        }
        idQuery.setMaxResults(pageSize);//Offset
        idQuery.setFirstResult(page*pageSize);//Limit
        @SuppressWarnings("unchecked")
        Integer total = ((List<Object>) countQuery.getResultList()).stream().map(elem -> ((Number) elem).intValue()).findFirst().orElseThrow(IllegalStateException::new);
        @SuppressWarnings("unchecked")
        List<Long> ids = ((List<Object>) idQuery.getResultList()).stream().map(elem -> ((Number) elem).longValue()).collect(Collectors.toList());
        List<Trip> result = new ArrayList<>();
        if(!ids.isEmpty()){
            String JPLQuery = "from Trip WHERE tripId IN :ids ";
            if(sortType.equals(Trip.SortType.PRICE)){
                JPLQuery += "order by price " + (descending?"DESC":"ASC") +", time ASC ";
            }else if(sortType.equals(Trip.SortType.TIME)){
                JPLQuery += "ORDER BY time " + (descending?"DESC":"ASC") + ", price ASC";
            }else if(sortType.equals(Trip.SortType.DRIVER_RATING)){
                JPLQuery += "order by driverRating DESC, price ASC";
            }else if(sortType.equals(Trip.SortType.CAR_RATING)){
                JPLQuery += "order by carRating DESC, price ASC";
            }
            TypedQuery<Trip> query = em.createQuery(JPLQuery,Trip.class);
            query.setParameter("ids",ids);
            result = query.getResultList();
        }
        LOGGER.debug("Found {} in the database", result);
        PagedContent<Trip> ans = new PagedContent<>(result,page,pageSize,total);
        for(Trip trip : ans.getElements()){
            trip.setQueryStartDateTime(startDateTime.toLocalDate().atTime(trip.getStartDateTime().toLocalTime()));
            trip.setQueryEndDateTime(endDateTime.toLocalDate().atTime(trip.getEndDateTime().toLocalTime()));
        }
        return ans;
    }

    @Override
    public PagedContent<Trip> getTripsByOriginAndStart(long origin_city_id, LocalDateTime startDateTime, long searchUserId, int page, int pageSize) {
        LOGGER.debug("Looking for the trips with originCity with id {} and startDateTime '{}' in page {} with size {} in the database",origin_city_id,startDateTime,page,pageSize);
        String queryString = " FROM trips trips NATURAL LEFT OUTER JOIN LATERAL(  "+
                "SELECT trips.trip_id as trip_id,days.days, count(passengers.user_id) as passenger_count " +
                "FROM generate_series(trips.start_date_time,trips.end_date_time, interval'7 day') days LEFT OUTER JOIN passengers ON passengers.trip_id = trips.trip_id AND passengers.start_date<=days.days AND passengers.end_date>=days.days AND passengers.passenger_state = 'ACCEPTED' "+
                "GROUP BY days.days,passengers.trip_id) aux "+
                "LEFT JOIN blocks AS b1 ON trips.driver_id = b1.blockedid AND b1.blockedbyid = :searchUserId " +
                "LEFT JOIN blocks AS b2 ON trips.driver_id = b2.blockedbyid AND b2.blockedid = :searchUserId " +
                "WHERE origin_city_id = :originCityId AND day_of_week = :dayOfWeek AND end_date_time >= :startDateTime AND trips.deleted = false AND cast(start_date_time as date) <= :startDate AND cast(start_date_time as time) >= :startTime " +
                "AND b1.blockedid IS NULL AND b2.blockedbyid IS NULL " +
                "GROUP BY trips.trip_id, trips.max_passengers, trips.price "+
                "HAVING coalesce(max(aux.passenger_count),0)<trips.max_passengers " +
                "ORDER BY cast(trips.start_date_time as time) ASC, trips.price ASC ";
        Query countQuery = em.createNativeQuery( "SELECT coalesce(sum(trip_count),0) FROM(SELECT count( distinct trip_id) as trip_count "+ queryString + ")aux" );
        Query idQuery = em.createNativeQuery("SELECT trip_id " + queryString);
        countQuery.setParameter("startTime",startDateTime.toLocalTime());
        countQuery.setParameter("startDate",startDateTime.toLocalDate());
        countQuery.setParameter("originCityId",origin_city_id);
        countQuery.setParameter("dayOfWeek",startDateTime.getDayOfWeek().getValue());
        countQuery.setParameter("startDateTime",Timestamp.valueOf(startDateTime));
        countQuery.setParameter("searchUserId", searchUserId);
        idQuery.setParameter("originCityId",origin_city_id);
        idQuery.setParameter("dayOfWeek",startDateTime.getDayOfWeek().getValue());
        idQuery.setParameter("startDateTime",Timestamp.valueOf(startDateTime));
        idQuery.setParameter("startTime",startDateTime.toLocalTime());
        idQuery.setParameter("startDate",startDateTime.toLocalDate());
        idQuery.setParameter("searchUserId", searchUserId);
        idQuery.setMaxResults(pageSize);//Offset
        idQuery.setFirstResult(page*pageSize);//Limit
        @SuppressWarnings("unchecked")
        Integer total = ((List<Object>) countQuery.getResultList()).stream().map(elem -> ((Number) elem).intValue()).findFirst().orElseThrow(IllegalStateException::new);
        @SuppressWarnings("unchecked")
        List<Long> ids = ((List<Object>) idQuery.getResultList()).stream().map(elem -> ((Number) elem).longValue()).collect(Collectors.toList());
        List<Trip> aux = new ArrayList<>();
        if(!ids.isEmpty()){
            TypedQuery<Trip> query = em.createQuery("from Trip WHERE tripId IN :ids ORDER BY time ASC, price ASC",Trip.class);
            query.setParameter("ids",ids);
            aux =  query.getResultList();
        }
        LOGGER.debug("Found {} in the database", aux);
        PagedContent<Trip> ans =  new PagedContent<>(aux,page,pageSize,total);
        for(Trip trip : ans.getElements()){
            trip.setQueryStartDateTime(startDateTime.toLocalDate().atTime(trip.getStartDateTime().toLocalTime()));
            trip.setQueryEndDateTime(startDateTime.toLocalDate().atTime(trip.getStartDateTime().toLocalTime()));
        }
        return ans;
    }
}
