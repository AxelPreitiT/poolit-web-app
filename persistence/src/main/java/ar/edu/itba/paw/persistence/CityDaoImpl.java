package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.CityDao;
import ar.edu.itba.paw.models.City;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class CityDaoImpl implements CityDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(CityDaoImpl.class);

    private final JdbcTemplate jdbcTemplate;

    private final static RowMapper<City> CITY_ROW_MAPPER = (rs, rowNum) -> new City(
            rs.getLong("city_id"),
            rs.getString("name"),
            rs.getLong("province_id")
    );

    @Autowired
    public CityDaoImpl(final DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<City> findCityById(long id) {
        LOGGER.debug("Looking for city with id {} in the database", id);
        final Optional<City> result = jdbcTemplate.query("SELECT * FROM cities WHERE city_id = ?", CITY_ROW_MAPPER, id).stream().findFirst();
        LOGGER.debug("Found {} in the database", result.isPresent() ? result.get() : "nothing");
        return result;
    }

    @Override
    public List<City> getCitiesByProvinceId(long provinceId) {
        LOGGER.debug("Looking for cities that belong to province with id {} in the database", provinceId);
        final List<City> result = jdbcTemplate.query("SELECT * FROM cities WHERE province_id = ?", CITY_ROW_MAPPER, provinceId);
        LOGGER.debug("Found {} in the database", result);
        return result;
    }
}
