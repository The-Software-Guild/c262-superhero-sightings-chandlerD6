package com.sg.superhero.controller;

import com.sg.superhero.dao.*;
import com.sg.superhero.dto.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class LocationController {
    @Autowired
    PowerDao powerDao;

    @Autowired
    HeroDao heroDao;

    @Autowired
    LocationDao locationDao;

    @Autowired
    OrganizationDao orgDao;

    @Autowired
    SightingDao sightingDao;

    @GetMapping("locations")
    public String displayLocations(Model model) {
        List<Location> locations = locationDao.getAllLocations();
        model.addAttribute("locations", locations);
        return "locations";
    }

    @PostMapping("addLocation")
    public String addLocation(HttpServletRequest request) {
        String locationName = request.getParameter("locationName");
        String locationDescription = request.getParameter("locationDescription");
        String locationAddress = request.getParameter("locationAddress");
        float locationLatitude = Float.valueOf(request.getParameter("locationLatitude"));
        float locationLongitude = Float.valueOf(request.getParameter("locationLongitude"));

        Location location = new Location();
        location.setLocName(locationName);
        location.setLocDescription(locationDescription);
        location.setLocAddress(locationAddress);
        location.setLocLat(locationLatitude);
        location.setLocLong(locationLongitude);

        locationDao.addLocation(location);

        return "redirect:/locations";
    }

    @GetMapping("deleteLocation")
    public String deleteLocation(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        locationDao.deleteLocationById(id);

        return "redirect:/locations";
    }

    @GetMapping("editLocation")
    public String editLocation(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Location location = locationDao.getLocationById(id);

        model.addAttribute("location", location);
        return "editLocation";
    }

    @PostMapping("editLocation")
    public String performEditLocation(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("locationId"));
        Location location = locationDao.getLocationById(id);

        location.setLocName(request.getParameter("locationName"));
        location.setLocDescription(request.getParameter("locationDescription"));
        location.setLocAddress(request.getParameter("locationAddress"));
        location.setLocLat(Float.valueOf(request.getParameter("locationLatitude")));
        location.setLocLong(Float.valueOf(request.getParameter("locationLongitude")));

        locationDao.updateLocation(location);

        return "redirect:/locations";
    }

}
