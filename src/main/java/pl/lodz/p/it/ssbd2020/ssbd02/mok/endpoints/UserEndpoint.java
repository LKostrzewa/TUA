package pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.User;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.facades.UserFacadeLocal;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class UserEndpoint implements UserEndpointLocal {
    @EJB
    private UserFacadeLocal userFacade;

    @Override
    public List<User> getAll() {
        return userFacade.findAll();
    }
}
