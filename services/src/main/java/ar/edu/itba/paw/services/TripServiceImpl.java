package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistence.TripDao;
import ar.edu.itba.paw.interfaces.services.TripService;
import ar.edu.itba.paw.interfaces.services.EmailService;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.trips.Trip;
import ar.edu.itba.paw.models.trips.TripInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class TripServiceImpl implements TripService {

    @Autowired
    private EmailService emailService;

    private final TripDao tripDao;

    @Autowired
    public TripServiceImpl(final TripDao tripDao){
        this.tripDao = tripDao;
    }

    @Override
    public Trip createTrip(final City originCity, final String originAddress, final City destinationCity, final String destinationAddress, final Car car, final String startDate, final String startTime,final double price, final int maxSeats, User driver, final String endDate, final String endTime) {
        //Usamos que el front debe pasar el date en ISO-8601
        LocalDateTime startDateTime = getLocalDateTime(startDate,startTime).get();
        //If Trip is not recurrent, then endDateTime is the same as startDateTime
        LocalDateTime endDateTime = getLocalDateTime(endDate,endTime).orElse(startDateTime);
        if(!startDateTime.getDayOfWeek().equals(endDateTime.getDayOfWeek())
        || startDateTime.isAfter(endDateTime)
        || originCity == null || destinationCity == null
        || car == null || driver == null
        || maxSeats<=0 || price<0){
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
                price,
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
    @Override
    public Trip createTrip(final City originCity, final String originAddress, final City destinationCity, final String destinationAddress, final Car car, final String date, final String time,final double price, final int maxSeats, User driver){
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
    public boolean addPassenger(Trip trip, User passenger, LocalDateTime dateTime){
        return addPassenger(trip,passenger,dateTime,dateTime);
    }
    @Override
    public boolean addPassenger(Trip trip,User passenger, String startDate,String startTime, String endDate){
        LocalDateTime startDateTime = getIsoLocalDateTime(startDate,startTime).get();
        LocalDateTime endDateTime = getIsoLocalDateTime(endDate,startTime).orElse(startDateTime);
        return addPassenger(trip,passenger,startDateTime,endDateTime);
    }
    @Override
    public boolean addPassenger(Trip trip, User user, LocalDateTime startDateTime, LocalDateTime endDateTime){
        if(trip==null || user==null){
            throw new IllegalArgumentException();
        }
        Passenger passenger = new Passenger(user,startDateTime,endDateTime);
        List<Passenger> passengers = tripDao.getPassengers(trip,trip.getStartDateTime(),trip.getEndDateTime());
        if(passengers.contains(passenger)){
            throw new IllegalStateException();
        }
        //TODO: se puede obviar si se pide al trip en el mismo intervalo en el que se va a inscribir a la persona
        //Y en ese caso se puede comparar de manera segura a getOccupiedSeats con maxSeats
        passengers = tripDao.getPassengers(trip,startDateTime,endDateTime);
        if(passengers.size()>=trip.getMaxSeats()){
            throw new IllegalStateException();
        }
        //TODO: arreglar (hacerlo cuando tenga varias vistas para inscribirse a un viaje)
        //El chequeo este
//        startDateTime.isBefore(LocalDateTime.now())
        //Esta mal cuando se quiere inscribir a un viaje desde el discovery
        //Tenemos que hacer que se inscriba desde el momento actual
        if(    startDateTime == null || endDateTime == null
            || startDateTime.isAfter(endDateTime) || trip.getStartDateTime().isAfter(startDateTime)
            || trip.getEndDateTime().isBefore(endDateTime) || !trip.getStartDateTime().getDayOfWeek().equals(startDateTime.getDayOfWeek())
            || !trip.getEndDateTime().getDayOfWeek().equals(endDateTime.getDayOfWeek()) || endDateTime.isBefore(startDateTime)
            || startDateTime.isBefore(LocalDateTime.now()) || trip.getDriver().equals(user)
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
    @Override
    public boolean addPassenger(long tripId, User passenger, LocalDateTime startDateTime, LocalDateTime endDateTime){
        Optional<Trip> trip = findById(tripId);
        if(!trip.isPresent()){
            throw new IllegalStateException();
        }
        //Ignorar suggestion, usar filter no tiene mucho sentido aca (funciona porque devuelve boolean)
        //Trip trip = findById(tripId);
        return addPassenger(trip.get()  ,passenger,startDateTime,endDateTime);
    }
    @Override
    public boolean addPassenger(long tripId, User passenger, LocalDateTime dateTime){
        return addPassenger(tripId,passenger,dateTime,dateTime);
    }

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
    public PagedContent<Trip> getIncomingTrips(int page, int pageSize){
        validatePageAndSize(page,pageSize);
        return tripDao.getIncomingTrips(page,pageSize);
    }
    @Override
    public PagedContent<Trip> getTripsByDateTimeAndOriginAndDestination(
            long origin_city_id, long destination_city_id, final String startDate,
            final String startTime, final String endDate, final String endTime,
            final int page, final int pageSize){
        validatePageAndSize(page,pageSize);
        LocalDateTime startDateTime;
        Optional<DayOfWeek> dayOfWeek = Optional.empty();
        Optional<LocalDateTime> aux = getLocalDateTime(startDate,startTime);
        if(aux.isPresent()){
            startDateTime = aux.get();
            dayOfWeek = Optional.of(startDateTime.getDayOfWeek());
        }else{
            startDateTime = LocalDateTime.now();
        }
        Optional<LocalDateTime> endDateTime = getLocalDateTime(endDate,endTime);

        return tripDao.getTripsWithFilters(origin_city_id,destination_city_id,startDateTime,dayOfWeek,endDateTime,Optional.empty(),Optional.empty(), page, pageSize);
    }
    @Override
    public PagedContent<Trip> getTripsByDateTimeAndOriginAndDestinationAndPrice(
            long origin_city_id, long destination_city_id, final String startDate,
            final String startTime, final String endDate, final String endTime,
            double minPrice, double maxPrice,
            final int page, final int pageSize){
        validatePageAndSize(page,pageSize);
        Optional<LocalDateTime> startDateTime = getLocalDateTime(startDate,startTime);
        LocalDateTime endDateTime = getLocalDateTime(endDate,endTime).orElse(startDateTime.get());
        return tripDao.getTripsWithFilters(origin_city_id,destination_city_id,startDateTime.get(),Optional.of(startDateTime.get().getDayOfWeek()),Optional.of(endDateTime),Optional.of(minPrice),Optional.of(maxPrice),page,pageSize);
    }
}
