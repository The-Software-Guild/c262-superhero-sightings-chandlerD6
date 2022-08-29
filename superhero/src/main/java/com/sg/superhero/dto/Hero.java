package com.sg.superhero.dto;

import java.util.Objects;

public class Hero {

    private int heroId;
    private String heroName;
    private String heroDescription;
    private String power;

    public int getHeroId() {
        return heroId;
    }

    public void setHeroId(int heroId) {
        this.heroId = heroId;
    }

    public String getHeroName() {
        return heroName;
    }

    public void setHeroName(String heroName) {
        this.heroName = heroName;
    }

    public String getHeroDescription() {
        return heroDescription;
    }

    public void setHeroDescription(String heroDescription) {
        this.heroDescription = heroDescription;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hero hero = (Hero) o;
        return heroId == hero.heroId && Objects.equals(heroName, hero.heroName) && Objects.equals(heroDescription, hero.heroDescription) && Objects.equals(power, hero.power);
    }

    @Override
    public int hashCode() {
        return Objects.hash(heroId, heroName, heroDescription, power);
    }
}
