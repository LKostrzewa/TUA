package pl.lodz.p.it.ssbd2020.ssbd02.mok.web;

import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.ListUsersDto;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints.UserEndpoint;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

@Named
@RequestScoped
public class ListUsersPageBean {
    @Inject
    private UserEndpoint userEndpoint;
    private List<ListUsersDto> users;
    private List<ListUsersDto> filteredUsers;

    public List<ListUsersDto> getUsers() {
        return users;
    }

    public void setUsers(List<ListUsersDto> users) {
        this.users = users;
    }

    public List<ListUsersDto> getFilteredUsers() {
        return filteredUsers;
    }

    public void setFilteredUsers(List<ListUsersDto> filteredUsers) {
        this.filteredUsers = filteredUsers;
    }

    @PostConstruct
    private void init() {
        this.users = userEndpoint.getAllUsers();
        Collections.sort(this.users);
    }

    public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (filterText == null || filterText.equals("")) {
            return true;
        }

        ListUsersDto listUsersDto = (ListUsersDto) value;
        return listUsersDto.getFirstName().toLowerCase().contains(filterText)
                || listUsersDto.getLastName().toLowerCase().contains(filterText);
    }
}

