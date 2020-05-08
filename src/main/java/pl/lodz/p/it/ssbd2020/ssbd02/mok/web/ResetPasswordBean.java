package pl.lodz.p.it.ssbd2020.ssbd02.mok.web;


import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints.UserEndpoint;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.ResourceBundle;
import java.util.logging.Logger;


@Named
@ViewScoped
public class ResetPasswordBean implements Serializable {

    @Inject
    UserEndpoint userEndpoint;
    @Inject
    private FacesContext facesContext;

    private String resetPasswordCode;
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$", message = "{validation.password}")
    private String password;

    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getKey() {
        return resetPasswordCode;
    }

    public void setKey(String key) {
        this.resetPasswordCode = key;
    }


    @PostConstruct
    public void init() {
        resetPasswordCode = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("key");
        logger.info("Klucz:" + resetPasswordCode);
    }

    public String resetPassword() {
        try {
            userEndpoint.resetPassword(resetPasswordCode,password);
            displayMessage();
        } catch (AppBaseException e) {
            displayError(e.getLocalizedMessage());
        }
        return "login.xhtml?faces-redirect=true";
    }

    public void displayMessage() {
        facesContext.getExternalContext().getFlash().setKeepMessages(true);
        ResourceBundle resourceBundle = ResourceBundle.getBundle("resource", facesContext.getViewRoot().getLocale());
        String msg = resourceBundle.getString("resetPassword.resetPasswordSuccess");
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


}
