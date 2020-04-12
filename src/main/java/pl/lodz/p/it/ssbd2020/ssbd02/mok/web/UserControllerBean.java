package pl.lodz.p.it.ssbd2020.ssbd02.mok.web;

import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.UserDTO;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints.UserEndpoint;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class UserControllerBean implements Serializable {
    @EJB
    private UserEndpoint userEndpoint;
    private int lastActionMethod = 0;
    private UserDTO selectedAccountDTO;

    public UserControllerBean() {
    }

    public void registerAccount(final UserDTO accountDTO) {
        userEndpoint.registerNewUser(accountDTO);
    }
}
