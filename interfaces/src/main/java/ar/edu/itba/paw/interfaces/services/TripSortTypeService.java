package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.trips.Trip;

import java.util.List;
import java.util.Optional;

public interface TripSortTypeService {

    List<Trip.SortType> getSortTypes();

    Optional<Trip.SortType> findById(final String id);
}
