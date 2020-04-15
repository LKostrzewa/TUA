package pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos;


import java.math.BigDecimal;

public class PortDto {

    private String name;
    private String lake;
    private String nearestCity;
    private BigDecimal long1;
    private BigDecimal lat;
    private Boolean active;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLake() {
        return lake;
    }

    public void setLake(String lake) {
        this.lake = lake;
    }

    public String getNearestCity() {
        return nearestCity;
    }

    public void setNearestCity(String nearestCity) {
        this.nearestCity = nearestCity;
    }

    public BigDecimal getLong1() {
        return long1;
    }

    public void setLong1(BigDecimal long1) {
        this.long1 = long1;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
