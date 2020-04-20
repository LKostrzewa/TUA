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
    UserDetailsPageBean userDetailsPageBean;
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

    public String openChangeAccessLevelPage(Long userId) {
        this.userId = userId;
        this.userDto = userAccessLevelEndpoint.findAccessLevelById(userId);
        return "/admin/changeAccessLevel.xhtml?faces-redirect=true";
    }

    public String changeAccessLevel() {
        userAccessLevelEndpoint.editAccessLevels(userDto, userId);
        userDetailsPageBean.refresh();
        return "/admin/userDetails.xhtml?faces-redirect=true";
    }
}
