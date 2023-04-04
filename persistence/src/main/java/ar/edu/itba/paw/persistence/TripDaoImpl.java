package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.TripDao;
import ar.edu.itba.paw.models.City;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;
import org.springframework.stereotype.Repository;

@Repository
public class TripDaoImpl implements TripDao {

    @Override
    public Trip create(final City originCity, final String originAddress, final City destinationCity, final String destinationAddress, final String date, final String time, final int seats, User driver) {
        return new Trip(originCity, originAddress, destinationCity, destinationAddress, date, time, seats,driver);
    }
}
