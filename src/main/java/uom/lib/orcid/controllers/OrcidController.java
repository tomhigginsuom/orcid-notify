package uom.lib.orcid.controllers;

import java.util.ArrayList;
import java.util.Collections;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import uom.lib.orcid.services.*;
import uom.lib.orcid.model.*;

@Controller
public class OrcidController {
    
    private WorksService worksService;
    public void setWorksService(WorksService worksService) {this.worksService = worksService;}
    
    private OrcidService orcidService;
    public void setOrcidService(OrcidService orcidService) {this.orcidService = orcidService;}
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getIndex(ModelMap model) throws Exception {
        
        ArrayList<String> orcids = orcidService.getOrcids();
        System.out.println("Got " + orcids.size() + " orcids");
        
        Collections.sort(orcids);
        
        model.addAttribute("orcids", orcids);
        
        return "orcid/index";
    }
    
    @RequestMapping(value = "/{orcidid}", method = RequestMethod.GET)
    public String getOrcid(ModelMap model, @PathVariable("orcidid") String orcidId) {
        
        // Validate the ORCID format?
        
        Profile profile = new Profile(orcidId);
        
        ArrayList<Work> works = worksService.getWorks(profile);
        System.out.println("Got " + works.size() + " works");
        
        Collections.sort(works, Collections.reverseOrder());
        
        model.addAttribute("profile", profile);
        model.addAttribute("works", works);
        return "orcid/orcid";
    }
}