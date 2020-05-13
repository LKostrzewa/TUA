package pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.rental;

import java.math.BigDecimal;
import java.util.Date;

public class MyRentalDetailsDto {
    private String yachtName;
    private String yachtYachtModelModel;
    private String yachtCurrentPortName;
    private int yachtProductionYear;
    private String yachtEquipment;
    private Date beginDate;
    private Date endDate;
    private String statusName;
    private BigDecimal price;

    public String getYachtName() {
        return yachtName;
    }

    public void setYachtName(String yachtName) {
        this.yachtName = yachtName;
    }

    public String getYachtYachtModelModel() {
        return yachtYachtModelModel;
    }

    public void setYachtYachtModelModel(String yachtYachtModelModel) {
        this.yachtYachtModelModel = yachtYachtModelModel;
    }

    public String getYachtCurrentPortName() {
        return yachtCurrentPortName;
    }

    public void setYachtCurrentPortName(String yachtCurrentPortName) {
        this.yachtCurrentPortName = yachtCurrentPortName;
    }

    public int getYachtProductionYear() {
        return yachtProductionYear;
    }

    public void setYachtProductionYear(int yachtProductionYear) {
        this.yachtProductionYear = yachtProductionYear;
    }

    public String getYachtEquipment() {
        return yachtEquipment;
    }

    public void setYachtEquipment(String yachtEquipment) {
        this.yachtEquipment = yachtEquipment;
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
