package pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yacht;

import java.math.BigDecimal;

/**
 * DTO do wyświetlenia listy jachtów.
 */
public class YachtListDto {
    private Long id;
    private String name;
    private String yachtModelName;
    private String currentPortName;
    private Float avgRating;
    private boolean active;

    public YachtListDto(Long id, String name, String yachtModelName, String currentPortName, Float avgRating, boolean active) {
        this.id = id;
        this.name = name;
        this.yachtModelName = yachtModelName;
        this.currentPortName = currentPortName;
        this.avgRating = avgRating;
        this.active = active;
    }

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

    public String getYachtModelName() {
        return yachtModelName;
    }

    public void setYachtModelName(String yachtModelName) {
        this.yachtModelName = yachtModelName;
    }

    public String getCurrentPortName() {
        return currentPortName;
    }

    public void setCurrentPortName(String currentPortName) {
        this.currentPortName = currentPortName;
    }

    public Float getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(Float avgRating) {
        this.avgRating = avgRating;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
