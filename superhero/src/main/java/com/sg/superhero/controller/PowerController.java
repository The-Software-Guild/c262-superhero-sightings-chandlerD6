package com.sg.superhero.controller;

import com.sg.superhero.dao.*;
import com.sg.superhero.dto.Power;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class PowerController {
    @Autowired
    PowerDao powerDao;

    @Autowired
    HeroDao heroDao;

    @Autowired
    LocationDao locDao;

    @Autowired
    OrganizationDao orgDao;

    @Autowired
    SightingDao sightingDao;

    @GetMapping("powers")
    public String displayPowers(Model model) {
        List<Power> powers = powerDao.getAllPower();
        model.addAttribute("powers", powers);
        return "powers";
    }

    @PostMapping("addPower")
    public String addPower(HttpServletRequest request) {
        String powerName = request.getParameter("powerName");
        String powerDescription = request.getParameter("powerDescription");

        Power power = new Power();
        power.setPowerName(powerName);
        power.setPowerDescription(powerDescription);

        powerDao.addPower(power);

        return "redirect:/powers";
    }

    @GetMapping("deletePower")
    public String deletePower(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        powerDao.deletePowerById(id);

        return "redirect:/powers";
    }

    @GetMapping("editPower")
    public String editPower(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Power power = powerDao.getPowerById(id);

        model.addAttribute("power", power);
        return "editPower";
    }

    @PostMapping("editPower")
    public String performEditPower(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("powerId"));
        Power power = powerDao.getPowerById(id);

        power.setPowerName(request.getParameter("powerName"));
        power.setPowerDescription(request.getParameter("powerDescription"));

        powerDao.updatePower(power);

        return "redirect:/powers";
    }

}
