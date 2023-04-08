package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.TripDao;
import ar.edu.itba.paw.models.City;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Repository
public class TripDaoImpl implements TripDao {
    private final Map<Long,Trip> trips = new HashMap<>();
    @Override
    public Trip create(final City originCity, final String originAddress, final City destinationCity, final String destinationAddress,final String carInfo, final String date, final String time, final int seats, User driver) {
        Trip ans = new Trip(originCity,originAddress,destinationCity,destinationAddress,carInfo,date,time,seats,driver);
        trips.put(ans.getId(),ans);
        return ans;
    }
    @Override
    public boolean addPassenger(final Trip trip, final User passenger){
        if(trip == null || passenger == null){
            return false;
        }
        return trip.addPassenger(passenger);
    }
    @Override
    public Trip findById(long id) {
        return trips.get(id);
    }
}
