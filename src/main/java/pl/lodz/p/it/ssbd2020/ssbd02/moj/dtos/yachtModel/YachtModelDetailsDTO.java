package pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yachtModel;

public class YachtModelDetailsDTO {
    private String manufacturer;
    private String model;
    private Integer capacity;
    private Float basicPrice;
    private Boolean active;
    private String description;

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

    public Float getBasicPrice() {
        return basicPrice;
    }

    public void setBasicPrice(Float basicPrice) {
        this.basicPrice = basicPrice;
    }

    public Boolean getActive() {
        return active;
    }



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
