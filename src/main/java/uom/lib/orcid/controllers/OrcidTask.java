package uom.lib.orcid.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import uom.lib.orcid.services.*;
import uom.lib.orcid.model.*;

@Component
public class OrcidTask {
    
    private OrcidService orcidService;
    public void setOrcidService(OrcidService orcidService) {this.orcidService = orcidService;}
    
    private ProfileService profileService;
    public void setProfileService(ProfileService profileService) {this.profileService = profileService;}
    
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    
    // Task executes at a maximum once every 60 seconds after an initial delay of 60 seconds
    @Scheduled(initialDelay=60000, fixedRate=60000)
    public void reportCurrentTime() {
        Date now = new Date();
        System.out.println("ORCID update @ " + dateFormat.format(now));
        
        try {
            ArrayList<String> orcids = orcidService.getStaleOrcids();
            System.out.println("Got " + orcids.size() + " stale orcids");
            
            for (String orcidId : orcids) {
                
                // Process a stale orcid record
                
                try {
                    System.out.println("Updating ORCID [" + orcidId + "]");
                    
                    // Query the API endpoint for profile information
                    Profile profile = profileService.getProfile(orcidId);
                    
                    // Record some statistics about our API usage
                    long bytes = 0;
                    if (profile.size != null) {
                        bytes = profile.size;
                    }
                    orcidService.updateStatistics(1, bytes);
                    
                    // Time of this update
                    Date timestamp = new Date();
                    
                    // Process works records
                    for (Work work : profile.getWorks()) {
                        orcidService.updateWorks(orcidId, work, timestamp);
                    }
                    
                    // Update the local copy of the orcid record (and mark as not stale)
                    orcidService.updateOrcid(orcidId,
                            profile.lastModified,
                            timestamp,
                            profile.getGivenNames(),
                            profile.getFamilyName());
                    
                } catch (Exception e) {
                    e.printStackTrace();
                    try {
                        // Try to record an error against this record
                        orcidService.markOrcidError(orcidId);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
