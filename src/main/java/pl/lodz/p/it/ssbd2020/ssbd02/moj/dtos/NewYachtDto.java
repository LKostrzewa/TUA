package pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos;

public class NewYachtDto {

    private String name;
    private Integer produciton_year;
    private Long yachtModelId;
    private Float priceMultipler;
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

    public Float getPriceMultipler() {
        return priceMultipler;
    }

    public void setPriceMultipler(Float priceMultipler) {
        this.priceMultipler = priceMultipler;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }
}
