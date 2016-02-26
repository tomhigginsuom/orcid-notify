package uom.lib.orcid.services;

import java.util.*;
import uom.lib.orcid.dao.OrcidDAO;

public class OrcidService {
    
    private OrcidDAO orcidDAO;

    public void setOrcidDAO(OrcidDAO orcidDAO) {
        this.orcidDAO = orcidDAO;
    }
    
    public ArrayList<String> getOrcids() throws Exception {
        
        ArrayList<String> orcids = new ArrayList<>();
        
        List<Map<String, Object>> results = orcidDAO.get();
        
        for (Map<String, Object> result : results) {
            Object value = result.get("orcid_id");
            orcids.add((String)value);
        }
        
        return orcids;
    }
    
}
