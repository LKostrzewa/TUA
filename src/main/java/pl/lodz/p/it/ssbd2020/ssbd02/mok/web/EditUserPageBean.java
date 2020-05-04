package pl.lodz.p.it.ssbd2020.ssbd02.mok.web;

import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.EditUserDto;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints.UserEndpoint;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ResourceBundle;

@Named
@ViewScoped
public class EditUserPageBean implements Serializable {
    @Inject
    private UserEndpoint userEndpoint;
    private EditUserDto editUserDto;
    private Long userId;

    public EditUserDto getEditUserDto() {
        return editUserDto;
    }

    public void setEditUserDto(EditUserDto editUserDto) {
        this.editUserDto = editUserDto;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void init() {
        this.editUserDto = userEndpoint.getEditUserDtoById(userId);
    }

    public void displayMessage() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
        ResourceBundle resourceBundle = ResourceBundle.getBundle("resource", context.getViewRoot().getLocale());
        String msg = resourceBundle.getString("users.editInfo");
        String head = resourceBundle.getString("success");
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, head, msg));
    }

    private String reload(){
        return "editUser.xhtml?faces-redirect=true?includeViewParams=true";
    }

    public String editUser() {
        try{
            userEndpoint.editUser(editUserDto, userId);
            displayMessage();
            return "userDetails.xhtml?faces-redirect=true?includeViewParams=true";
        }catch (Exception ex){
            return reload();
        }
    }
}
