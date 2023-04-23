package ar.edu.itba.paw.persistence;


import ar.edu.itba.paw.interfaces.persistence.ImageDao;
import ar.edu.itba.paw.models.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class ImageDaoImpl implements ImageDao {

    private static final RowMapper<Image> ROW_MAPPER = (resultSet, rowNum) -> new Image(resultSet.getLong("image_id"),resultSet.getBytes("bytea"));

    private final JdbcTemplate jdbcTemplate;

    private final SimpleJdbcInsert jdbcInsert;

    @Autowired
    public ImageDaoImpl(final DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .usingGeneratedKeyColumns("image_id")
                .withTableName("images");
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS images(" +
                "image_id SERIAL PRIMARY KEY," +
                "bytea BYTEA)" );
    }

    @Override
    public Image create(byte[] data) {
        Map<String,Object> parameters = new HashMap<>();
        parameters.put("bytea", data);
        Number key = jdbcInsert.executeAndReturnKey(parameters);
        return new Image(key.longValue(), data);
    }


    @Override
    public Optional<Image> findById(long imageId){
        return jdbcTemplate.query("SELECT * FROM images WHERE image_id = ?",ROW_MAPPER,imageId).stream().findFirst();
    }

}




