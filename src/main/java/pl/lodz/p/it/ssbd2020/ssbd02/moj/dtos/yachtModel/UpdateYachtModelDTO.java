package pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yachtModel;

public class UpdateYachtModelDTO {
    private Integer capacity;
    private String generalDescription;
    private Float basicPrice;

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

    public Float getBasicPrice() {
        return basicPrice;
    }

    public void setBasicPrice(Float basicPrice) {
        this.basicPrice = basicPrice;
    }
}
