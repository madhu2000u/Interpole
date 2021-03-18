package com.madhu.interpol.DataClasses;

import java.util.List;

public class SpecificForceData {


    private String description;

    private String url;

    private List<SpecificForceEngagementMethod> specificForceEngagementMethods = null;

    private String telephone;

    private String id;

    private String name;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<SpecificForceEngagementMethod> getSpecificForceEngagementMethods() {
        return specificForceEngagementMethods;
    }

    public void setSpecificForceEngagementMethods(List<SpecificForceEngagementMethod> specificForceEngagementMethods) {
        this.specificForceEngagementMethods = specificForceEngagementMethods;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
