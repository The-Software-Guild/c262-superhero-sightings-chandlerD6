package com.sg.superhero.dao;

import com.sg.superhero.dto.Power;
import com.sg.superhero.dto.Sighting;

import java.util.List;

public interface PowerDao {
    Power getPowerById(int id);
    List<Power> getAllPower();
    Power addPower(Power power);
    void updatePower(Power power);
    void deletePowerById(int id);
}
