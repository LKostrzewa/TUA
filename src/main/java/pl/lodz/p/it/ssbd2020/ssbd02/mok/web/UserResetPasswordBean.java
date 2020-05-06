package pl.lodz.p.it.ssbd2020.ssbd02.mok.web;


import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints.UserEndpoint;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.ResourceBundle;

@Named
@RequestScoped
public class UserResetPasswordBean implements Serializable {
    @Inject
    private UserEndpoint userEndpoint;

    @Inject
    private FacesContext facesContext;

    @Pattern(regexp = "^[a-zA-Z0-9.!#$%&''*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?$",
            message = "{validation.email}")
    private String email;
    private ResourceBundle bundle;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @PostConstruct
    public void init() {

        bundle = ResourceBundle.getBundle("resource", getHttpRequestFromFacesContext().getLocale());
    }

    public String resetPassword() {
        try {
            userEndpoint.resetPassword(email);
            displayMessage();
        } catch (AppBaseException e) {
            displayError(e.getLocalizedMessage());
        }
        return "login.xhtml?faces-redirect=true";
    }

    public void displayMessage() {
        facesContext.getExternalContext().getFlash().setKeepMessages(true);
        ResourceBundle resourceBundle = ResourceBundle.getBundle("resource", facesContext.getViewRoot().getLocale());
        String msg = resourceBundle.getString("forgetPassword.sendEmail");
        String head = resourceBundle.getString("success");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, head, msg));
    }

    private void displayError(String message) {
        facesContext.getExternalContext().getFlash().setKeepMessages(true);
        ResourceBundle resourceBundle = ResourceBundle.getBundle("resource", facesContext.getViewRoot().getLocale());
        String msg = resourceBundle.getString(message);
        String head = resourceBundle.getString("error");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, head, msg));

    }

    private HttpServletRequest getHttpRequestFromFacesContext() {
        return (HttpServletRequest) facesContext
                .getExternalContext()
                .getRequest();
    }
}
