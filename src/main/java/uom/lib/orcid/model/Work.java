package uom.lib.orcid.model;

// A Work which is related to an Orcid

import java.util.Date;

public class Work implements Comparable {
    
    // ID
    public Integer putCode;
    
    // ORCID
    public String orcid;
    
    // Title
    public String title;
    
    // Dates
    public Integer year;
    public Integer month;
    public Integer day;
    
    // Local database record created
    public Date created;
    
    // Context
    public String workType;
    
    // Source
    public String identifierType;
    public String identifier;
    
    // Grouping
    public Integer group;
    
    public int compareTo(Object other) {
        
        Work otherWork = (Work)other;
        
        // Compare years
        if (year != null || otherWork.year != null) {
            if (otherWork.year == null) {
                return 1;
            }
            if (year == null) {
                return -1;
            }
            if (!year.equals(otherWork.year)) {
                return year - otherWork.year;
            }
        }
        
        // Compare months
        if (month != null || otherWork.month != null) {
            if (otherWork.month == null) {
                return 1;
            }
            if (month == null) {
                return -1;
            }
            if (!month.equals(otherWork.month)) {
                return month - otherWork.month;
            }
        }
        
        // Compare days
        if (day != null || otherWork.day != null) {
            if (otherWork.day == null) {
                return 1;
            }
            if (day == null) {
                return -1;
            }
            if (!day.equals(otherWork.day)) {
                return day - otherWork.day;
            }
        }
        
        // Default order by title (reverse)
        return otherWork.title.compareTo(title);
    }
    
    public Work(String orcid, Integer putCode, String workType, String title, Integer year, Integer month, Integer day, String identifierType, String identifier) {
        this.orcid = orcid;
        this.putCode = putCode;
        this.workType = workType;
        this.title = title;
        this.year = year;
        this.month = month;
        this.day = day;
        this.identifierType = identifierType;
        this.identifier = identifier;
    }

    public String getOrcid() {
        return orcid;
    }

    public void setOrcid(String orcid) {
        this.orcid = orcid;
    }
    
    public Integer getPutCode() {
        return putCode;
    }

    public void setPutCode(Integer putCode) {
        this.putCode = putCode;
    }

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public String getIdentifierType() {
        return identifierType;
    }

    public void setIdentifierType(String identifierType) {
        this.identifierType = identifierType;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Integer getGroup() {
        return group;
    }

    public void setGroup(Integer group) {
        this.group = group;
    }
}
