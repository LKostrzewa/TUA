package pl.lodz.p.it.ssbd2020.ssbd02.mok.web;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.User;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints.UserEndpoint;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Named
@ConversationScoped
public class EditAccountPageBean implements Serializable {

    @Inject
    private UserEndpoint userEndpoint;
    @Inject
    private Conversation conversation;
    private User user;

    public String onClick(User user) {
        conversation.begin();
        this.user = user;
        return "/admin/editAccount.xhtml?faces-redirect=true";
    }

    public String onFinish() {
        userEndpoint.edit(user);
        conversation.end();
        return "users.xhtml?faces-redirect=true";
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
