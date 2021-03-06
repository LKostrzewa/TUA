package pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yacht;

import javax.validation.constraints.*;
import java.math.BigDecimal;

/**
 * DTO do dodawnia nowego jachtu.
 */
public class NewYachtDto {
    @NotNull(message = "{validation.not.null}")
    @Pattern(regexp = "[a-zA-ZąĄćĆęĘłŁńŃóÓśŚźŹżŻ.-]{1,31}", message = "{validation.yacht.name}")
    private String name;
    @NotNull(message = "{validation.not.null}")
    @Min(value = 1000, message = "{validation.yacht.productionYear}")
    @Max(value = 10000, message = "{validation.yacht.productionYear}")
    @Digits(integer = 4,fraction = 0, message = "{validation.yacht.productionYear}")
    private Integer productionYear;
    @NotNull(message = "{validation.not.null}")
    private Long yachtModelId;
    @NotNull(message = "{validation.not.null}")
    @Min(value = 0, message = "{validation.yacht.priceMultiplier}")
    @Max(value = 10, message = "{validation.yacht.priceMultiplier}")
    @Digits(integer = 1,fraction = 2, message = "{validation.yacht.priceMultiplier}")
    private BigDecimal priceMultiplier;
    @NotNull(message = "{validation.not.null}")
    @Pattern(regexp = "^[^=;%&'\"]+$", message = "{validation.yacht.equipment}")
    private String equipment;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(Integer productionYear) {
        this.productionYear = productionYear;
    }

    public Long getYachtModelId() {
        return yachtModelId;
    }

    public void setYachtModelId(Long yachtModelId) {
        this.yachtModelId = yachtModelId;
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
