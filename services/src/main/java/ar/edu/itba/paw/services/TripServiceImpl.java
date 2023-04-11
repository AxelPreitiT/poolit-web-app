package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistence.TripDao;
import ar.edu.itba.paw.interfaces.services.TripService;
import ar.edu.itba.paw.models.Car;
import ar.edu.itba.paw.models.City;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.StringTokenizer;

@Service
public class TripServiceImpl implements TripService {

    private final TripDao tripDao;

    @Autowired
    public TripServiceImpl(final TripDao tripDao){
        this.tripDao = tripDao;
    }

    @Override
    public Trip createTrip(final City originCity, final String originAddress, final City destinationCity, final String destinationAddress, final Car car, final String date, final String time,final double price, final int maxSeats, User driver) {
        //Usamos que el front debe pasar el date en ISO-8601
        String[] timeTokens = time.split(":");
        LocalDateTime dateTime= LocalDate.parse(date,DateTimeFormatter.ISO_DATE).atTime(Integer.parseInt(timeTokens[0]),Integer.parseInt(timeTokens[1]));
        return tripDao.create(
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
    }
    public boolean addPassenger(Trip trip, User passenger){
        if( trip == null || passenger == null || trip.getOccupiedSeats()==trip.getMaxSeats()){
            return false;
        }
        return tripDao.addPassenger(trip,passenger);
    }
    @Override
    public boolean addPassenger(long tripId, User passenger){
        Optional<Trip> trip = findById(tripId);
        //Ignorar suggestion, usar filter no tiene mucho sentido aca (funciona porque devuelve boolean)
        if(trip.isPresent()){
            return addPassenger(trip.get(),passenger);
        }
        return false;
    }
    @Override
    public Optional<Trip> findById(long id) {
        return tripDao.findById(id);
    }
}
