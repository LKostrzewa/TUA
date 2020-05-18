package pl.lodz.p.it.ssbd2020.ssbd02.mok.web;


import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.ResetPasswordDto;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints.UserEndpoint;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.ResourceBundle;


@Named
@ViewScoped
public class ResetPasswordPageBean implements Serializable {

    @Inject
    UserEndpoint userEndpoint;
    @Inject
    private FacesContext facesContext;

    private ResetPasswordDto resetPasswordDto;

    private ResourceBundle resourceBundle;
    //private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);


    public ResetPasswordDto getResetPasswordDto() {
        return resetPasswordDto;
    }

    public void setResetPasswordDto(ResetPasswordDto resetPasswordDto) {
        this.resetPasswordDto = resetPasswordDto;
    }

    @PostConstruct
    public void init() {
        resetPasswordDto = new ResetPasswordDto();
        resetPasswordDto.setResetPasswordCode(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("key"));
        //logger.info("Klucz:" + resetPasswordCode);
    }

    public String resetPassword() {
        try {
            userEndpoint.resetPassword(resetPasswordDto);
            displayMessage();
        } catch (AppBaseException e) {
            displayError(e.getLocalizedMessage());
        }
        return "login.xhtml?faces-redirect=true";
    }

    public void displayInit(){
        facesContext.getExternalContext().getFlash().setKeepMessages(true);
        resourceBundle = ResourceBundle.getBundle("resource", facesContext.getViewRoot().getLocale());
    }

    public void displayMessage() {
        displayInit();
        String msg = resourceBundle.getString("resetPassword.resetPasswordSuccess");
        String head = resourceBundle.getString("success");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, head, msg));
    }

    private void displayError(String message) {
        displayInit();
        String msg = resourceBundle.getString(message);
        String head = resourceBundle.getString("error");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, head, msg));
    }


}
