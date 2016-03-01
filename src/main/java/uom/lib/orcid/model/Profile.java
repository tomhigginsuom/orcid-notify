package uom.lib.orcid.model;

import java.util.ArrayList;
import java.util.Date;

// An ORCID profile
public class Profile {
    
    // ORCID
    public String identifier;
    
    // Dates
    public Date lastModified;

    // Names
    public String givenNames;
    public String familyName;
    
    // Works
    public ArrayList<Work> works;
    
    public Profile(String identifier) {
        this.identifier = identifier;
    }
    
    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public String getGivenNames() {
        return givenNames;
    }

    public void setGivenNames(String givenNames) {
        this.givenNames = givenNames;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public ArrayList<Work> getWorks() {
        return works;
    }
    
    public void setWorks(ArrayList<Work> works) {
        this.works = works;
    }
}
