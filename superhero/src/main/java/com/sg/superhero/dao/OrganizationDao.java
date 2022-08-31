package com.sg.superhero.dao;

import com.sg.superhero.dto.Hero;
import com.sg.superhero.dto.Organization;

import java.util.List;

public interface OrganizationDao {
    Organization getOrgById(int id);
    List<Organization> getAllOrgs();
    Organization addOrg(Organization org);
    void updateOrg(Organization org);
    void deleteOrgById(int id);

    List<Hero> getHeroesByOrg(Organization org);
    List<Organization> getOrgsByHero(Hero hero);


}
