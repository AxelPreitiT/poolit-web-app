package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistence.TripDao;
import ar.edu.itba.paw.interfaces.services.TripService;
import ar.edu.itba.paw.interfaces.services.EmailService;
import ar.edu.itba.paw.models.Car;
import ar.edu.itba.paw.models.City;
import ar.edu.itba.paw.models.trips.Trip;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    //TODO: revisar si conviene que pase todos los campos de usuario aca y que esto se encargue de crearlos
    //Lo bueno que tendria hacerlo asi es que aisla al controller de toda la logica
    //Pero acopla demasiada funcionalidad entre servicios
    @Override
    public Trip createTrip(final City originCity, final String originAddress, final City destinationCity, final String destinationAddress, final Car car, final String date, final String time,final double price, final int maxSeats, User driver) {
        //Usamos que el front debe pasar el date en ISO-8601
        LocalDateTime dateTime = getLocalDateTime(date,time).get();
        Trip newTrip = tripDao.create(
                originCity,
                originAddress,
                destinationCity,
                destinationAddress,
                car,
                dateTime,
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
    private Optional<LocalDateTime> getLocalDateTime(final String date, final String time){
        if(date.length()==0 || time.length()==0){
            return Optional.empty();
        }
        LocalDateTime ans;
        try{
            String[] timeTokens = time.split(":");
            ans = LocalDate.parse(date,DateTimeFormatter.ISO_DATE).atTime(Integer.parseInt(timeTokens[0]),Integer.parseInt(timeTokens[1]));
        }catch (Exception e){
            return Optional.empty();
        }
        return Optional.of(ans);
    }

    public boolean addPassenger(Trip trip, User passenger){
        if( trip == null || passenger == null || trip.getOccupiedSeats()==trip.getMaxSeats()){
            return false;
        }
        try{
            emailService.sendMailNewPassenger(trip, passenger);
            emailService.sendMailTripConfirmation(trip, passenger);
        }
        catch( Exception e){
            e.printStackTrace();
        }
        return tripDao.addPassenger(trip,passenger);
    }
    @Override
    public boolean addPassenger(long tripId, User passenger){
        Optional<Trip> trip = findById(tripId);
        //Ignorar suggestion, usar filter no tiene mucho sentido aca (funciona porque devuelve boolean)
        //Trip trip = findById(tripId);
        if(trip.isPresent()){
            return addPassenger(trip.get(),passenger);
        }
        return false;
    }
    @Override
    public Optional<Trip> findById(long id) {
        return tripDao.findById(id);
    }
    @Override
    public List<User> getPassengers(final long tripId){
        return tripDao.getPassengers(tripId);
    }
    @Override
    public List<Trip> getFirstNTrips(long n){
        return tripDao.getFirstNTrips(n);
    }
    @Override
    public List<Trip> getTripsByDateTimeAndOriginAndDestination(long origin_city_id, long destination_city_id,final String date, final String time){
        Optional<LocalDateTime> dateTime = getLocalDateTime(date,time);
        return tripDao.getTripsByDateTimeAndOriginAndDestination(origin_city_id,destination_city_id,dateTime);
    }
}
