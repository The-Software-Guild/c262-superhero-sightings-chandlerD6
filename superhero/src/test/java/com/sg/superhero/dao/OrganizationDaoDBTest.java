package com.sg.superhero.dao;

import com.sg.superhero.dto.Organization;
import com.sg.superhero.dto.Power;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Sql(scripts = "/scripts/dbSetup.sql")
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class OrganizationDaoDBTest {

    @Autowired
    OrganizationDao orgDao;


    @Test
    public void testAddAndGetOrganization(){
        Organization org = new Organization();
        org.setOrgName("test");
        org.setOrgDescription("test");
        org.setContactInfo("test");
        org = orgDao.addOrg(org);

        Organization fromDao = orgDao.getOrgById(org.getOrgId());

        assertEquals(org, fromDao);
    }

}
