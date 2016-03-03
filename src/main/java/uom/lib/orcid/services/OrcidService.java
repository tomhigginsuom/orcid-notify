package uom.lib.orcid.services;

import java.util.*;
import uom.lib.orcid.dao.OrcidDAO;
import uom.lib.orcid.model.*;

public class OrcidService {
    
    private OrcidDAO orcidDAO;

    public void setOrcidDAO(OrcidDAO orcidDAO) {
        this.orcidDAO = orcidDAO;
    }
    
    public ArrayList<Profile> getOrcids() throws Exception {
        
        ArrayList<Profile> orcids = new ArrayList<>();
        
        List<Map<String, Object>> results = orcidDAO.getOrcids();
        
        for (Map<String, Object> result : results) {
            
            String orcidId = (String)result.get("orcid_id");
            Profile profile = new Profile(orcidId);
            
            profile.setFamilyName((String)result.get("family_name"));
            profile.setGivenNames((String)result.get("given_names"));
            profile.setLastModified((Date)result.get("last_modified"));
            profile.setGroupCount((Long)result.get("group_count"));
            
            orcids.add(profile);
        }
        
        return orcids;
    }
    
    public ArrayList<String> getStaleOrcids() throws Exception {
        
        ArrayList<String> orcids = new ArrayList<>();
        
        List<Map<String, Object>> results = orcidDAO.getStaleOrcids();
        
        for (Map<String, Object> result : results) {
            Object value = result.get("orcid_id");
            orcids.add((String)value);
        }
        
        return orcids;
    }
    
    public void markOrcidStale(String orcidId) {
        orcidDAO.markOrcidStale(orcidId);
    }
    
    public void markOrcidError(String orcidId) {
        orcidDAO.markOrcidError(orcidId);
    }
    
    public void updateOrcid(String orcidId, Date timestamp, Date initialLoad, String givenNames, String familyName) {
        orcidDAO.updateOrcid(orcidId, timestamp, initialLoad, givenNames, familyName);
    }
    
    public Profile getOrcidProfile(String orcidId) throws Exception {
        Map<String, Object> result =  orcidDAO.getOrcidProfile(orcidId);
        
        Profile profile = new Profile(orcidId);
        profile.setFamilyName((String)result.get("family_name"));
        profile.setGivenNames((String)result.get("given_names"));
        profile.setLastModified((Date)result.get("last_modified"));
        profile.setGroupCount((Long)result.get("group_count"));
        
        return profile;
    }
    
    public void updateWorks(String orcidId, Work work, Date timestamp) {
        
        // Check the work record already exists in the database ...
        boolean exists = orcidDAO.checkOrcidWorkExists(orcidId, work.getPutCode(), work.getIdentifierType(), work.getIdentifier());
        
        if (!exists) {
            
            Integer worksGroup = assignWorksGroup(orcidId, work);
            
            // Add new work information with current timestamp
            orcidDAO.addOrcidWork(orcidId,
                    timestamp,
                    work.getPutCode(),
                    work.getIdentifierType(),
                    work.getIdentifier(),
                    worksGroup,
                    work.getWorkType(),
                    work.getTitle(),
                    work.getYear(),
                    work.getMonth(),
                    work.getDay());
        }
    }
    
    public ArrayList<Work> getOrcidWorks(String orcidId) throws Exception {

        ArrayList<Work> works = new ArrayList<>();
        
        List<Map<String, Object>> results =  orcidDAO.getOrcidWorks(orcidId);
        
        for (Map<String, Object> result : results) {
            Integer putCode = (Integer)result.get("put_code");
            String workType = (String)result.get("work_type");
            String title = (String)result.get("title");
            Integer year = (Integer)result.get("publication_year");
            Integer month = (Integer)result.get("publication_month");
            Integer day = (Integer)result.get("publication_day");
            String identifierType = (String)result.get("identifier_type");
            String identifier = (String)result.get("identifier");
            Date created = (Date)result.get("created");
            Integer group = (Integer)result.get("group_id");
            
            Work work = new Work(putCode, workType, title, year, month, day, identifierType, identifier);
            work.setCreated(created);
            work.setGroup(group);
            
            works.add(work);
        }
        
        return works;
    }
    
    public void updateStatistics(int requests, long bytes) {
        orcidDAO.updateStatistics(requests, bytes);
    }
    public Integer assignWorksGroup(String orcidId, Work work) {
        
        HashSet<Integer> groups = new HashSet<>();
        
        // Check if any works groups exist for this orcid
        Integer groupCount = orcidDAO.getGroupCount(orcidId);
        if (groupCount > 0) {
            
            // Check for a matching existing group ...
            
            // Check for a matching ID in an existing group. ISSN matches are ignored.
            if (work.identifierType != null && work.identifierType != null && !work.identifierType.equals("ISSN")) {        
                List<Map<String, Object>> results =  orcidDAO.getWorksGroupsForId(orcidId, work.identifierType, work.identifier);
                
                for (Map<String, Object> result : results) {
                    groups.add((Integer)result.get("group_id"));
                }
            }
            
            // Check for groups which have the same put code.
            List<Map<String, Object>> results =  orcidDAO.getWorksGroupsForPutCode(orcidId, work.putCode);
            for (Map<String, Object> result : results) {
                groups.add((Integer)result.get("group_id"));
            }
            
            // Choose a group from the set
            Integer newGroupId = null;
            for (Integer group : groups) {
                
                if (newGroupId == null) {
                    // Take the first group
                    newGroupId = group;
                } else {
                    // Merge this group into the chosen group
                    orcidDAO.mergeWorksGroups(orcidId, group, newGroupId);
                }
            }
            
            if (newGroupId != null) {
                return newGroupId;
            }
        }
        
        // Create a new group and return the ID
        return orcidDAO.addWorksGroup(orcidId);
    }
}
