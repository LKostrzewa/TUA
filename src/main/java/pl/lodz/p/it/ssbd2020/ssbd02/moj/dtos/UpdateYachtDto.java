package pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos;

public class UpdateYachtDto {

    private String name;
    private Float priceMultiplier;
    private String equipment;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
