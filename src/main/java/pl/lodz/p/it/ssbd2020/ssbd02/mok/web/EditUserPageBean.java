package pl.lodz.p.it.ssbd2020.ssbd02.mok.web;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.User;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.EditUserDTO;
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
    private EditUserDTO editUserDTO;

    public EditUserDTO getEditUserDTO() {
        return editUserDTO;
    }

    public void setEditUserDTO(EditUserDTO editUserDTO) {
        this.editUserDTO = editUserDTO;
    }

    public String onClick() {
        User user = userEndpoint.find(userDetailsPageBean.getUserDetailsDTO().getId());
        this.editUserDTO = ObjectMapperUtils.map(user, EditUserDTO.class);
        return "editUser.xhtml?faces-redirect=true";
    }

    public String onFinish() {
        userEndpoint.editUser(editUserDTO);
        userDetailsPageBean.refresh();
        return "userDetails.xhtml?faces-redirect=true";
    }
}
