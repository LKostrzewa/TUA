package pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos;

import java.util.Date;

public class UserLoginDto {
    private Long id;
    private Date lastValidLogin;
    private Date lastInvalidLogin;
    private String lastLoginIp;

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

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }
}
