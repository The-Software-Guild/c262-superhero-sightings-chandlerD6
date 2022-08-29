package com.sg.superhero.dto;

import java.sql.Timestamp;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.Objects;

public class Sighting {

    private int sightingId;
    private int heroId;
    private int locId;
    private Timestamp sightingDate;

    public int getSightingId() {
        return sightingId;
    }

    public void setSightingId(int sightingId) {
        this.sightingId = sightingId;
    }

    public int getHeroId() {
        return heroId;
    }

    public void setHeroId(int heroId) {
        this.heroId = heroId;
    }

    public int getLocId() {
        return locId;
    }

    public void setLocId(int locId) {
        this.locId = locId;
    }

    public Timestamp getSightingDate() {
        return sightingDate;
    }

    public void setSightingDate(Timestamp sightingDate) {
        this.sightingDate = sightingDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sighting sighting = (Sighting) o;
        return sightingId == sighting.sightingId && heroId == sighting.heroId && locId == sighting.locId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sightingId, heroId, locId, sightingDate);
    }
}
