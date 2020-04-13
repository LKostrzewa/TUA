package pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos;


public class YachtDto {

    private Long id;
    private String name;
    private Integer produciton_year;
    private Long yachtModelId;
    private Float priceMultiplier;
    private Long currentPortId;
    private String equipment;
    private Float avgRating;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
        return priceMultiplier;
    }

    public void setPriceMultipler(Float priceMultipler) {
        this.priceMultiplier = priceMultipler;
    }

    public Long getCurrentPortId() {
        return currentPortId;
    }

    public void setCurrentPortId(Long currentPortId) {
        this.currentPortId = currentPortId;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public Float getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(Float avgRating) {
        this.avgRating = avgRating;
    }
}
