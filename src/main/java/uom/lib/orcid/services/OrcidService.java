package uom.lib.orcid.services;

import java.util.*;
import uom.lib.orcid.dao.OrcidDAO;
import uom.lib.orcid.model.*;

public class OrcidService {
    
    private OrcidDAO orcidDAO;

    public void setOrcidDAO(OrcidDAO orcidDAO) {
        this.orcidDAO = orcidDAO;
    }
    
    public ArrayList<String> getOrcids() throws Exception {
        
        ArrayList<String> orcids = new ArrayList<>();
        
        List<Map<String, Object>> results = orcidDAO.getOrcids();
        
        for (Map<String, Object> result : results) {
            Object value = result.get("orcid_id");
            orcids.add((String)value);
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
    
    public void updateOrcid(String orcidId, Date timestamp, String givenNames, String familyName) {
        orcidDAO.updateOrcid(orcidId, timestamp, givenNames, familyName);
    }
    
    public Profile getOrcidProfile(String orcidId) throws Exception {
        Map<String, Object> result =  orcidDAO.getOrcidProfile(orcidId);
        
        Profile profile = new Profile(orcidId);
        profile.setFamilyName((String)result.get("family_name"));
        profile.setGivenNames((String)result.get("given_names"));
        profile.setLastModified((Date)result.get("updated"));
        
        return profile;
    }
    
    public void updateWorks(String orcidId, Work work) {
        
        // Check if exists in DB ...
        boolean exists = orcidDAO.checkOrcidWorkExists(orcidId, work.getPutCode(), work.getIdentifierType(), work.getIdentifier());
        
        if (!exists) {
            // Add new work informtion with current timestamp
            orcidDAO.addOrcidWork(orcidId,
                    work.getPutCode(),
                    work.getIdentifierType(),
                    work.getIdentifier(),
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
            String title = (String)result.get("title");
            Integer year = (Integer)result.get("publication_year");
            Integer month = (Integer)result.get("publication_month");
            Integer day = (Integer)result.get("publication_day");
            String identifierType = (String)result.get("identifier_type");
            String identifier = (String)result.get("identifier");
            Date created = (Date)result.get("created");
            
            Work work = new Work(putCode, title, year, month, day, identifierType, identifier);
            work.setCreated(created);
            
            works.add(work);
        }
        
        return works;
    }
}
