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
        return this.jdbcTemplate.queryForList("select * from orcid where error is null and (needs_update = 1 or last_modified is null)");
    }
    
    public Map<String, Object> getOrcidProfile(String orcidId) {
        return this.jdbcTemplate.queryForMap("select * from orcid where orcid_id = ?",
                orcidId);
    }
    
    public void markOrcidStale(String orcidId) {
        this.jdbcTemplate.update("update orcid set needs_update = 1 where orcid_id = ?", orcidId);
    }
    
    public void markOrcidError(String orcidId) {
        this.jdbcTemplate.update("update orcid set error = 1 where orcid_id = ?", orcidId);
    }
    
    public void updateOrcid(String orcidId, Date timestamp, Date initialLoad, String givenNames, String familyName) {
        
        // Update the timestamp and state of the record to indicate that it has been checked
        this.jdbcTemplate.update("update orcid set needs_update = 0, last_modified = ?, given_names = ?, family_name = ? where orcid_id = ?",
                timestamp, givenNames, familyName, orcidId);
        
        // Set the initial load timestamp if this is the first time the profile is being checked
        this.jdbcTemplate.update("update orcid set initial_load = ? where orcid_id = ? and initial_load is null",
                initialLoad, orcidId);
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
    
    public void addOrcidWork(String orcidId, Date timestamp, Integer putCode, String identifierType, String identifier, String title, Integer year, Integer month, Integer day) {
        
        if (identifier == null) {
            identifier = "";
        }
        
        if (identifierType == null) {
            identifierType = "";
        }
        
        this.jdbcTemplate.update("insert into works (orcid_id, created, put_code, identifier_type, identifier, title, publication_year, publication_month, publication_day) values (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                orcidId, timestamp, putCode, identifierType, identifier, title, year, month, day);
    }
    
    public void updateStatistics(int requests, long bytes) {
        
        Integer count = this.jdbcTemplate.queryForObject("select count(*) from statistics", Integer.class);
        
        if (count == null || count == 0) {
            // Insert new statistics record
            this.jdbcTemplate.update("insert into statistics (api_requests, api_data_bytes) values (?, ?)",
                requests, bytes);

        } else {
            // Update existing statistics record
            this.jdbcTemplate.update("update statistics set api_requests = api_requests + ?, api_data_bytes = api_data_bytes + ?",
                requests, bytes);
        }
    }
}
