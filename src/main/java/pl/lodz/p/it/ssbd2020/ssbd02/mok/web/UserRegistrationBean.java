package pl.lodz.p.it.ssbd2020.ssbd02.mok.web;


import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.UserDTO;

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
    private UserDTO accountDTO;

    public UserRegistrationBean() {
    }

    public UserDTO getAccountDTO() {
        return accountDTO;
    }

    public void setAccountDTO(UserDTO accountDTO) {
        this.accountDTO = accountDTO;
    }

    @PostConstruct
    public void init() {
        accountDTO = new UserDTO();
    }

    public String registerAccountAction() {
        accountControllerBean.registerAccount(accountDTO);
        return "login.xhtml?faces-redirect=true";
    }
}
