package pl.lodz.p.it.ssbd2020.ssbd02.mok.web;

import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.UsersListDTO;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints.UserEndpoint;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class ListUsersPageBean {
    @Inject
    private UserEndpoint userEndpoint;

    private List<UsersListDTO> users;

    @PostConstruct
    private void init() {
        this.users = userEndpoint.getAll();
    }

    public List<UsersListDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UsersListDTO> users) {
        this.users = users;
    }
}

