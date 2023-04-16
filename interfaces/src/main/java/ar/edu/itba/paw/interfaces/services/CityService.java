package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.City;
import java.util.List;
import java.util.Optional;

public interface CityService {

    /**
     * @param id the id of the City
     * @return the City with the given id, or Optional.empty() if it doesn't exist
     */
    Optional<City> findCityById(final long id);

    /**
     * @param provinceId the id of the Province
     * @return a list of City objects that belong to the Province with the given id
     */
    List<City> getCitiesByProvinceId(final long provinceId);
}
