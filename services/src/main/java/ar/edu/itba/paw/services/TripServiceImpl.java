package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistence.TripDao;
import ar.edu.itba.paw.interfaces.services.TripService;
import ar.edu.itba.paw.models.City;
import ar.edu.itba.paw.models.Trip;
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
    public Trip createTrip(City originCity, String originAddress, City destinationCity, String destinationAddress, String date, String time, String carInfo, int seats, String email, String phone) {
        return tripDao.create(originCity, originAddress, destinationCity, destinationAddress, date, time, carInfo, seats, email, phone);
    }
}
