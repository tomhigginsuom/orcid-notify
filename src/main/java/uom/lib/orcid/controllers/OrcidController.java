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
    
    private OrcidService orcidService;
    public void setOrcidService(OrcidService orcidService) {this.orcidService = orcidService;}
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getIndex(ModelMap model) throws Exception {
        
        ArrayList<Profile> orcids = orcidService.getOrcids();
        System.out.println("Got " + orcids.size() + " orcids");
        
        Collections.sort(orcids, Collections.reverseOrder());
        
        model.addAttribute("profiles", orcids);
        
        return "orcid/index";
    }
    
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String getNew(ModelMap model) throws Exception {
        
        ArrayList<Work> works = orcidService.getNewOrcidWorksForYear(2016);
        ArrayList<WorkGroup> workGroups = orcidService.groupWorks(works);
        
        Collections.sort(workGroups, Collections.reverseOrder());
        
        model.addAttribute("workGroups", workGroups);
        
        return "orcid/new";
    }
    
    @RequestMapping(value = "/{orcidid}", method = RequestMethod.GET)
    public String getOrcid(ModelMap model, @PathVariable("orcidid") String orcidId) throws Exception {
        
        // Validate the ORCID format?
        
        Profile profile = orcidService.getOrcidProfile(orcidId);
        ArrayList<Work> works = orcidService.getOrcidWorks(orcidId);
        ArrayList<WorkGroup> workGroups = orcidService.groupWorks(works);
        
        Collections.sort(workGroups, Collections.reverseOrder());
        
        model.addAttribute("profile", profile);
        model.addAttribute("workGroups", workGroups);
        return "orcid/orcid";
    }
}