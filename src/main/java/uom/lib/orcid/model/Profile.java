package uom.lib.orcid.model;

import java.util.ArrayList;
import java.util.Date;

// An ORCID profile
public class Profile implements Comparable {
    
    // ORCID
    public String identifier;
    
    // Dates
    public Date lastModified;

    // Names
    public String givenNames;
    public String familyName;
    
    // Works
    public ArrayList<Work> works;
    public Long groupCount;
    
    // API statistics
    public Integer size;
    
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

    public Long getGroupCount() {
        return groupCount;
    }

    public void setGroupCount(Long groupCount) {
        this.groupCount = groupCount;
    }
    
    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
    
    public int compareTo(Object other) {
        Profile otherProfile = (Profile)other;
        
        if (this.lastModified != null || otherProfile.lastModified != null) {
            if (otherProfile.lastModified == null) {
                return 1;
            }
            if (this.lastModified == null) {
                return -1;
            }
            return (this.lastModified.compareTo(otherProfile.lastModified));
        }
        
        // Default order by identifier (reverse)
        return otherProfile.identifier.compareTo(this.identifier);
    }
}
