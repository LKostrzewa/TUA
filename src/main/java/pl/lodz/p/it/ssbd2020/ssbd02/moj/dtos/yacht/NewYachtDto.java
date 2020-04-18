package pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yacht;

public class NewYachtDto {
    private String name;
    private Integer productionYear;
    private Long yachtModelId;
    private Float priceMultiplier;
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

    public Float getPriceMultiplier() {
        return priceMultiplier;
    }

    public void setPriceMultiplier(Float priceMultiplier) {
        this.priceMultiplier = priceMultiplier;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }
}
