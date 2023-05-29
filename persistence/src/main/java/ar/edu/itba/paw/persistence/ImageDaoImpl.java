package ar.edu.itba.paw.persistence;


import ar.edu.itba.paw.interfaces.persistence.ImageDao;
import ar.edu.itba.paw.models.Image;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

// @Repository
public class ImageDaoImpl implements ImageDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageDaoImpl.class);

    private static final RowMapper<Image> ROW_MAPPER = (resultSet, rowNum) -> new Image(resultSet.getLong("image_id"),resultSet.getBytes("bytea"));

    private final JdbcTemplate jdbcTemplate;

    private final SimpleJdbcInsert jdbcInsert;

    @Autowired
    public ImageDaoImpl(final DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .usingGeneratedKeyColumns("image_id")
                .withTableName("images");
    }

    @Override
    public Image create(byte[] data) {
        Map<String,Object> parameters = new HashMap<>();
        parameters.put("bytea", data);
        LOGGER.debug("Adding new image to the database");
        Number key = jdbcInsert.executeAndReturnKey(parameters);
        LOGGER.info("Image added to the database with id {}", key.longValue());
        final Image image = new Image(key.longValue(), data);
        LOGGER.debug("New {}", image);
        return image;
    }


    @Override
    public Optional<Image> findById(long imageId){
        LOGGER.debug("Looking for image with id {} in the database", imageId);
        final Optional<Image> result = jdbcTemplate.query("SELECT * FROM images WHERE image_id = ?",ROW_MAPPER,imageId).stream().findFirst();
        LOGGER.debug("Found {} in the database", result.isPresent() ? result.get() : "nothing");
        return result;
    }

}




