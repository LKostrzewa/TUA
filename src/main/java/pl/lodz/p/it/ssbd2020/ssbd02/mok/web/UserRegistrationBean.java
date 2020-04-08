package pl.lodz.p.it.ssbd2020.ssbd02.mok.web;


import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.UserDTO;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints.UserEndpoint;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

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
