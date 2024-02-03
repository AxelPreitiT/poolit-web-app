package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.Province;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ar.edu.itba.paw.interfaces.persistence.ProvinceDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;


@Repository
public class ProvinceHibernateDao implements ProvinceDao{

    @PersistenceContext
    private EntityManager em;

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageHibernateDao.class);

    @Override
    public Optional<Province> findProvinceById(long id) {
        LOGGER.debug("Looking for province with id {} in the database", id);
        final Optional<Province> result =  Optional.ofNullable(em.find(Province.class, id));
        LOGGER.debug("Found {} in the database", result.isPresent() ? result.get() : "nothing");
        return result;
    }

    @Override
    public List<Province> getAllProvinces() {
        LOGGER.debug("Looking for all provinces in the database");
        final TypedQuery<Province> result = (em.createQuery("from Province", Province.class));
        LOGGER.debug("Found {} in the database", result);
        final List<Province> list = result.getResultList();
        return list;
    }
}
