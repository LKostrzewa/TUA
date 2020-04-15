package pl.lodz.p.it.ssbd2020.ssbd02.mok.web;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.User;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.EditUserDto;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints.UserEndpoint;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.ObjectMapperUtils;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ConversationScoped
public class EditUserPageBean implements Serializable {
    @Inject
    private UserEndpoint userEndpoint;
    @Inject
    UserDetailsPageBean userDetailsPageBean;
    private EditUserDto editUserDto;

    public EditUserDto getEditUserDto() {
        return editUserDto;
    }

    public void setEditUserDto(EditUserDto editUserDto) {
        this.editUserDto = editUserDto;
    }

    public String onClick() {
        User user = userEndpoint.getUserById(userDetailsPageBean.getUserDetailsDto().getId());
        this.editUserDto = ObjectMapperUtils.map(user, EditUserDto.class);
        return "editUser.xhtml?faces-redirect=true";
    }

    public String onFinish() {
        userEndpoint.editUser(editUserDto);
        userDetailsPageBean.refresh();
        return "userDetails.xhtml?faces-redirect=true";
    }
}
