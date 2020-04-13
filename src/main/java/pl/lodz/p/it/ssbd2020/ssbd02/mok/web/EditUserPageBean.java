package pl.lodz.p.it.ssbd2020.ssbd02.mok.web;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.User;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints.UserEndpoint;

import javax.enterprise.context.Conversation;
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
    private Conversation conversation;
    private User user;

    public String onClick(User user) {
        this.user = user;
        conversation.begin();
        return "/admin/editUser.xhtml?faces-redirect=true";
    }

//    public String onFinish() {
//        userEndpoint.edit(user);
//        conversation.end();
//        return "usersList.xhtml?faces-redirect=true";
//    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
