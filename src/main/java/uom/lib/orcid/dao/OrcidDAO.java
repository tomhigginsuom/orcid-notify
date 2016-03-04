package uom.lib.orcid.dao;

import java.util.*;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.*;

public class OrcidDAO {
   
    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Map<String, Object>> getOrcids() {
        return this.jdbcTemplate.queryForList("select *, (select count(*) from works_groups where works_groups.orcid_id = orcid.orcid_id) as group_count from orcid");
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
        return this.jdbcTemplate.queryForList("select * from works where orcid_id = ? order by group_id",
                orcidId);
    }
    
    public List<Map<String, Object>> getNewOrcidsForYear(Integer year) {
        return this.jdbcTemplate.queryForList("select * from works where group_id in (select g.group_id from (select group_id, min(created) min_created from works where group_id in (select distinct group_id from works where created > CURRENT_TIMESTAMP - INTERVAL 24 HOUR and publication_year = ?) group by group_id) as g where g.min_created > CURRENT_TIMESTAMP - INTERVAL 24 HOUR) order by group_id",
                year);
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
    
    public void addOrcidWork(String orcidId, Date timestamp, Integer putCode, String identifierType, String identifier, Integer worksGroup, String workType, String title, Integer year, Integer month, Integer day) {
        
        if (identifier == null) {
            identifier = "";
        }
        
        if (identifierType == null) {
            identifierType = "";
        }
        
        if (title == null) {
            title = "";
        }
        
        this.jdbcTemplate.update("insert into works (orcid_id, created, put_code, identifier_type, identifier, group_id, work_type, title, publication_year, publication_month, publication_day) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                orcidId, timestamp, putCode, identifierType, identifier, worksGroup, workType, title, year, month, day);
    }
    
    public Integer getGroupCount(String orcidId) {
        
        Integer count = this.jdbcTemplate.queryForObject("select count(*) from works_groups where orcid_id = ?",
                Integer.class,
                orcidId);
        
        return count;
    }
    
    public List<Map<String, Object>> getWorksGroupsForId(String orcidId, String identifierType, String identifier) {
        
        return this.jdbcTemplate.queryForList("select group_id from works where orcid_id = ? and identifier_type = ? and identifier = ? and group_id is not null",
                orcidId, identifierType, identifier);
    }
    
    public List<Map<String, Object>> getWorksGroupsForPutCode(String orcidId, Integer putCode) {
        return this.jdbcTemplate.queryForList("select group_id from works where orcid_id = ? and put_code = ? and group_id is not null",
                orcidId, putCode);
    }
    
    public Integer addWorksGroup(final String orcidId) {
        
        KeyHolder keyHolder = new GeneratedKeyHolder();
        
        jdbcTemplate.update(
            new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps =
                        connection.prepareStatement("insert into works_groups (orcid_id) values (?)", new String[] {"group_id"});
                    ps.setString(1, orcidId);
                    return ps;
                }
            },
            keyHolder);
        
        // Get the generated work group ID
        return keyHolder.getKey().intValue();
    }
    
    public void mergeWorksGroups(String orcidId, int oldGroupId, int newGroupId) {
        
        // Update the individual works records
        this.jdbcTemplate.update("update works set group_id = ? where group_id = ? and orcid_id = ?",
            newGroupId, oldGroupId, orcidId);
        
        // Remove the old group record
        this.jdbcTemplate.update("delete from works_groups where group_id = ? and orcid_id = ?",
            oldGroupId, orcidId);
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
