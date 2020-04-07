package pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.User;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.facades.UserFacade;


import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import java.util.List;

@Stateful
public class UserEndpoint {
    @EJB
    private UserFacade userFacade;


    public List<User> getAll() {
        return userFacade.findAll();
    }
}
