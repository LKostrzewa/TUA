package pl.lodz.p.it.ssbd2020.ssbd02.mok.web;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.User;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.UserAccessLevelDTO;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.UserDetailsDTO;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints.UserAccessLevelEndpoint;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints.UserEndpoint;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.ObjectMapperUtils;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ConversationScoped
public class UserDetailsPageBean implements Serializable {
    @Inject
    private UserEndpoint userEndpoint;
    @Inject
    private UserAccessLevelEndpoint userAccessLevelEndpoint;
    @Inject
    private Conversation conversation;
    private UserDetailsDTO userDetailsDTO;
    private UserAccessLevelDTO userAccessLevelDTO;

    public UserAccessLevelDTO getUserAccessLevelDTO() {
        return userAccessLevelDTO;
    }

    public void setUserAccessLevelDTO(UserAccessLevelDTO userAccessLevelDTO) {
        this.userAccessLevelDTO = userAccessLevelDTO;
    }

    public UserDetailsDTO getUserDetailsDTO() {
        return userDetailsDTO;
    }

    public void setUserDetailsDTO(UserDetailsDTO userDetailsDTO) {
        this.userDetailsDTO = userDetailsDTO;
    }

    public String onClick(long id) {
        conversation.begin();
        User user = userEndpoint.find(id);
        this.userDetailsDTO = ObjectMapperUtils.map(user, UserDetailsDTO.class);
        this.userAccessLevelDTO = userAccessLevelEndpoint.findAccessLevelByUserID(id);
        return "userDetails.xhtml?faces-redirect=true";
    }

    public String onFinish() {
        conversation.end();
        return "usersList.xhtml?faces-redirect=true";
    }

    public void refresh() {
        User user = userEndpoint.find(userDetailsDTO.getId());
        this.userDetailsDTO = ObjectMapperUtils.map(user, UserDetailsDTO.class);
        this.userAccessLevelDTO = userAccessLevelEndpoint.findAccessLevelByUserID(user.getId());
    }

    public String getAccessLevels() {
        String string = "";
        if (userAccessLevelDTO.getAdmin())
            string += "ADMINISTRATOR ";
        if (userAccessLevelDTO.getManager())
            string += "MANAGER ";
        if (userAccessLevelDTO.getClient())
            string += "CLIENT";
        return string;
    }
}
