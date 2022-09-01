package com.sg.superhero.controller;

import com.sg.superhero.dao.*;
import com.sg.superhero.dto.Hero;
import com.sg.superhero.dto.Location;
import com.sg.superhero.dto.Sighting;
import com.sg.superhero.dto.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SightingController {
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

    @GetMapping("sightings")
    public String displaySightings(Model model) {
        List<Sighting> sightings = sightingDao.getAllSighting();
        List<Hero> heroes = heroDao.getAllHeroes();
        List<Location> locs  = locDao.getAllLocations();
        model.addAttribute("sightings", sightings);
        model.addAttribute("heroes", heroes);
        model.addAttribute("locations", locs);
        return "sightings";
    }

    @PostMapping("addSighting")
    public String addSighting(Sighting sighting, HttpServletRequest request) {
        String heroId = request.getParameter("heroId");
        String locId = request.getParameter("locId");
        String sightingDate = request.getParameter("sightingDate");

        sighting.setHeroId(Integer.parseInt(heroId));
        sighting.setLocId(Integer.parseInt(locId));
        sighting.setSightingDate(Timestamp.valueOf(sightingDate));
        Hero hero = heroDao.getHeroById(Integer.parseInt(heroId));
        Location loc = locDao.getLocationById(Integer.parseInt(locId));
        sighting.setHero(hero);
        sighting.setLoc(loc);
        sightingDao.addSighting(sighting);

        return "redirect:/sightings";
    }

    @GetMapping("sightingDetail")
    public String sightingDetail(Integer id, Model model) {
        Sighting sighting = sightingDao.getSightingById(id);
        model.addAttribute("sighting", sighting);
        return "sightingDetail";
    }

    @GetMapping("deleteSighting")
    public String deleteSighting(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        sightingDao.deleteSightingById(id);

        return "redirect:/sightings";
    }

    @GetMapping("editSighting")
    public String editSighting(Integer id, Model model) {
        Sighting sighting = sightingDao.getSightingById(id);
        List<Hero> heroes = heroDao.getAllHeroes();
        model.addAttribute("sighting", sighting);
        model.addAttribute("heroes", heroes);
        return "editSighting";
    }

    @PostMapping("editSighting")
    public String performEditSighting(Sighting sighting, HttpServletRequest request) {
        String[] heroIds = request.getParameterValues("heroId");

       // sighting.setSightingName(request.getParameter("sightingName"));
       // sighting.setSightingDescription(request.getParameter("sightingDescription"));
      //  sighting.setContactInfo(request.getParameter("contactInfo"));

        List<Hero> heroes = new ArrayList<>();
        for (String heroId : heroIds) {
            heroes.add(heroDao.getHeroById(Integer.parseInt(heroId)));
        }
       // sighting.setHeroes(heroes);
        sightingDao.updateSighting(sighting);

        return "redirect:/sightings";
    }

}