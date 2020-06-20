package pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yachtModel;

import org.primefaces.model.StreamedContent;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.image.ImageDto;

import java.math.BigDecimal;
import java.util.List;

/**
 * DTO do wyświetlenia szczegółów modelu jachtu.
 */
public class YachtModelDetailsDto {
    private String manufacturer;
    private String model;
    private Integer capacity;
    private BigDecimal basicPrice;
    private Boolean active;
    private String description;
    private List<ImageDto> images;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ImageDto> getImages() {
        return images;
    }

    public void setImages(List<ImageDto> images) {
        this.images = images;
    }
}
