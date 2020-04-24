package pl.lodz.p.it.ssbd2020.ssbd02.mok.web;


import pl.lodz.p.it.ssbd2020.ssbd02.mok.managers.UserManager;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.annotation.ManagedProperty;
import javax.inject.Inject;
import javax.inject.Named;


@Named
@RequestScoped
public class Email {

    @EJB
    UserManager userManager;

    @ManagedProperty(value="#{param.key}")
    private String key;

    private boolean valid=true;

    @PostConstruct
    public void init() {
        // Get User based on activation key.
        // Delete activation key from database.
        // Login user.
        //userManager.confirmActivationCode(key);
    }


}
