package ar.edu.itba.paw.persistence;
/*
import ar.edu.itba.paw.interfaces.persistence.ProvinceDao;
import ar.edu.itba.paw.models.Province;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;


public class ProvinceDaoImpl implements ProvinceDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProvinceDaoImpl.class);

    private final JdbcTemplate jdbcTemplate;

    private final static RowMapper<Province> PROVINCE_ROW_MAPPER = (rs, rowNum) -> new Province(
            rs.getLong("province_id"),
            rs.getString("name")
    );

    @Autowired
    public ProvinceDaoImpl(final DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<Province> findProvinceById(long id) {
        LOGGER.debug("Looking for province with id {} in the database", id);
        final Optional<Province> result = jdbcTemplate.query("SELECT * FROM provinces WHERE province_id = ?", PROVINCE_ROW_MAPPER, id).stream().findFirst();
        LOGGER.debug("Found {} in the database", result.isPresent() ? result.get() : "nothing");
        return result;
    }

    @Override
    public List<Province> getAllProvinces() {
        LOGGER.debug("Looking for all provinces in the database");
        final List<Province> result = jdbcTemplate.query("SELECT * FROM provinces", PROVINCE_ROW_MAPPER);
        LOGGER.debug("Found {} in the database", result);
        return result;
    }
}


 */