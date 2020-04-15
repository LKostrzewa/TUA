package pl.lodz.p.it.ssbd2020.ssbd02.mok.web;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.User;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.ChangePasswordDto;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints.UserEndpoint;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.ObjectMapperUtils;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ConversationScoped
public class ChangePasswordPageBean implements Serializable {
    @Inject
    private UserEndpoint userEndpoint;
    @Inject
    UserDetailsPageBean userDetailsPageBean;
    private ChangePasswordDto changePasswordDto;

    public ChangePasswordDto getChangePasswordDto() {
        return changePasswordDto;
    }

    public void setChangePasswordDto(ChangePasswordDto changePasswordDto) {
        this.changePasswordDto = changePasswordDto;
    }

    public String onClick() {
        User user = userEndpoint.getUserById(userDetailsPageBean.getUserDetailsDto().getId());
        this.changePasswordDto = ObjectMapperUtils.map(user, ChangePasswordDto.class);
        return "changePassword.xhtml?faces-redirect=true";
    }

    public String onFinish() {
        userEndpoint.editUserPassword(changePasswordDto);
        userDetailsPageBean.refresh();
        return "userDetails.xhtml?faces-redirect=true";
    }
}
