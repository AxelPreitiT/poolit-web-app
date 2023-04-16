package ar.edu.itba.paw.interfaces.persistence;

import ar.edu.itba.paw.models.Province;
import java.util.List;
import java.util.Optional;

public interface ProvinceDao {

    /**
     * @param id the id of the Province
     * @return the Province with the given id, or Optional.empty() if it doesn't exist
     */
    Optional<Province> findProvinceById(final long id);

    /**
     * @return a list of all the Provinces
     */
    List<Province> getAllProvinces();
}
