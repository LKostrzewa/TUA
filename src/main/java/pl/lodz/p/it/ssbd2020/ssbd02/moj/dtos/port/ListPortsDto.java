package pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.port;

import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yacht.YachtsToPortDto;

import java.math.BigDecimal;
import java.util.Collection;

/**
 * DTO do wyświetlania listy portów.
 */
public class ListPortsDto {
    private Long id;
    private String name;
    private String lake;
    private String nearestCity;
    private BigDecimal long1;
    private BigDecimal lat;
    private Boolean active;
    private Collection<YachtsToPortDto> yachts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Collection<YachtsToPortDto> getYachts() {
        return yachts;
    }

    public void setYachts(Collection<YachtsToPortDto> yachts) {
        this.yachts = yachts;
    }
}
