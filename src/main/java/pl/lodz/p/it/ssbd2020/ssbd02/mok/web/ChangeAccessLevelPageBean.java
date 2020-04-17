package pl.lodz.p.it.ssbd2020.ssbd02.mok.web;

import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.UserAccessLevelDto;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints.UserAccessLevelEndpoint;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ConversationScoped
public class ChangeAccessLevelPageBean implements Serializable {
    @Inject
    private UserAccessLevelEndpoint userAccessLevelEndpoint;
    @Inject
    UserDetailsPageBean userDetailsPageBean;

    private UserAccessLevelDto userDto;

    public UserAccessLevelDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserAccessLevelDto userDto) {
        this.userDto = userDto;
    }


    public String openChangeAccessLevelPage() {
        this.userDto = userAccessLevelEndpoint.findAccessLevelById(userDetailsPageBean.getUserDetailsDto().getId());
        return "/admin/changeAccessLevel.xhtml?faces-redirect=true";
    }

    public String changeAccessLevel() {
        userAccessLevelEndpoint.editAccessLevels(userDto);
        userDetailsPageBean.refresh();
        return "/admin/userDetails.xhtml?faces-redirect=true";
    }

}
