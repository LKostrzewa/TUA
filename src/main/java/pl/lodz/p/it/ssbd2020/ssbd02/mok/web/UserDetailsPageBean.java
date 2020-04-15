package pl.lodz.p.it.ssbd2020.ssbd02.mok.web;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.User;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.UserDetailsDto;
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
    private Conversation conversation;
    private UserDetailsDto userDetailsDto;

    public UserDetailsDto getUserDetailsDto() {
        return userDetailsDto;
    }

    public void setUserDetailsDto(UserDetailsDto userDetailsDto) {
        this.userDetailsDto = userDetailsDto;
    }

    public String onClick(Long id) {
        conversation.begin();
        User user = userEndpoint.getUserById(id);
        this.userDetailsDto = ObjectMapperUtils.map(user, UserDetailsDto.class);
        return "userDetails.xhtml?faces-redirect=true";
    }

    public String onFinish() {
        conversation.end();
        return "listUsers.xhtml?faces-redirect=true";
    }

    public void refresh() {
        User user = userEndpoint.getUserById(userDetailsDto.getId());
        this.userDetailsDto = ObjectMapperUtils.map(user, UserDetailsDto.class);
    }
}
