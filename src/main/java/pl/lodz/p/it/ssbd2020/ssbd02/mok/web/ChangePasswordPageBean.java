package pl.lodz.p.it.ssbd2020.ssbd02.mok.web;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.User;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.ChangePasswordDTO;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints.UserEndpoint;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.ObjectMapperUtils;

import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ConversationScoped
public class ChangePasswordPageBean implements Serializable{
    @Inject
    private UserEndpoint userEndpoint;
    @Inject
    UserDetailsPageBean userDetailsPageBean;
    private ChangePasswordDTO changePasswordDTO;

    public ChangePasswordDTO getChangePasswordDTO() {
        return changePasswordDTO;
    }

    public void setChangePasswordDTO(ChangePasswordDTO changePasswordDTO) {
        this.changePasswordDTO = changePasswordDTO;
    }

    public String onClick() {
        User user = userEndpoint.find(userDetailsPageBean.getUserDetailsDTO().getId());
        this.changePasswordDTO = ObjectMapperUtils.map(user, ChangePasswordDTO.class);
        return "changePassword.xhtml?faces-redirect=true";
    }

    public String onFinish() {
        userEndpoint.editPassword(changePasswordDTO);
        userDetailsPageBean.refresh();
        return "userDetails.xhtml?faces-redirect=true";
    }
}
