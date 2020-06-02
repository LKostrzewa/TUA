package pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.port;

/**
 * DTO portu.
 */
public class PortDto {
    private Long id;
    private String name;
    private String lake;
    private String nearestCity;
    private Boolean active;

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

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
