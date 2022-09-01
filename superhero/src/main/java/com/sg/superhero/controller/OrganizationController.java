package com.sg.superhero.controller;

import com.sg.superhero.dao.*;
import com.sg.superhero.dto.Hero;
import com.sg.superhero.dto.Organization;
import com.sg.superhero.dto.Power;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class OrganizationController {
    @Autowired
    HeroDao heroDao;

    @Autowired
    PowerDao powerDao;

    @Autowired
    LocationDao locDao;

    @Autowired
    OrganizationDao orgDao;

    @Autowired
    SightingDao sightingDao;

    @GetMapping("organizations")
    public String displayOrgs(Model model) {
        List<Organization> organizations = orgDao.getAllOrgs();
        List<Hero> heroes = heroDao.getAllHeroes();
        model.addAttribute("organizations", organizations);
        model.addAttribute("heroes", heroes);
        return "organizations";
    }

    @PostMapping("addOrganization")
    public String addOrg(Organization org, HttpServletRequest request) {
        String[] heroIds = request.getParameterValues("heroId");
        String orgName = request.getParameter("orgName");
        String orgDescription = request.getParameter("orgDescription");
        String contactInfo = request.getParameter("contactInfo");

        org.setOrgName(orgName);
        org.setOrgDescription(orgDescription);
        org.setContactInfo(contactInfo);

        List<Hero> heroes = new ArrayList<>();
        for (String heroId : heroIds) {
            Hero hero = heroDao.getHeroById(Integer.parseInt(heroId));
            heroes.add(hero);
        }
        org.setHeroes(heroes);
        orgDao.addOrg(org);

        return "redirect:/organizations";
    }

    @GetMapping("orgDetail")
    public String orgDetail(Integer id, Model model) {
        Organization org = orgDao.getOrgById(id);
        model.addAttribute("org", org);
        return "orgDetail";
    }

    @GetMapping("deleteOrg")
    public String deleteOrg(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        orgDao.deleteOrgById(id);

        return "redirect:/organizations";
    }

    @GetMapping("editOrg")
    public String editOrg(Integer id, Model model) {
        Organization org = orgDao.getOrgById(id);
        List<Hero> heroes = heroDao.getAllHeroes();
        model.addAttribute("org", org);
        model.addAttribute("heroes", heroes);
        return "editOrg";
    }

    @PostMapping("editOrg")
    public String performEditOrg(Organization org, HttpServletRequest request) {
        String[] heroIds = request.getParameterValues("heroId");

        org.setOrgName(request.getParameter("orgName"));
        org.setOrgDescription(request.getParameter("orgDescription"));
        org.setContactInfo(request.getParameter("contactInfo"));

        List<Hero> heroes = new ArrayList<>();
        for (String heroId : heroIds) {
            heroes.add(heroDao.getHeroById(Integer.parseInt(heroId)));
        }
        org.setHeroes(heroes);
        orgDao.updateOrg(org);

        return "redirect:/organizations";
    }

}