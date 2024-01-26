package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.services.TripSortTypeService;
import ar.edu.itba.paw.models.trips.Trip;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TripSortTypeServiceImpl implements TripSortTypeService {

    @Override
    public List<Trip.SortType> getSortTypes() {
        return Arrays.stream(Trip.SortType.values()).collect(Collectors.toList());
    }

    @Override
    public Optional<Trip.SortType> findById(final String id) {
        try {
            return Optional.of(Trip.SortType.valueOf(id));
        } catch (final Exception e) {
            return Optional.empty();
        }
    }
}
