package pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yachtModel;

import java.math.BigDecimal;

/**
 * DTO do dodania nowego modelu jachtu.
 */
public class NewYachtModelDto {
    private String manufacturer;
    private String model;
    private Integer capacity;
    private String description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getBasicPrice() {
        return basicPrice;
    }

    public void setBasicPrice(BigDecimal basicPrice) {
        this.basicPrice = basicPrice;
    }
}
