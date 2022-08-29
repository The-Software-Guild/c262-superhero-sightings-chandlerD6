package com.sg.superhero.dao;

import com.sg.superhero.dto.Location;
import com.sg.superhero.dto.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrganizationDaoDB implements OrganizationDao{

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Organization getOrgById(int id) {
        return null;
    }

    @Override
    public List<Organization> getAllOrgs() {
        return null;
    }

    @Override
    public Organization addOrg(Organization org) {
        return null;
    }

    @Override
    public void updateOrg(Organization org) {

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
