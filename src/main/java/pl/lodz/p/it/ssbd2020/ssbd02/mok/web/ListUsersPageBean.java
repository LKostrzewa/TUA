package pl.lodz.p.it.ssbd2020.ssbd02.mok.web;

import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.ListUsersDto;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints.UserEndpoint;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collections;
import java.util.List;

@Named
@RequestScoped
public class ListUsersPageBean {
    @Inject
    private UserEndpoint userEndpoint;
    private List<ListUsersDto> users;

    public List<ListUsersDto> getUsers() {
        return users;
    }

    public void setUsers(List<ListUsersDto> users) {
        this.users = users;
    }

    @PostConstruct
    private void init() {
        this.users = userEndpoint.getAll();
        Collections.sort(this.users);
    }
}

