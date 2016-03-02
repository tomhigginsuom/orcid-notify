package uom.lib.orcid.controllers;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import uom.lib.orcid.services.OrcidService;

@RestController
public class OrcidWebhook {
    
    private OrcidService orcidService;
    public void setOrcidService(OrcidService orcidService) {this.orcidService = orcidService;}
    
    // Webhook endpoint
    @RequestMapping(value = "/{orcidid}/update", method = RequestMethod.POST)
    public ResponseEntity<?> notifyOrcid(@PathVariable("orcidid") String orcidId) {
        
        System.out.println("Web hook called for ORCID [" + orcidId + "]");
        
        // Validate the ORCID format?
        // ...
        
        // Mark the ORCID for processing
        orcidService.markOrcidStale(orcidId);
        
        // Return HTTP 204 "No Content"
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}