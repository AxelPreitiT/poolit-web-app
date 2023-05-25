package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.CityDao;
import ar.edu.itba.paw.models.City;
import ar.edu.itba.paw.models.Province;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class CityHibernateDao implements CityDao {

    @PersistenceContext
    private EntityManager em;

    private static final Logger LOGGER = LoggerFactory.getLogger(CityHibernateDao.class);


    @Override
    public Optional<City> findCityById(long id) {
        LOGGER.debug("Looking for city with id {} in the database", id);
        final Optional<City> result =  Optional.of(em.find(City.class, id));
        LOGGER.debug("Found {} in the database", result.isPresent() ? result.get() : "nothing");
        return result;
    }

    @Override
    public List<City> getCitiesByProvinceId(long provinceId) {
        LOGGER.debug("Looking for cities that belong to province with id {} in the database", provinceId);
        final TypedQuery<City> query = (em.createQuery("from City as c where c.province.id= :provinceId", City.class));
        query.setParameter("provinceId", provinceId);
        final List<City> result = query.getResultList();
        LOGGER.debug("Found {} in the database", result);
        return result;
    }
}
