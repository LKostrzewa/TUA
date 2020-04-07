package pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.User;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.facades.UserFacade;


import javax.ejb.EJB;
import javax.ejb.Stateful;
import java.io.Serializable;
import java.util.List;

@Stateful
public class UserEndpoint implements Serializable {
    @EJB
    private UserFacade userFacade;

    public List<User> getAll() {
        return userFacade.findAll();
    }

    public void edit(User user) {
        userFacade.edit(user);
    }
}
