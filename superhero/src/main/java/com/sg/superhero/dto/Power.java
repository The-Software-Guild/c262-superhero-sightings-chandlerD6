package com.sg.superhero.dto;

import java.util.Objects;

public class Power {
    private int powerId;
    private String powerName;
    private String powerDescription;

    public int getPowerId() {
        return powerId;
    }

    public void setPowerId(int powerId) {
        this.powerId = powerId;
    }

    public String getPowerName() {
        return powerName;
    }

    public void setPowerName(String powerName) {
        this.powerName = powerName;
    }

    public String getPowerDescription() {
        return powerDescription;
    }

    public void setPowerDescription(String powerDescription) {
        this.powerDescription = powerDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Power power = (Power) o;
        return powerId == power.powerId && Objects.equals(powerName, power.powerName) && Objects.equals(powerDescription, power.powerDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(powerId, powerName, powerDescription);
    }
}
