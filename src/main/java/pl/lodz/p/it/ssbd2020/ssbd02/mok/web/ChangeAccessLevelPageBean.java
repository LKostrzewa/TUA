package pl.lodz.p.it.ssbd2020.ssbd02.mok.web;

import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.UserAccessLevelDTO;
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

    private UserAccessLevelDTO userDTO;

    public UserAccessLevelDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserAccessLevelDTO userDTO) {
        this.userDTO = userDTO;
    }


    public String onClick() {
        this.userDTO = userAccessLevelEndpoint.findAccessLevelByUserID(userDetailsPageBean.getUserDetailsDto().getId());
        return "/admin/changeAccessLevel.xhtml?faces-redirect=true";
    }

    public String onFinish() {

        userAccessLevelEndpoint.editAccessLevels(userDTO);
        userDetailsPageBean.refresh();
        return "/admin/userDetails.xhtml?faces-redirect=true";
    }

}
