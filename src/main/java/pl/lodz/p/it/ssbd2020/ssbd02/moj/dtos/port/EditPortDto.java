package pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.port;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO do edycji portu.
 */
public class EditPortDto implements Serializable {
    @NotNull(message = "{validation.not.null}")
    @Pattern(regexp = "[\\sa-zA-ZąĄćĆęĘłŁńŃóÓśŚźŹżŻ.-]{1,63}", message = "{validation.port.name}")
    private String name;
    @NotNull(message = "{validation.not.null}")
    @Pattern(regexp = "[\\sa-zA-ZąĄćĆęĘłŁńŃóÓśŚźŹżŻ.-]{1,31}", message = "{validation.port.lake}")
    private String lake;
    @NotNull(message = "{validation.not.null}")
    @Pattern(regexp = "[a-zA-ZąĄćĆęĘłŁńŃóÓśŚźŹżŻ.-]{1,31}", message = "{validation.port.nearestCity}")
    private String nearestCity;
    @NotNull(message = "{validation.not.null}")
    @Digits(integer = 3, fraction = 6, message = "{validation.port.long}")
    @Min(value = 0,message = "{validation.port.long}")
    private BigDecimal long1;
    @NotNull(message = "{validation.not.null}")
    @Digits(integer = 3, fraction = 6, message = "{validation.port.lat}")
    @Min(value = 0,message = "{validation.port.lat}")
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
