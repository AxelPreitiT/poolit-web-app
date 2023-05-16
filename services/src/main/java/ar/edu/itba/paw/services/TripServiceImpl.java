package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.exceptions.TripAlreadyStartedException;
import ar.edu.itba.paw.interfaces.persistence.TripDao;
import ar.edu.itba.paw.interfaces.services.TripService;
import ar.edu.itba.paw.interfaces.services.EmailService;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.trips.Trip;
import ar.edu.itba.paw.models.trips.TripInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final static Logger LOGGER = LoggerFactory.getLogger(TripServiceImpl.class);

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
        LocalDateTime startDateTime = startDate.atTime(startTime);
        //If Trip is not recurrent, then endDateTime is the same as startDateTime
        LocalDateTime endDateTime = (endDate != null) ? endDate.atTime(endTime) : startDateTime;
        if(!startDateTime.getDayOfWeek().equals(endDateTime.getDayOfWeek())
        || startDateTime.isAfter(endDateTime)
        || originCity == null || destinationCity == null
        || car == null || driver == null
        || maxSeats<=0 || price.doubleValue()<0){
            IllegalArgumentException e = new IllegalArgumentException();
            LOGGER.error("Trip with originCity with id {}, originAddress '{}', destinationCity with id {}, destinationAddress '{}', car with id {}, startDateTime '{}', endDateTime '{}', price ${}, maxSeats {} and driver with id {} has invalid values",
                    originCity.getId(), originAddress, destinationCity.getId(), destinationAddress, car.getCarId(), startDateTime, endDateTime, price, maxSeats, driver.getUserId(), e);
            throw e;
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
            LOGGER.error("There was an error sending the email for the new trip with id {} to the driver with id {}", newTrip.getTripId(), driver.getUserId(), e);
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
            LOGGER.error("Error parsing date '{}' and time '{}'", date, time, e);
            return Optional.empty();
        }
        LOGGER.debug("Parsed date '{}' and time '{}' to LocalDateTime '{}'", date, time, ans);
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
            LOGGER.error("Error parsing date '{}' and time '{}'", date, time, e);
            return Optional.empty();
        }
        LOGGER.debug("Parsed date '{}' and time '{}' to LocalDateTime '{}'", date, time, ans);
        return Optional.of(ans);
    }
    private static void validatePageAndSize(int page, int pageSize){
        if(page<0 || pageSize<0) {
            IllegalArgumentException e = new IllegalArgumentException();
            LOGGER.error("Page {} and pageSize {} must be positive", page, pageSize, e);
            throw e;
        }
    }
    @Transactional
    public boolean deleteTrip(final Trip trip){
        //Si el viaje ya termino
        if(trip.getEndDateTime().isBefore(LocalDateTime.now())){
            RuntimeException e = new IllegalStateException();
            LOGGER.error("Driver {} tried deleting the trip with id {} after it ended", trip.getDriver().getUserId(), trip.getTripId(), e);
            throw e;
        }
        List<Passenger> tripPassengers = tripDao.getPassengers(trip,trip.getStartDateTime(),trip.getEndDateTime());
        //Notify passengers that trip was deleted
        for(Passenger passenger : tripPassengers){
            try{
                emailService.sendMailTripDeletedToPassenger(trip,passenger);
            }catch (Exception e){
                LOGGER.error("There was an error sending the email for the deleted trip with id {} to the passenger with id {}", trip.getTripId(), passenger.getUserId(), e);
                throw new IllegalStateException();
            }
        }
        try{
            emailService.sendMailTripDeletedToDriver(trip);
        }catch (Exception e){
            LOGGER.error("There was an error sending the email for the deleted trip with id {} to the driver with id {}", trip.getTripId(), trip.getDriver().getUserId(), e);
            throw new IllegalStateException();
        }
        return tripDao.deleteTrip(trip);
    }
    @Transactional
    public boolean deleteTrip(int tripId){
        Optional<Trip> tripToDelete = tripDao.findById(tripId);
        if(!tripToDelete.isPresent()){
            IllegalArgumentException e = new IllegalArgumentException();
            LOGGER.error("Trip with id {} cannot be deleted", tripId, e);
            throw e;
        }
        return deleteTrip(tripToDelete.get());
    }
    @Override
    @Transactional
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
    @Transactional
    @Override
    public boolean addPassenger(Trip trip, User user, LocalDateTime startDateTime, LocalDateTime endDateTime) throws TripAlreadyStartedException {
        if(trip==null || user==null || startDateTime == null || endDateTime == null){
            IllegalArgumentException e = new IllegalArgumentException();
            LOGGER.error("Trip {} or User {} or startDateTime '{}' or endDateTime '{}' cannot be null", trip, user, startDateTime, endDateTime, e);
            throw e;
        }
        Passenger passenger = new Passenger(user,startDateTime,endDateTime);
        List<Passenger> passengers = tripDao.getPassengers(trip,trip.getStartDateTime(),trip.getEndDateTime());
        if(passengers.contains(passenger)){
            IllegalStateException e = new IllegalStateException();
            LOGGER.error("Passenger with id {} is already in trip with id {}", passenger.getUserId(), trip.getTripId(), e);
            throw e;
        }
        Trip aux = tripDao.findById(trip.getTripId(),startDateTime,endDateTime).orElseThrow(IllegalArgumentException::new);
        if(aux.getOccupiedSeats()>=trip.getMaxSeats()){
            IllegalStateException e = new IllegalStateException();
            LOGGER.error("Trip with id {} is full", trip.getTripId(), e);
            throw e;
        }
        if(startDateTime.isBefore(LocalDateTime.now())){
            TripAlreadyStartedException e = new TripAlreadyStartedException();
            LOGGER.error("Trip with id {} already started", trip.getTripId(), e);
            throw e;
        }
        if(     startDateTime.isAfter(endDateTime) || trip.getStartDateTime().isAfter(startDateTime)
            || trip.getEndDateTime().isBefore(endDateTime) || !trip.getStartDateTime().getDayOfWeek().equals(startDateTime.getDayOfWeek())
            || !trip.getEndDateTime().getDayOfWeek().equals(endDateTime.getDayOfWeek()) || endDateTime.isBefore(startDateTime)
            || trip.getDriver().equals(user)
            || !startDateTime.toLocalTime().equals(trip.getStartDateTime().toLocalTime()) || !endDateTime.toLocalTime().equals(trip.getEndDateTime().toLocalTime())){
            IllegalArgumentException e = new IllegalArgumentException();
            LOGGER.error("{}, startDateTime '{}' or endDateTime '{}' have invalid values", trip, startDateTime, endDateTime, e);
            throw e;
        }
        try{
            emailService.sendMailNewPassenger(trip, passenger);
        }
        catch( Exception e){
            LOGGER.error("There was an error sending the email for the new passenger with id {} added to the trip with id {} to the driver with id {}", passenger.getUserId(), trip.getTripId(), trip.getDriver().getUserId(), e);
        }
        try {
            emailService.sendMailTripConfirmation(trip, passenger);
        }
        catch (Exception e) {
            LOGGER.error("There was an error sending the email for the new passenger with id {} added to the trip with id {} to the passenger with id {}", passenger.getUserId(), trip.getTripId(), passenger.getUserId(), e);
        }
        return tripDao.addPassenger(trip,passenger);
    }
    @Transactional
    @Override
    public boolean addPassenger(long tripId, User passenger, LocalDateTime startDateTime, LocalDateTime endDateTime) throws TripAlreadyStartedException{
        Optional<Trip> trip = findById(tripId);
        if(!trip.isPresent()){
            IllegalStateException e = new IllegalStateException();
            LOGGER.error("Trip with id {} not found", tripId, e);
            throw e;
        }
        return addPassenger(trip.get()  ,passenger,startDateTime,endDateTime);
    }
    @Transactional
    @Override
    public boolean addPassenger(long tripId, User passenger, LocalDateTime dateTime) throws TripAlreadyStartedException{
        return addPassenger(tripId,passenger,dateTime,dateTime);
    }

    @Transactional
    @Override
    public boolean removePassenger(final Trip trip, final User user){
        if(trip == null || user == null){
            IllegalArgumentException e = new IllegalArgumentException();
            LOGGER.error("Trip {} or User {} cannot be null", trip, user, e);
            throw e;
        }
        final Optional<Passenger> passengerOptional = tripDao.getPassenger(trip,user);
        if(!passengerOptional.isPresent()) {
            IllegalStateException e = new IllegalStateException();
            LOGGER.error("Passenger with id {} is not in trip with id {}", user.getUserId(), trip.getTripId(), e);
            throw e;
        }
        if(passengerOptional.get().getEndDateTime().isBefore(LocalDateTime.now())){
            IllegalStateException e = new IllegalStateException();
            LOGGER.error("Passenger with id {} tried to get out of trip {} after the period has ended", user.getUserId(), trip.getTripId(), e);
            throw e;
        }
        final Passenger passenger = passengerOptional.get();
        try{
            emailService.sendMailTripCancelledToDriver(trip,passenger);
        }catch (Exception e){
            LOGGER.error("There was an error sending the email for the cancelled trip with id {} by the passenger with id {} to the driver with id {}", trip.getTripId(), passenger.getUserId(), trip.getDriver().getUserId(), e);
        }
        return tripDao.removePassenger(trip,passenger);
    }

    @Override
    public Optional<Trip> findById(long id) {
        return tripDao.findById(id);
    }
    @Override
    public Optional<Trip> findById(long id, String startDate, String startTime, String endDate){
        Optional<LocalDateTime> start = getIsoLocalDateTime(startDate,startTime);
        if(!start.isPresent()){
            IllegalArgumentException e = new IllegalArgumentException();
            LOGGER.error("StartDate '{}' or startTime '{}' have invalid values", startDate, startTime, e);
            throw e;
        }
        Optional<LocalDateTime> end = getIsoLocalDateTime(endDate,startTime);
        if(!end.isPresent()){
            IllegalArgumentException e = new IllegalArgumentException();
            LOGGER.error("EndDate '{}' or endTime '{}' have invalid values", endDate, startTime, e);
            throw e;
        }
        return findById(id,start.get(),end.get());
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
        final Optional<Trip> trip = tripDao.findById(tripId);
        if(!trip.isPresent()){
            IllegalStateException e = new IllegalStateException();
            LOGGER.error("Trip with id {} not found", tripId, e);
            throw e;
        }
        return trip.get().getDriver().equals(user);
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
            IllegalArgumentException e = new IllegalArgumentException();
            LOGGER.error("{} or dateTime '{}' have invalid values", trip, dateTime, e);
            throw e;
        }
        return tripDao.getPassengers(trip,dateTime);
    }
    @Override
    public List<Passenger> getPassengersRecurrent(Trip trip, LocalDateTime startDate, LocalDateTime endDate){
        if( trip.getStartDateTime().isAfter(startDate)
                || trip.getEndDateTime().isBefore(startDate)
                || Period.between(trip.getStartDateTime().toLocalDate(),startDate.toLocalDate()).getDays()%7!=0
        ){
            IllegalArgumentException e = new IllegalArgumentException();
            LOGGER.error("{} or startDate '{}' or endDate '{}' have invalid values", trip, startDate, endDate, e);
            throw e;
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
            IllegalArgumentException e = new IllegalArgumentException();
            LOGGER.error("StartDateTime '{}' or endDateTime '{}' have invalid values", start, end, e);
            throw e;
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
    public PagedContent<Trip> getTripsCreatedByUser(final User user, int page, int pageSize){
        validatePageAndSize(page,pageSize);
        return tripDao.getTripsCreatedByUser(user,Optional.empty(),Optional.empty(),page,pageSize);
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
            return Trip.SortType.valueOf(sortType.toUpperCase());
        }catch (Exception e){
            return Trip.SortType.PRICE;
        }
    }

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
