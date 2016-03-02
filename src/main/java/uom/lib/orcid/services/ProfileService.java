package uom.lib.orcid.services;

import java.io.*;
import java.net.*;
import java.util.*;
import com.google.gson.*;
import uom.lib.orcid.model.Work;
import uom.lib.orcid.model.OrcidResponse;
import uom.lib.orcid.model.Profile;

// A service to retrieve ORCID profile and works data from the public ORCID API

public class ProfileService {
    
    private String endpoint;
    public void setEndpoint(String endpoint) {this.endpoint = endpoint;};
    
    // For now only try to handle profiles which are < 10MB
    private static final long maxProfileSize = 10 * 1024 * 1024;
    
    public Profile getProfile(String orcidId) throws Exception {
        
        Profile profile = new Profile(orcidId);
        ArrayList<Work> works = new ArrayList<>();
        
        String url = endpoint + profile.identifier + "/orcid-profile";
        System.out.println("query: " + url);

        String responseText = query(url);
        System.out.println("response: " + responseText.length() + " bytes");
        profile.size = responseText.length();

        OrcidResponse response = new Gson().fromJson(responseText, OrcidResponse.class);

        if (response.profile != null &&
            response.profile.bio != null &&
            response.profile.bio.personalDetails != null) {

            if (response.profile.bio.personalDetails.givenNames != null) {
                profile.setGivenNames(response.profile.bio.personalDetails.givenNames.value);
            }

            if (response.profile.bio.personalDetails.familyName != null) {
                profile.setFamilyName(response.profile.bio.personalDetails.familyName.value);
            }
        }

        if (response.profile != null &&
            response.profile.history != null &&
            response.profile.history.lastModifiedDate != null) {
            profile.setLastModified(new Date(response.profile.history.lastModifiedDate.value));
        }

        if (response.profile == null ||
            response.profile.activities == null ||
            response.profile.activities.works == null ||
            response.profile.activities.works.work == null) {
            System.out.println("No works available (null)");
        } else {
            for (OrcidResponse.Work work : response.profile.activities.works.work) {

                Integer year = null;
                if (work.publicationDate != null &&
                    work.publicationDate.year != null &&
                    work.publicationDate.year.value != null) {
                    year = work.publicationDate.year.value;
                }

                Integer month = null;
                if (work.publicationDate != null &&
                    work.publicationDate.month != null &&
                    work.publicationDate.month.value != null) {
                    month = work.publicationDate.month.value;
                }

                Integer day = null;
                if (work.publicationDate != null &&
                    work.publicationDate.day != null &&
                    work.publicationDate.day.value != null) {
                    day = work.publicationDate.day.value;
                }

                String title = null;
                if (work.workTitle != null &&
                    work.workTitle.title != null &&
                    work.workTitle.title.value != null) {
                    title = work.workTitle.title.value;
                }

                if (work.workExternalIdentifiers == null ||
                    work.workExternalIdentifiers.workExternalIdentifier == null) {
                    works.add(new Work(work.putCode,
                                       title,
                                       year,
                                       month,
                                       day,
                                       null,
                                       null));
                } else {
                    for (OrcidResponse.WorkExternalIdentifier workId : work.workExternalIdentifiers.workExternalIdentifier) {
                        works.add(new Work(work.putCode,
                                       title,
                                       year,
                                       month,
                                       day,
                                       workId.workExternalIdentifierType,
                                       workId.workExternalIdentifierId.value));
                    }
                }
            }
        }
        
        profile.works = works;
        return profile;
    }
    
    private String query(String url) throws Exception {

        URL queryURL = new URL(url);
        HttpURLConnection hc = (HttpURLConnection)queryURL.openConnection();
        hc.setRequestMethod("GET");
        hc.setRequestProperty("Accept", "application/json");
        
        // Getting the input stream implicity connects to the server
        InputStream is = hc.getInputStream();
        
        final char[] buffer = new char[4096];
        final StringBuilder sb = new StringBuilder();
        int n;
        int count = 0;
        
        try (Reader in = new InputStreamReader(is, "UTF-8")) {
            while ((n = in.read(buffer, 0, buffer.length)) != -1) {
                count += n;
                if (count > maxProfileSize) {
                    throw new Exception("maxProfileSize exceeded");
                }
                
                sb.append(buffer, 0, n);
            }
        }
        
        return sb.toString();
    }
}
