package uom.lib.orcid.dao;

import java.util.*;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class OrcidDAO {
   
    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Map<String, Object>> getOrcids() {
        return this.jdbcTemplate.queryForList("select * from orcid");
    }
    
    public List<Map<String, Object>> getStaleOrcids() {
        return this.jdbcTemplate.queryForList("select * from orcid where needs_update = 1 or updated is null");
    }
    
    public Map<String, Object> getOrcidProfile(String orcidId) {
        return this.jdbcTemplate.queryForMap("select * from orcid where orcid_id = ?",
                orcidId);
    }
    
    public void updateOrcid(String orcidId, Date timestamp, String givenNames, String familyName) {
        this.jdbcTemplate.update("update orcid set needs_update = 0, updated = ?, given_names = ?, family_name = ? where orcid_id = ?",
                timestamp, givenNames, familyName, orcidId);
    }
    
    public List<Map<String, Object>> getOrcidWorks(String orcidId) {
        return this.jdbcTemplate.queryForList("select * from works where orcid_id = ?",
                orcidId);
    }
    
    public boolean checkOrcidWorkExists(String orcidId, Integer putCode, String identifierType, String identifier) {
        
        if (identifier == null) {
            identifier = "";
        }
        
        if (identifierType == null) {
            identifierType = "";
        }
        
        Integer count = this.jdbcTemplate.queryForObject("select count(*) from works where orcid_id = ? and put_code = ? and identifier_type = ? and identifier = ?",
                Integer.class,
                orcidId, putCode, identifierType, identifier);
        
        return count != null && count > 0;
    }
    
    public void addOrcidWork(String orcidId, Integer putCode, String identifierType, String identifier, String title, Integer year, Integer month, Integer day) {
        
        if (identifier == null) {
            identifier = "";
        }
        
        if (identifierType == null) {
            identifierType = "";
        }
        
        this.jdbcTemplate.update("insert into works (orcid_id, put_code, identifier_type, identifier, title, publication_year, publication_month, publication_day) values (?, ?, ?, ?, ?, ?, ?, ?)",
                orcidId, putCode, identifierType, identifier, title, year, month, day);
    }
}
