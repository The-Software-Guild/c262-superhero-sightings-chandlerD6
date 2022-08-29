package com.sg.superhero.dao;

import com.sg.superhero.dto.Location;
import com.sg.superhero.dto.Sighting;

import java.util.List;

public interface SightingDao {
    Sighting getSightingById(int id);
    List<Sighting> getAllSighting();
    Sighting addSighting(Sighting sighting);
    void updateSighting(Sighting sighting);
    void deleteSightingById(int id);
}
