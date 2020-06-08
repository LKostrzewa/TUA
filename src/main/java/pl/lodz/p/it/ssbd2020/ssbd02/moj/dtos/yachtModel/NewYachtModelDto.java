package pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yachtModel;

import javax.validation.constraints.*;
import java.math.BigDecimal;

/**
 * DTO do dodania nowego modelu jachtu.
 */
public class NewYachtModelDto {
    @Pattern(regexp = "[a-zA-ZąĄćĆęĘłŁńŃóÓśŚźŹżŻ.-]{2,32}", message = "{validation.yachtModel.manufacturer}")
    private String manufacturer;
    @Size(min = 2, message = "{validation.yachtModel.model}")
    @Pattern(regexp = "^[^=;%&'\"/\\s\\\\]+$", message = "{validation.invalidCharacter}")
    private String model;
    @Min(value = 1, message = "{validation.yachtModel.capacity}")
    private Integer capacity;
    @Size(min = 10, max = 4096, message = "{validation.yachtModel.description}")
    @Pattern(regexp = "^[^=;%&'\"]+$", message = "{validation.invalidCharacter}")
    private String generalDescription;
    @DecimalMin(value = "0.0", inclusive = false, message = "{validation.yachtModel.price.value}")
    @Digits(integer = 8, fraction = 2, message = "{validation.yachtModel.price.digits}")
    private BigDecimal basicPrice;

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getGeneralDescription() {
        return generalDescription;
    }

    public void setGeneralDescription(String generalDescription) {
        this.generalDescription = generalDescription;
    }

    public BigDecimal getBasicPrice() {
        return basicPrice;
    }

    public void setBasicPrice(BigDecimal basicPrice) {
        this.basicPrice = basicPrice;
    }
}
