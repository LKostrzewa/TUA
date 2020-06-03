package pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.rental;

import java.util.Date;

/**
 * DTO do dodawania wypożyczenia.
 */
public class AddRentalDto {
    private String userLogin;
    private String yachtName;
    private Date beginDate;
    private Date endDate;

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
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
}
