package pl.lodz.p.it.ssbd2020.ssbd02.mok.web;

import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.UsersListDTO;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints.UserEndpoint;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.ObjectMapperUtils;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class UsersListPageBean {
    @Inject
    private UserEndpoint userEndpoint;
    private List<UsersListDTO> users;

    @PostConstruct
    private void init() {
        this.users = ObjectMapperUtils.mapAll(userEndpoint.getAll(), UsersListDTO.class);
    }

    public List<UsersListDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UsersListDTO> users) {
        this.users = users;
    }
}

