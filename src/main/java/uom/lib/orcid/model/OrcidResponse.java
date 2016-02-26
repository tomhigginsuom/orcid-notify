package uom.lib.orcid.model;

import com.google.gson.annotations.SerializedName;

public class OrcidResponse {

    @SerializedName("orcid-profile")
    public Profile profile;

    public static class Profile {
        @SerializedName("orcid-bio")
        public OrcidBio bio;
        
        @SerializedName("orcid-history")
        public History history;
        
        @SerializedName("orcid-activities")
        public Activities activities;
    }
    
    public static class OrcidBio {
        @SerializedName("personal-details")
        public PersonalDetails personalDetails;
    }
    
    public static class PersonalDetails {
        @SerializedName("given-names")
        public NameValue givenNames;
        
        @SerializedName("family-name")
        public NameValue familyName;
    }
    
    public static class NameValue {
        @SerializedName("value")
        public String value;
        
        // Visibility
    }
    
    public static class History {
        @SerializedName("last-modified-date")
        public LongValue lastModifiedDate;
    }
    
    public static class Activities {
        @SerializedName("orcid-works")
        public Works works;
    }

    public static class Works {
        @SerializedName("orcid-work")
        public Work[] work;
    }

    public static class Work {
        @SerializedName("put-code")
        public Integer putCode;

        @SerializedName("publication-date")
        public PublicationDate publicationDate;

        @SerializedName("work-title")
        public WorkTitle workTitle;
        
        @SerializedName("work-external-identifiers")
        public WorkExternalIdentifiers workExternalIdentifiers;
    }

    public static class PublicationDate {
        @SerializedName("year")
        public IntegerValue year;
        
        @SerializedName("month")
        public IntegerValue month;
        
        @SerializedName("day")
        public IntegerValue day;
    }

    public static class WorkTitle {
        @SerializedName("title")
        public StringValue title;

        @SerializedName("subtitle")
        public StringValue subtitle;
    }
    
    public static class WorkExternalIdentifiers {
        @SerializedName("work-external-identifier")
        public WorkExternalIdentifier[] workExternalIdentifier;
    }
    
    public static class WorkExternalIdentifier {
        @SerializedName("work-external-identifier-type")
        public String workExternalIdentifierType;
        
        @SerializedName("work-external-identifier-id")
        public StringValue workExternalIdentifierId;
    }

    public static class StringValue {
        @SerializedName("value")
        public String value;
    }
    
    public static class IntegerValue {
        @SerializedName("value")
        public Integer value;
    }
    
    public static class LongValue {
        @SerializedName("value")
        public Long value;
    }
}
