package pl.lodz.p.it.ssbd2020.ssbd02.mok.web;

import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.EditUserDto;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints.UserEndpoint;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.ResourceBundle;

@Named
@ViewScoped
public class MyEditPageBean implements Serializable {
    @Inject
    private UserEndpoint userEndpoint;

    @Inject
    private FacesContext facesContext;

    private EditUserDto editUserDto;
    private Long userId;

    ResourceBundle language;

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
        language = ResourceBundle.getBundle("resource", getHttpRequestFromFacesContext().getLocale());
    }

    //TODO obsługa tego wyjątku
    public String editUser() throws Exception {
        userEndpoint.editUser(editUserDto, userId);
        return "account.xhtml?faces-redirect=true?includeViewParams=true";
    }

    public ResourceBundle getLanguage() {
        return language;
    }

    public void setLanguage(ResourceBundle language) {
        this.language = language;
    }

    private HttpServletRequest getHttpRequestFromFacesContext() {
        return (HttpServletRequest) facesContext
                .getExternalContext()
                .getRequest();
    }
}
