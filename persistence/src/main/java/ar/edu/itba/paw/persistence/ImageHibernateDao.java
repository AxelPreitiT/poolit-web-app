package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.ImageDao;
import ar.edu.itba.paw.models.Image;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class ImageHibernateDao implements ImageDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageHibernateDao.class);


    @PersistenceContext
    private EntityManager em;


    @Override
    public Image create(byte[] data) {
        LOGGER.debug("Adding new image to the database");
        final Image image = new Image(data);
        em.persist(image);
        LOGGER.info("Image added to the database with id {}", image.getImageId());
        LOGGER.debug("New {}", image);
        return image;
    }


    @Override
    public Optional<Image> findById(final long imageId){
        LOGGER.debug("Looking for image with id {} in the database", imageId);
        final Optional<Image> result = Optional.ofNullable(em.find(Image.class, imageId));
        LOGGER.debug("Found {} in the database", result.isPresent() ? result.get() : "nothing");
        return result;
    }

    @Override
    public void replaceImage(long id, byte[] data) {
        //TODO es mejor cambiar data o crear nueva entrada
        Optional<Image> img = findById(id);
        if (img.isPresent()){
            Image imgToModify= img.get();
            imgToModify.setData(data);
            em.merge(imgToModify);
        }
    }
}
