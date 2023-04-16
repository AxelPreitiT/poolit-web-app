package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.CityDao;
import ar.edu.itba.paw.models.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class CityDaoImpl implements CityDao {

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
        return jdbcTemplate.query("SELECT * FROM cities WHERE city_id = ?", CITY_ROW_MAPPER, id).stream().findFirst();
    }

    @Override
    public List<City> getCitiesByProvinceId(long provinceId) {
        return jdbcTemplate.query("SELECT * FROM cities WHERE province_id = ?", CITY_ROW_MAPPER, provinceId);
    }
}
