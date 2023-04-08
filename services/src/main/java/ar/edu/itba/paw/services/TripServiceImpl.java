package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistence.TripDao;
import ar.edu.itba.paw.interfaces.services.TripService;
import ar.edu.itba.paw.models.City;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TripServiceImpl implements TripService {

    private final TripDao tripDao;

    @Autowired
    public TripServiceImpl(final TripDao tripDao){
        this.tripDao = tripDao;
    }

    @Override
    public Trip createTrip(final City originCity, final String originAddress, final City destinationCity, final String destinationAddress,final String carInfo, final String date, final String time, final int seats, User driver) {
        return tripDao.create(originCity,originAddress,destinationCity,destinationAddress,carInfo,date,time,seats,driver);
    }

    public boolean addPassenger(Trip trip, User passenger){
        if( trip == null || passenger == null){
            return false;
        }
        return tripDao.addPassenger(trip,passenger);
    }
    @Override
    public boolean addPassenger(long tripId, User passenger){
        Trip trip = findById(tripId);
        return addPassenger(trip,passenger);
    }
    @Override
    public Trip findById(long id) {
        return tripDao.findById(id);
    }
}
