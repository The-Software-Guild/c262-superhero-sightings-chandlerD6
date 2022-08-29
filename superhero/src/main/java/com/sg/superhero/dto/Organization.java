package com.sg.superhero.dto;

import java.util.List;
import java.util.Objects;

public class Organization {
    private int orgId;
    private String orgName;
    private String orgDescription;
    private String contactInfo;

    private List<Hero> heroes;

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgDescription() {
        return orgDescription;
    }

    public void setOrgDescription(String orgDescription) {
        this.orgDescription = orgDescription;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public List<Hero> getHeroes() {
        return heroes;
    }

    public void setHeroes(List<Hero> heroes) {
        this.heroes = heroes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return orgId == that.orgId && Objects.equals(orgName, that.orgName) && Objects.equals(orgDescription, that.orgDescription) && Objects.equals(contactInfo, that.contactInfo) && Objects.equals(heroes, that.heroes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orgId, orgName, orgDescription, contactInfo, heroes);
    }
}
