package pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos;

public class UpdateYachtDto {

    private String name;
    private Long yachtModelId;
    private Float priceMultiplier;
    private String equipment;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getYachtModelId() {
        return yachtModelId;
    }

    public void setYachtModelId(Long yachtModelId) {
        this.yachtModelId = yachtModelId;
    }

    public Float getPriceMultipler() {
        return priceMultiplier;
    }

    public void setPriceMultipler(Float priceMultipler) {
        this.priceMultiplier = priceMultipler;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }
}
