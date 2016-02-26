package uom.lib.orcid.dao;

import java.util.*;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class OrcidDAO {
   
    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Map<String, Object>> get() {
        return this.jdbcTemplate.queryForList("select * from orcid");
    }
}
