package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.ProvinceDao;
import ar.edu.itba.paw.models.Province;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class ProvinceDaoImpl implements ProvinceDao {

    private final JdbcTemplate jdbcTemplate;

    private final static RowMapper<Province> PROVINCE_ROW_MAPPER = (rs, rowNum) -> new Province(
            rs.getLong("id"),
            rs.getString("name")
    );

    @Autowired
    public ProvinceDaoImpl(final DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<Province> findProvinceById(long id) {
        return jdbcTemplate.query("SELECT * FROM provinces WHERE id = ?", PROVINCE_ROW_MAPPER, id).stream().findFirst();
    }

    @Override
    public List<Province> getAllProvinces() {
        return jdbcTemplate.query("SELECT * FROM provinces", PROVINCE_ROW_MAPPER);
    }
}
