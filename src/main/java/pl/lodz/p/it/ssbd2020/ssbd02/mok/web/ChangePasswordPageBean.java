package pl.lodz.p.it.ssbd2020.ssbd02.mok.web;

import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.ChangePasswordDto;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints.UserEndpoint;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ConversationScoped
public class ChangePasswordPageBean implements Serializable {
    @Inject
    UserDetailsPageBean userDetailsPageBean;
    @Inject
    private UserEndpoint userEndpoint;
    private ChangePasswordDto changePasswordDto;

    public ChangePasswordDto getChangePasswordDto() {
        return changePasswordDto;
    }

    public void setChangePasswordDto(ChangePasswordDto changePasswordDto) {
        this.changePasswordDto = changePasswordDto;
    }

    public String onClick() {
        this.changePasswordDto = userEndpoint.getUserById1(userDetailsPageBean.getUserDetailsDto().getId());
        return "changePassword.xhtml?faces-redirect=true";
    }

    public String onFinish() {
        userEndpoint.editUserPassword(changePasswordDto);
        userDetailsPageBean.refresh();
        return "userDetails.xhtml?faces-redirect=true";
    }
}
