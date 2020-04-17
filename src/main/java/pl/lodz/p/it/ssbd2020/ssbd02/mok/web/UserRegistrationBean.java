package pl.lodz.p.it.ssbd2020.ssbd02.mok.web;


import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.UserDto;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@RequestScoped
public class UserRegistrationBean implements Serializable {
    @Inject
    private UserControllerBean accountControllerBean;
    private UserDto accountDto;

    public UserRegistrationBean() {
    }

    public UserDto getAccountDto() {
        return accountDto;
    }

    public void setAccountDto(UserDto accountDto) {
        this.accountDto = accountDto;
    }

    @PostConstruct
    public void init() {
        accountDto = new UserDto();
    }

    public String registerAccountAction() {
        accountControllerBean.registerAccount(accountDto);
        return "login.xhtml?faces-redirect=true";
    }
}
