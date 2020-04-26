package pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos;

import java.util.Date;

public class UserLoginDto {
    private Long id;
    private Date lastValidLogin;
    private Date lastInvalidLogin;
    private Integer invalidLoginAttempts;
    private Boolean locked;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getLastValidLogin() {
        return lastValidLogin;
    }

    public void setLastValidLogin(Date lastValidLogin) {
        this.lastValidLogin = lastValidLogin;
    }

    public Date getLastInvalidLogin() {
        return lastInvalidLogin;
    }

    public void setLastInvalidLogin(Date lastInvalidLogin) {
        this.lastInvalidLogin = lastInvalidLogin;
    }


    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public Integer getInvalidLoginAttempts() {
        return invalidLoginAttempts;
    }

    public void setInvalidLoginAttempts(Integer invalidLoginAttempts) {
        this.invalidLoginAttempts = invalidLoginAttempts;
    }
}
