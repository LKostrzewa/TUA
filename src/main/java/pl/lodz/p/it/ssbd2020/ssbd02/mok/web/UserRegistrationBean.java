package pl.lodz.p.it.ssbd2020.ssbd02.mok.web;


import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.AddUserDto;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.UserDto;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints.UserEndpoint;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ResourceBundle;

@Named
@RequestScoped
public class UserRegistrationBean implements Serializable {
    @Inject
    private UserEndpoint userEndpoint;
    private AddUserDto userDto;

    public AddUserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(AddUserDto userDto) {
        this.userDto = userDto;
    }

    @PostConstruct
    public void init() {
        userDto = new AddUserDto();
    }

    public String registerAccountAction() {
        FacesContext context = FacesContext.getCurrentInstance();
        //czy do bundle można się odwoływać w ten sposób tzn hardCoded base name ?
        ResourceBundle resourceBundle = ResourceBundle.getBundle("resource", context.getViewRoot().getLocale());
        try {
            userEndpoint.registerNewUser(userDto);
        }
        catch (AppBaseException e) {
            String msg = resourceBundle.getString(e.getLocalizedMessage());
            String head = resourceBundle.getString("error");
            context.getExternalContext().getFlash().setKeepMessages(true);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, head, msg));
            return "register.xhtml";
        }
        String msg = resourceBundle.getString("users.registerInfo");
        String head = resourceBundle.getString("success");
        context.getExternalContext().getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, head, msg));
        return "login.xhtml?faces-redirect=true";
    }
}
