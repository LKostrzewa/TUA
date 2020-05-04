package pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos;

import java.util.Date;

public class UserLoginDto {
    private Long id;
    private String email;
    private Date lastValidLogin;
    private Date lastInvalidLogin;
    private Integer invalidLoginAttempts;
    private Boolean locked;
    private String lastLoginIp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }
}
