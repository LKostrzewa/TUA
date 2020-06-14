package pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.rental;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

/**
 * DTO do wyświetlenia listy własnych wypożyczeń.
 */
public class ListRentalsDto {
    private UUID businessKey;
    private String yachtName;
    private Date beginDate;
    private Date endDate;
    private String statusName;
    private BigDecimal price;

    public UUID getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(UUID businessKey) {
        this.businessKey = businessKey;
    }

    public String getYachtName() {
        return yachtName;
    }

    public void setYachtName(String yachtName) {
        this.yachtName = yachtName;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
