package pl.lodz.p.it.ssbd2020.ssbd02.mok.web;

import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.EditUserDto;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints.UserEndpoint;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class MyEditPageBean implements Serializable {
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

    public String editUser() {
        userEndpoint.editUser(editUserDto, userId);
        return "account.xhtml?faces-redirect=true?includeViewParams=true";
    }
}
