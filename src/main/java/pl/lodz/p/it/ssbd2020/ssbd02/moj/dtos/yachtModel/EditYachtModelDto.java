package pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yachtModel;


import java.math.BigDecimal;

/**
 * DTO do edycji modelu jachtu.
 */
public class EditYachtModelDto {
    private Integer capacity;
    private String manufacturer;
    private String generalDescription;
    private BigDecimal basicPrice;

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

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

}
