package pl.lodz.p.it.ssbd2020.ssbd02.mok.web;

import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.EditUserDto;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints.UserEndpoint;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ConversationScoped
public class EditUserPageBean implements Serializable {
    @Inject
    UserDetailsPageBean userDetailsPageBean;
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

    public String openEditUserPage(Long userId) {
        this.userId = userId;
        this.editUserDto = userEndpoint.getEditUserDtoById(userId);
        return "editUser.xhtml?faces-redirect=true";
    }

    public String editUser() {
        userEndpoint.editUser(editUserDto, userId);
        userDetailsPageBean.refresh();
        return "userDetails.xhtml?faces-redirect=true";
    }
}
