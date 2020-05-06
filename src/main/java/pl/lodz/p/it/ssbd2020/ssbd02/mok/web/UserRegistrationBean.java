package pl.lodz.p.it.ssbd2020.ssbd02.mok.web;


import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.AddUserDto;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints.UserEndpoint;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.ResourceBundle;

@Named
@RequestScoped
public class UserRegistrationBean implements Serializable {
    @Inject
    private UserEndpoint userEndpoint;

    @Inject
    private FacesContext facesContext;

    private AddUserDto userDto;

    private ResourceBundle bundle;

    public AddUserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(AddUserDto userDto) {
        this.userDto = userDto;
    }

    @PostConstruct
    public void init() {
        userDto = new AddUserDto();
        bundle = ResourceBundle.getBundle("resource", getHttpRequestFromFacesContext().getLocale());
    }

    public String registerAccountAction() {
        try {
            userEndpoint.registerNewUser(userDto);
        }
        catch (AppBaseException e) {
            String msg = bundle.getString(e.getLocalizedMessage());
            String head = bundle.getString("error");
            facesContext.getExternalContext().getFlash().setKeepMessages(true);
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, head, msg));
            return "register.xhtml";
        }
        String msg = bundle.getString("users.registerInfo");
        String head = bundle.getString("success");
        facesContext.getExternalContext().getFlash().setKeepMessages(true);
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, head, msg));
        return "login.xhtml?faces-redirect=true";
    }


    public ResourceBundle getBundle() {
        return bundle;
    }

    public void setBundle(ResourceBundle bundle) {
        this.bundle = bundle;
    }

    private HttpServletRequest getHttpRequestFromFacesContext() {
        return (HttpServletRequest) facesContext
                .getExternalContext()
                .getRequest();
    }
}
