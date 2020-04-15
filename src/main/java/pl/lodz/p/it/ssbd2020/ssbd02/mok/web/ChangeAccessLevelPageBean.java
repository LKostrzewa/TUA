package pl.lodz.p.it.ssbd2020.ssbd02.mok.web;


import pl.lodz.p.it.ssbd2020.ssbd02.entities.User;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.UserAccessLevelDTO;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.UserDTO;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints.UserEndpoint;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.ObjectMapperUtils;

import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.HashMap;

@Named
@ConversationScoped
public class ChangeAccessLevelPageBean implements Serializable {

    public final static String ADMIN_ACCESS_LEVEL = "ADMINISTRATOR";
    public final static String MANAGER_ACCESS_LEVEL = "MANAGER";
    public final static String CLIENT_ACCESS_LEVEL = "CLIENT";

    @Inject
    private UserEndpoint userEndpoint;
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
        this.userDTO = userEndpoint.findAccessLevelByUserID(userDetailsPageBean.getUserDetailsDTO().getId());
        return "/admin/changeAccessLevel.xhtml?faces-redirect=true";
    }

    public String onFinish() {

        userEndpoint.editAccessLevels(userDTO);
        userDetailsPageBean.refresh();
        return "/admin/userDetails.xhtml?faces-redirect=true";
    }

}
