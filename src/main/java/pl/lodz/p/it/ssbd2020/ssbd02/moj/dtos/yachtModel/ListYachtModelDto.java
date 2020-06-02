package pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yachtModel;

import java.math.BigDecimal;

/**
 * DTO do wyświetlenia listy wszystkich modeli jachtów.
 */
public class ListYachtModelDto {
    private Long id;
    private String manufacturer;
    private String model;
    private Integer capacity;
    private BigDecimal basicPrice;
    private Boolean active;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public BigDecimal getBasicPrice() {
        return basicPrice;
    }

    public void setBasicPrice(BigDecimal basicPrice) {
        this.basicPrice = basicPrice;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
