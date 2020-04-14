package pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos;

public class NewYachtDto {

    private String name;
    private Integer produciton_year;
    private Long yachtModelId;
    private Float priceMultiplier;
    private String equipment;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getProduciton_year() {
        return produciton_year;
    }

    public void setProduciton_year(Integer produciton_year) {
        this.produciton_year = produciton_year;
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
