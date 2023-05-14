package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.exceptions.TripAlreadyStartedException;
import ar.edu.itba.paw.interfaces.persistence.TripDao;
import ar.edu.itba.paw.interfaces.services.TripService;
import ar.edu.itba.paw.interfaces.services.EmailService;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.trips.Trip;
import ar.edu.itba.paw.models.trips.TripInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class TripServiceImpl implements TripService {

    private static final int OFFSET_MINUTES = 30;

    private final EmailService emailService;

    private final TripDao tripDao;

    @Autowired
    public TripServiceImpl(final TripDao tripDao, EmailService emailService1){
        this.tripDao = tripDao;
        this.emailService = emailService1;
    }

    @Transactional
    @Override
    public Trip createTrip(final City originCity, final String originAddress, final City destinationCity, final String destinationAddress, final Car car, final LocalDate startDate, final LocalTime startTime,final BigDecimal price, final int maxSeats, User driver, final LocalDate endDate, final LocalTime endTime) {
        //Usamos que el front debe pasar el date en ISO-8601
        LocalDateTime startDateTime = startDate.atTime(startTime);
        //If Trip is not recurrent, then endDateTime is the same as startDateTime
        LocalDateTime endDateTime = (endDate != null) ? endDate.atTime(endTime) : startDateTime;
        if(!startDateTime.getDayOfWeek().equals(endDateTime.getDayOfWeek())
        || startDateTime.isAfter(endDateTime)
        || originCity == null || destinationCity == null
        || car == null || driver == null
        || maxSeats<=0 || price.doubleValue()<0){
            throw new IllegalArgumentException();
        }
        Trip newTrip = tripDao.create(
                originCity,
                originAddress,
                destinationCity,
                destinationAddress,
                car,
                startDateTime,
                endDateTime,
                !startDateTime.equals(endDateTime),
                price.doubleValue(),
                maxSeats,
                driver
        );
        try {
            emailService.sendMailNewTrip(newTrip);
        }
        catch( Exception e){
            e.printStackTrace();
        }
        return newTrip;
    }
    @Transactional
    @Override
    public Trip createTrip(final City originCity, final String originAddress, final City destinationCity, final String destinationAddress, final Car car, final LocalDate date, final LocalTime time,final BigDecimal price, final int maxSeats, User driver){
        return createTrip(originCity,originAddress,destinationCity,destinationAddress,car,date,time,price,maxSeats,driver,date,time);
    }
    private Optional<LocalDateTime> getLocalDateTime(final String date, final String time){
        if(date == null || time == null || date.length()==0 || time.length()==0){
            return Optional.empty();
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime ans;
        try{
            String[] timeTokens = time.split(":");
            ans = LocalDate.parse(date, formatter).atTime(Integer.parseInt(timeTokens[0]),Integer.parseInt(timeTokens[1]));
        }catch (Exception e){
            return Optional.empty();
        }
        return Optional.of(ans);
    }
    private Optional<LocalDateTime> getIsoLocalDateTime(final String date, final String time){
        if(date == null || time == null || date.length()==0 || time.length()==0){
            return Optional.empty();
        }
        LocalDateTime ans;
        try{
            String[] timeTokens = time.split(":");
            ans = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE).atTime(Integer.parseInt(timeTokens[0]),Integer.parseInt(timeTokens[1]));
        }catch (Exception e){
            return Optional.empty();
        }
        return Optional.of(ans);
    }
    private static void validatePageAndSize(int page, int pageSize){
        if(page<0 || pageSize<0) throw new IllegalArgumentException();
    }
    @Transactional
    public boolean deleteTrip(final Trip trip){
        List<Passenger> tripPassengers = tripDao.getPassengers(trip,trip.getStartDateTime(),trip.getEndDateTime());
        //Notify passengers that trip was deleted
        for(Passenger passenger : tripPassengers){
            try{
                emailService.sendMailTripDeletedToPassenger(trip,passenger);
            }catch (Exception e){
                e.printStackTrace();
                throw new IllegalStateException();
            }
        }
        try{
            emailService.sendMailTripDeletedToDriver(trip);
        }catch (Exception e){
            e.printStackTrace();
            throw new IllegalStateException();
        }
        return tripDao.deleteTrip(trip);
    }
    public boolean deleteTrip(int tripId){
        //TODO: change for TripNotFoundException
        Trip tripToDelete = tripDao.findById(tripId).orElseThrow(IllegalArgumentException::new);
        return deleteTrip(tripToDelete);
    }
    @Override
    public boolean addPassenger(Trip trip, User passenger, LocalDateTime dateTime) throws TripAlreadyStartedException{
        return addPassenger(trip,passenger,dateTime,dateTime);
    }
    @Transactional
    @Override
    public boolean addPassenger(Trip trip,User passenger, String startDate,String startTime, String endDate) throws TripAlreadyStartedException{
        LocalDateTime startDateTime = getIsoLocalDateTime(startDate,startTime).get();
        LocalDateTime endDateTime = getIsoLocalDateTime(endDate,startTime).orElse(startDateTime);
        return addPassenger(trip,passenger,startDateTime,endDateTime);
    }
    @Override
    public boolean addPassenger(Trip trip, User user, LocalDateTime startDateTime, LocalDateTime endDateTime) throws TripAlreadyStartedException {
        if(trip==null || user==null || startDateTime == null || endDateTime == null){
            throw new IllegalArgumentException();
        }
        Passenger passenger = new Passenger(user,startDateTime,endDateTime);
        List<Passenger> passengers = tripDao.getPassengers(trip,trip.getStartDateTime(),trip.getEndDateTime());
        if(passengers.contains(passenger)){
            throw new IllegalStateException();
        }
        Trip aux = tripDao.findById(trip.getTripId(),startDateTime,endDateTime).orElseThrow(IllegalArgumentException::new);
        if(aux.getOccupiedSeats()>=trip.getMaxSeats()){
            throw new IllegalStateException();
        }
        if(startDateTime.isBefore(LocalDateTime.now())){
            throw new TripAlreadyStartedException();
        }
        if(     startDateTime.isAfter(endDateTime) || trip.getStartDateTime().isAfter(startDateTime)
            || trip.getEndDateTime().isBefore(endDateTime) || !trip.getStartDateTime().getDayOfWeek().equals(startDateTime.getDayOfWeek())
            || !trip.getEndDateTime().getDayOfWeek().equals(endDateTime.getDayOfWeek()) || endDateTime.isBefore(startDateTime)
            || trip.getDriver().equals(user)
            || !startDateTime.toLocalTime().equals(trip.getStartDateTime().toLocalTime()) || !endDateTime.toLocalTime().equals(trip.getEndDateTime().toLocalTime())){
            throw new IllegalArgumentException();
        }
        try{
            emailService.sendMailNewPassenger(trip, passenger);
            emailService.sendMailTripConfirmation(trip, passenger);
        }
        catch( Exception e){
            //TODO: change for logging
            e.printStackTrace();
        }
        return tripDao.addPassenger(trip,passenger);
    }
    @Transactional
    @Override
    public boolean addPassenger(long tripId, User passenger, LocalDateTime startDateTime, LocalDateTime endDateTime) throws TripAlreadyStartedException{
        Optional<Trip> trip = findById(tripId);
        if(!trip.isPresent()){
            throw new IllegalStateException();
        }
        //Ignorar suggestion, usar filter no tiene mucho sentido aca (funciona porque devuelve boolean)
        //Trip trip = findById(tripId);
        return addPassenger(trip.get()  ,passenger,startDateTime,endDateTime);
    }
    @Override
    public boolean addPassenger(long tripId, User passenger, LocalDateTime dateTime) throws TripAlreadyStartedException{
        return addPassenger(tripId,passenger,dateTime,dateTime);
    }

    @Transactional
    @Override
    public boolean removePassenger(final Trip trip, final User user){
        if(trip == null || user == null){
            throw new IllegalArgumentException();
        }
        Passenger passenger = tripDao.getPassenger(trip,user).orElseThrow(IllegalStateException::new);
        try{
            emailService.sendMailTripCancelledToDriver(trip,passenger);
        }catch (Exception e){
            e.printStackTrace();
        }
        return tripDao.removePassenger(trip,passenger);
    }

    @Override
    public Optional<Trip> findById(long id) {
        return tripDao.findById(id);
    }
    @Override
    public Optional<Trip> findById(long id, String startDate, String startTime, String endDate){
        LocalDateTime start = getIsoLocalDateTime(startDate,startTime).orElseThrow(IllegalArgumentException::new);
        LocalDateTime end = getIsoLocalDateTime(endDate,startTime).orElseThrow(IllegalArgumentException::new);
        return findById(id,start,end);
    }
    @Override
    public Optional<Trip> findById(long id,LocalDateTime start, LocalDateTime end){
        return tripDao.findById(id,start,end);
    }
    @Override
    public Optional<Trip> findById(long id, LocalDateTime dateTime){
        return tripDao.findById(id,dateTime,dateTime);
    }
    @Override
    public boolean userIsDriver(final long tripId, final User user){
        //TODO: cambiar a excepciones conocidas
        final Trip trip = tripDao.findById(tripId).orElseThrow(IllegalArgumentException::new);
        return trip.getDriver().equals(user);
    }
    @Override
    public boolean userIsPassenger(final long tripId, final User user){
        return tripDao.getPassenger(tripId,user).isPresent();
    }
    @Override
    public Optional<Passenger> getPassenger(final Trip trip, final User user){
        return tripDao.getPassenger(trip,user);
    }
    @Override
    public Optional<Passenger> getPassenger(final long tripId, final User user){
        return tripDao.getPassenger(tripId,user);
    }
    @Override
    public List<Passenger> getPassengers(Trip trip, LocalDateTime dateTime){
        if( trip.getStartDateTime().isAfter(dateTime)
                || trip.getEndDateTime().isBefore(dateTime)
                || Period.between(trip.getStartDateTime().toLocalDate(),dateTime.toLocalDate()).getDays()%7!=0
        ){
            throw new IllegalArgumentException();
        }
        return tripDao.getPassengers(trip,dateTime);
    }
    @Override
    public List<Passenger> getPassengersRecurrent(Trip trip, LocalDateTime startDate, LocalDateTime endDate){
        if( trip.getStartDateTime().isAfter(startDate)
                || trip.getEndDateTime().isBefore(startDate)
                || Period.between(trip.getStartDateTime().toLocalDate(),startDate.toLocalDate()).getDays()%7!=0
        ){
            throw new IllegalArgumentException();
        }
        return tripDao.getPassengers(trip,startDate,endDate);
    }
    @Override
    public List<Passenger> getPassengers(Trip trip){
        return tripDao.getPassengers(trip,trip.getStartDateTime(),trip.getEndDateTime());
    }
    @Override
    public List<Passenger> getPassengers(TripInstance tripInstance){
        return tripDao.getPassengers(tripInstance);
    }
    @Override
    public PagedContent<TripInstance> getTripInstances(final Trip trip, int page, int pageSize){
        validatePageAndSize(page,pageSize);
        return tripDao.getTripInstances(trip,page,pageSize);
    }
    @Override
    public PagedContent<TripInstance> getTripInstances(final Trip trip, int page, int pageSize, LocalDateTime start, LocalDateTime end){
        validatePageAndSize(page,pageSize);
        if(start.isBefore(trip.getStartDateTime()) || end.isAfter(trip.getEndDateTime())
         || !start.getDayOfWeek().equals(end.getDayOfWeek()) || !start.getDayOfWeek().equals(trip.getStartDateTime().getDayOfWeek())){
            throw new IllegalArgumentException();
        }
        return tripDao.getTripInstances(trip,page,pageSize,start,end);
    }
    @Override
    public PagedContent<Trip> getTripsCreatedByUserFuture(final User user, int page, int pageSize){
        validatePageAndSize(page,pageSize);
        return tripDao.getTripsCreatedByUser(user,Optional.of(LocalDateTime.now()),Optional.empty(),page,pageSize);
    }
    @Override
    public PagedContent<Trip> getTripsCreatedByUserPast(final User user, int page, int pageSize){
        validatePageAndSize(page,pageSize);
        return tripDao.getTripsCreatedByUser(user,Optional.empty(),Optional.of(LocalDateTime.now()),page,pageSize);
    }

    @Override
    public PagedContent<Trip> getTripsWhereUserIsPassengerFuture(final User user, int page, int pageSize){
        validatePageAndSize(page,pageSize);
        return tripDao.getTripsWhereUserIsPassenger(user,Optional.of(LocalDateTime.now()),Optional.empty(),page,pageSize);
    }
    @Override
    public PagedContent<Trip> getTripsWhereUserIsPassengerPast(final User user, int page, int pageSize){
        validatePageAndSize(page,pageSize);
        return tripDao.getTripsWhereUserIsPassenger(user,Optional.empty(),Optional.of(LocalDateTime.now()),page,pageSize);
    }

    @Override
    public PagedContent<Trip> getRecommendedTripsForUser(User user, int page, int pageSize){
        validatePageAndSize(page,pageSize);
        LocalDateTime start = LocalDateTime.now();
        return tripDao.getTripsByOriginAndStart(user.getBornCity().getId(),start,page,pageSize);
    }
    private Trip.SortType getTripSortType(final String sortType){
        try{
            Trip.SortType aux = Trip.SortType.valueOf(sortType.toUpperCase());
            return aux;
        }catch (Exception e){
            return Trip.SortType.PRICE;
        }
    }

    //TODO: sacar los optional que nunca se usan
    @Override
    public PagedContent<Trip> getTripsByDateTimeAndOriginAndDestinationAndPrice(
            long origin_city_id, long destination_city_id, final LocalDate startDate,
            final LocalTime startTime, final LocalDate endDate, final LocalTime endTime,
            final Optional<BigDecimal> minPrice, final Optional<BigDecimal> maxPrice, final String sortType, final boolean descending,
            final int page, final int pageSize){
        validatePageAndSize(page,pageSize);
        LocalDateTime startDateTime = startDate.atTime(startTime);
        LocalDateTime endDateTime = (endDate != null) ? endDate.atTime(endTime) : startDateTime;
        return tripDao.getTripsWithFilters(origin_city_id,destination_city_id,startDateTime,Optional.of(startDateTime.getDayOfWeek()),Optional.of(endDateTime),OFFSET_MINUTES,minPrice,maxPrice,getTripSortType(sortType),descending,page,pageSize);
    }

}
