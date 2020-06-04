package pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yacht;

import java.math.BigDecimal;

/**
 * DTO do edycji jachtu.
 */
public class EditYachtDto {
    private String name;
    private BigDecimal priceMultiplier;
    private String equipment;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPriceMultiplier() {
        return priceMultiplier;
    }

    public void setPriceMultiplier(BigDecimal priceMultiplier) {
        this.priceMultiplier = priceMultiplier;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }
}
