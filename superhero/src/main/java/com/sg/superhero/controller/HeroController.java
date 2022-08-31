package com.sg.superhero.controller;

import com.sg.superhero.dao.*;
import com.sg.superhero.dto.Hero;
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
public class HeroController {
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

    @GetMapping("heroes")
    public String displayHeroes(Model model) {
        List<Hero> heroes = heroDao.getAllHeroes();
        List<Power> powers = powerDao.getAllPower();
        model.addAttribute("heroes", heroes);
        model.addAttribute("powers", powers);
        return "heroes";
    }

    @PostMapping("addHero")
    public String addHero(Hero hero, HttpServletRequest request) {
        String[] powerIds = request.getParameterValues("powerId");
        String heroName = request.getParameter("heroName");
        String heroDescription = request.getParameter("heroDescription");

        hero.setHeroName(heroName);
        hero.setHeroDescription(heroDescription);
        List<Power> powers = new ArrayList<>();
        for(String powerId : powerIds){
            powers.add(powerDao.getPowerById(Integer.parseInt(powerId)));
        }
        hero.setPower(powers);
        heroDao.addHero(hero);

        return "redirect:/heroes";
    }

    @GetMapping("heroDetail")
    public String heroDetail(Integer id, Model model){
        Hero hero = heroDao.getHeroById(id);
        model.addAttribute("hero", hero);
        return "heroDetail";
    }

    @GetMapping("deleteHero")
    public String deleteHero(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        heroDao.deleteHeroById(id);

        return "redirect:/heroes";
    }

    @GetMapping("editHero")
    public String editHero(Integer id, Model model) {
        Hero hero = heroDao.getHeroById(id);
        List<Power> powers = powerDao.getAllPower();
        model.addAttribute("hero", hero);
        model.addAttribute("powers", powers);
        return "editHero";
    }

    @PostMapping("editHero")
    public String performEditHero(Hero hero, HttpServletRequest request) {
        String[] powerIds = request.getParameterValues("powerId");

        hero.setHeroName(request.getParameter("heroName"));
        hero.setHeroDescription(request.getParameter("heroDescription"));

        List<Power> powers = new ArrayList<>();
        for(String powerId: powerIds){
            powers.add(powerDao.getPowerById(Integer.parseInt(powerId)));
        }
        hero.setPower(powers);
        heroDao.updateHero(hero);

        return "redirect:/heroes";
    }

}