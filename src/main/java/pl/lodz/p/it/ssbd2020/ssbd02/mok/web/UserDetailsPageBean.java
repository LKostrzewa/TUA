package pl.lodz.p.it.ssbd2020.ssbd02.mok.web;

import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.UserDetailsDto;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints.UserEndpoint;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ConversationScoped
public class UserDetailsPageBean implements Serializable {
    @Inject
    private Conversation conversation;
    @Inject
    private UserEndpoint userEndpoint;
    private UserDetailsDto userDetailsDto;

    public UserDetailsDto getUserDetailsDto() {
        return userDetailsDto;
    }

    public void setUserDetailsDto(UserDetailsDto userDetailsDto) {
        this.userDetailsDto = userDetailsDto;
    }

    public String onClick(Long id) {
        conversation.begin();
        this.userDetailsDto = userEndpoint.getUserById3(id);
        return "userDetails.xhtml?faces-redirect=true";
    }

    public String onFinish() {
        conversation.end();
        return "listUsers.xhtml?faces-redirect=true";
    }

    public void refresh() {
        this.userDetailsDto = userEndpoint.getUserById3(userDetailsDto.getId());
    }
}
