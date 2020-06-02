package pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.port;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.math.BigDecimal;

public class EditPortDto implements Serializable {
    @NotNull
    @Pattern(regexp = "[a-zA-ZąĄćĆęĘłŁńŃóÓśŚźŹżŻ.-]{1,63}", message = "{validation.port.name}")
    private String name;
    @NotNull
    @Pattern(regexp = "[a-zA-ZąĄćĆęĘłŁńŃóÓśŚźŹżŻ.-]{1,31}", message = "{validation.port.lake}")
    private String lake;
    @NotNull
    @Pattern(regexp = "[a-zA-ZąĄćĆęĘłŁńŃóÓśŚźŹżŻ.-]{1,31}", message = "{validation.port.nearestCity}")
    private String nearestCity;
    @NotNull
    @Digits(integer = 3, fraction = 6, message = "{validation.port.long}")
    private BigDecimal long1;
    @NotNull
    @Digits(integer = 3, fraction = 6, message = "{validation.port.lat}")
    private BigDecimal lat;

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
}
