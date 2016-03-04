package uom.lib.orcid.model;

import java.util.ArrayList;
import java.util.Date;

// An ORCID work (grouped)
public class WorkGroup implements Comparable {
    
     // ID
    public Integer group;
    
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
    
    // Component works (identifiers)
    public ArrayList<Work> works = new ArrayList<>();
    
    public int compareTo(Object other) {
        
        WorkGroup otherWorkGroup = (WorkGroup)other;
        
        // Compare years
        if (year != null || otherWorkGroup.year != null) {
            if (otherWorkGroup.year == null) {
                return 1;
            }
            if (year == null) {
                return -1;
            }
            if (!year.equals(otherWorkGroup.year)) {
                return year - otherWorkGroup.year;
            }
        }
        
        // Compare months
        if (month != null || otherWorkGroup.month != null) {
            if (otherWorkGroup.month == null) {
                return 1;
            }
            if (month == null) {
                return -1;
            }
            if (!month.equals(otherWorkGroup.month)) {
                return month - otherWorkGroup.month;
            }
        }
        
        // Compare days
        if (day != null || otherWorkGroup.day != null) {
            if (otherWorkGroup.day == null) {
                return 1;
            }
            if (day == null) {
                return -1;
            }
            if (!day.equals(otherWorkGroup.day)) {
                return day - otherWorkGroup.day;
            }
        }
        
        // Default order by title (reverse)
        return otherWorkGroup.title.compareTo(title);
    }
    
    public WorkGroup(String orcid, Integer group, String workType, String title, Integer year, Integer month, Integer day) {
        this.orcid = orcid;
        this.group = group;
        this.workType = workType;
        this.title = title;
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public String getOrcid() {
        return orcid;
    }

    public void setOrcid(String orcid) {
        this.orcid = orcid;
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
    
    public ArrayList<Work> getWorks() {
        return works;
    }

    public void setWorks(ArrayList<Work> works) {
        this.works = works;
    }
    
    public void addWork(Work work) {
        works.add(work);
    }
}
