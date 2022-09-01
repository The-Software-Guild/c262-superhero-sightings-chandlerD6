package com.sg.superhero.dao;

import com.sg.superhero.dto.Hero;
import com.sg.superhero.dto.Location;
import com.sg.superhero.dto.Organization;
import com.sg.superhero.dto.Power;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrganizationDaoDB implements OrganizationDao{

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Organization getOrgById(int id) {
        try {
            final String SELECT_ORG_BY_ID = "SELECT * FROM organization WHERE orgId = ?";
            Organization org = jdbc.queryForObject(SELECT_ORG_BY_ID, new OrganizationMapper(), id);
            org.setHeroes(getHeroesForOrg(id));
            associatePowers(org.getHeroes());
            return org;
        } catch(DataAccessException ex) {
            return null;
        }
    }

    private List<Hero> getHeroesForOrg(int id) {
        final String SELECT_HEROES_FOR_ORG = "SELECT h.* FROM hero h "
                + "JOIN orgMembers om ON om.heroId = h.heroId WHERE om.orgId = ?";
        return jdbc.query(SELECT_HEROES_FOR_ORG, new HeroDaoDB.HeroMapper(), id);
    }

    @Override
    public List<Organization> getAllOrgs() {
        final String SELECT_ALL_ORGS = "SELECT * FROM organization";
        List<Organization> orgs = jdbc.query(SELECT_ALL_ORGS, new OrganizationMapper());
        associateOrgsAndHeroes(orgs);
        return orgs;
    }

    private List<Power> getPowersForHero(int id) {
        final String SELECT_POWERS_FOR_HERO = "SELECT p.* FROM power p "
                + "JOIN heroPowers hp ON hp.powerId = p.powerId WHERE hp.heroId = ?";
        return jdbc.query(SELECT_POWERS_FOR_HERO, new PowerDaoDB.PowerMapper(), id);
    }
    private void associatePowers(List<Hero> heroes){
        for (Hero hero : heroes){
            hero.setPower(getPowersForHero(hero.getHeroId()));
        }
    }

    private void associateOrgsAndHeroes(List<Organization> orgs){
        for(Organization org : orgs){
            org.setHeroes(getHeroesForOrg(org.getOrgId()));
            associatePowers(org.getHeroes());
        }
    }


    @Override
    @Transactional
    public Organization addOrg(Organization org) {
        final String INSERT_ORG = "INSERT INTO organization(orgName, orgDescription, contactInfo) "
                + "VALUES(?,?,?)";
        jdbc.update(INSERT_ORG,
                org.getOrgName(),
                org.getOrgDescription(),
                org.getContactInfo());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        org.setOrgId(newId);
        insertOrgMember(org);
        return org;

    }

    private void insertOrgMember(Organization org){
        final String INSERT_ORG_MEM = "INSERT INTO "
                + "orgMembers(heroId, orgId) VALUES(?,?)";
        if(org.getHeroes() != null) {
            for (Hero hero : org.getHeroes()) {
                jdbc.update(INSERT_ORG_MEM,
                        hero.getHeroId(),
                        org.getOrgId());
            }
        }
        else
        {
            org.setHeroes(new ArrayList<Hero>());
        }
    }

    @Override
    @Transactional
    public void updateOrg(Organization org) {
        final String UPDATE_ORG = "UPDATE organization SET orgName = ?, orgDescription = ?, "
                + "contactInfo = ? WHERE orgId = ?";
        jdbc.update(UPDATE_ORG,
                org.getOrgName(),
                org.getOrgDescription(),
                org.getContactInfo(),
                org.getOrgId());

        final String DELETE_ORG_MEMS = "DELETE FROM orgMembers WHERE orgId = ?";
        jdbc.update(DELETE_ORG_MEMS, org.getOrgId());
        insertOrgMember(org);
    }

    @Override
    @Transactional
    public void deleteOrgById(int id) {
        final String DELETE_MEMBERS = "DELETE ms.* FROM orgMembers ms "
                + "JOIN organization o ON ms.orgId = o.orgId WHERE o.orgId = ?";
        jdbc.update(DELETE_MEMBERS, id);

        final String DELETE_ORG = "DELETE FROM organization WHERE orgId = ?";
        jdbc.update(DELETE_ORG, id);
    }

    @Override
    public List<Hero> getHeroesByOrg(Organization org){
        final String GET_HEROES_BY_ORG = "SELECT h.heroId, h.heroName, h.heroDescription " +
                "FROM orgMembers om " +
                "JOIN organization o ON om.orgId=o.orgID " +
                "JOIN hero h ON h.heroId=om.heroId " +
                "WHERE o.orgId= ? ";
        List<Hero> heroes = jdbc.query(GET_HEROES_BY_ORG, new HeroDaoDB.HeroMapper(), org.getOrgId());
        associatePowers(heroes);
        return heroes;
    }

    @Override
    public List<Organization> getOrgsByHero(Hero hero){
        final String GET_ORGS_BY_HERO = "SELECT o.orgId, o.orgName, o.orgDescription, o.contactInfo " +
                "FROM orgMembers om " +
                "JOIN organization o ON om.orgId=o.orgID " +
                "JOIN hero h ON h.heroId=om.heroId " +
                "WHERE h.heroId= ?";
        List<Organization> orgs = jdbc.query(GET_ORGS_BY_HERO, new OrganizationMapper(), hero.getHeroId());
        associateOrgsAndHeroes(orgs);
        return orgs;
    }



    public static final class OrganizationMapper implements RowMapper<Organization> {

        @Override
        public Organization mapRow(ResultSet rs, int index) throws SQLException {
            Organization org = new Organization();
            org.setOrgId(rs.getInt("orgId"));
            org.setOrgName(rs.getString("orgName"));
            org.setOrgDescription(rs.getString("orgDescription"));
            org.setContactInfo(rs.getString("contactInfo"));

            return org;
        }
    }

}
