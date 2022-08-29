package com.sg.superhero.dao;

import com.sg.superhero.dto.Location;

import java.util.List;

public interface LocationDao {
    Location getLocationById(int id);
    List<Location> getAllLocations();
    Location addLocation(Location loc);
    void updateLocation(Location loc);
    void deleteLocationById(int id);
}
