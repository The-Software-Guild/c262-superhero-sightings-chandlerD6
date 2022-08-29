package com.sg.superhero.dto;

import java.util.Objects;

public class Location {
    private int locId;
    private String locName;
    private String locDescription;
    private String locAddress;
    private float locLat;
    private float locLong;

    public int getLocId() {
        return locId;
    }

    public void setLocId(int locId) {
        this.locId = locId;
    }

    public String getLocName() {
        return locName;
    }

    public void setLocName(String locName) {
        this.locName = locName;
    }

    public String getLocDescription() {
        return locDescription;
    }

    public void setLocDescription(String locDescription) {
        this.locDescription = locDescription;
    }

    public String getLocAddress() {
        return locAddress;
    }

    public void setLocAddress(String locAddress) {
        this.locAddress = locAddress;
    }

    public float getLocLat() {
        return locLat;
    }

    public void setLocLat(float locLat) {
        this.locLat = locLat;
    }

    public float getLocLong() {
        return locLong;
    }

    public void setLocLong(float locLong) {
        this.locLong = locLong;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return locId == location.locId && Float.compare(location.locLat, locLat) == 0 && Float.compare(location.locLong, locLong) == 0 && Objects.equals(locName, location.locName) && Objects.equals(locDescription, location.locDescription) && Objects.equals(locAddress, location.locAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(locId, locName, locDescription, locAddress, locLat, locLong);
    }
}
