package pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yacht;

import javax.validation.constraints.*;
import java.math.BigDecimal;

/**
 * DTO do edycji jachtu.
 */
public class EditYachtDto {
    @NotNull
    @Pattern(regexp = "[a-zA-ZąĄćĆęĘłŁńŃóÓśŚźŹżŻ.]{1,31}", message = "{validation.yacht.name}")
    private String name;
    @NotNull
    @Min(value = 0, message = "{validation.yacht.productionYear}")
    @Max(value = 10, message = "{validation.yacht.productionYear}")
    @Digits(integer = 1,fraction = 2, message = "{validation.yacht.priceMultiplier}")
    private BigDecimal priceMultiplier;
    @NotNull
    @Pattern(regexp = "[a-zA-ZąĄćĆęĘłŁńŃóÓśŚźŹżŻ.\n]{1,2048}", message = "{validation.yacht.equipment}")
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
