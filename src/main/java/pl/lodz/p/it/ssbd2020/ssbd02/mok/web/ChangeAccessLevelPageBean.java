package pl.lodz.p.it.ssbd2020.ssbd02.mok.web;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.UserAccessLevelDto;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints.UserAccessLevelEndpoint;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ResourceBundle;

@Named
@ViewScoped
public class ChangeAccessLevelPageBean implements Serializable {
    @Inject
    private UserAccessLevelEndpoint userAccessLevelEndpoint;
    private UserAccessLevelDto userDto;
    private Long userId;

    public UserAccessLevelDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserAccessLevelDto userDto) {
        this.userDto = userDto;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void init() throws AppBaseException{
        this.userDto = userAccessLevelEndpoint.findAccessLevelById(userId);
    }

    public void displayMessage(FacesContext context, ResourceBundle resourceBundle) {
        context.getExternalContext().getFlash().setKeepMessages(true);
        String msg = resourceBundle.getString("users.changeAccessLevelInfo");
        String head = resourceBundle.getString("success");
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, head, msg));
    }

    public String changeAccessLevel() {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("resource", context.getViewRoot().getLocale());
        try {
            userAccessLevelEndpoint.editAccessLevels(userDto);
        }
        catch (AppBaseException e){
            //TODO w odpowiednim pliku xhtml dodac growl
            String msg = resourceBundle.getString(e.getLocalizedMessage());
            String head = resourceBundle.getString("error");
            context.getExternalContext().getFlash().setKeepMessages(true);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, head, msg));
            return "changeAccessLevel.xhtml";
        }
        displayMessage(context, resourceBundle);
        return "userDetails.xhtml?faces-redirect=true?includeViewParams=true";
    }
}
