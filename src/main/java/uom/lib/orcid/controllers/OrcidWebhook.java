package uom.lib.orcid.controllers;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrcidWebhook {
    
    // Webhook endpoint
    @RequestMapping(value = "/{orcidid}/update", method = RequestMethod.POST)
    public ResponseEntity<?> notifyOrcid(@PathVariable("orcidid") String orcidId) {
        
        System.out.println("Notification received for ORCID [" + orcidId + "]");
        
        // Validate the ORCID format?
        
        // Mark the ORCID for processing
        // ...
        
        // Return HTTP 204 "No Content"
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}