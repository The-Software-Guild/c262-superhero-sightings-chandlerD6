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
    private Hero hero;
    private Location loc;

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

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public Location getLoc() {
        return loc;
    }

    public void setLoc(Location loc) {
        this.loc = loc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sighting sighting = (Sighting) o;
        return sightingId == sighting.sightingId && heroId == sighting.heroId && locId == sighting.locId && Objects.equals(hero, sighting.hero) && Objects.equals(loc, sighting.loc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sightingId, heroId, locId, sightingDate, hero, loc);
    }
}
