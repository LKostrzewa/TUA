package pl.lodz.p.it.ssbd2020.ssbd02.mok.web;

import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.UserReportDto;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints.UserEndpoint;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

@Named
@ViewScoped
public class UserReportPageBean implements Serializable {
    @Inject
    private UserEndpoint userEndpoint;
    private List<UserReportDto> users;
    private List<UserReportDto> filteredUsers;

    public List<UserReportDto> getUsers() {
        return users;
    }

    public void setUsers(List<UserReportDto> users) {
        this.users = users;
    }

    public List<UserReportDto> getFilteredUsers() {
        return filteredUsers;
    }

    public void setFilteredUsers(List<UserReportDto> filteredUsers) {
        this.filteredUsers = filteredUsers;
    }

    @PostConstruct
    private void init() {
        this.users = userEndpoint.getAllUserReportDto();
        Collections.sort(this.users);
    }

    public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
        if (filterText == null || filterText.equals("")) {
            return true;
        }

        UserReportDto userReportDto = (UserReportDto) value;
        return userReportDto.getLogin().toLowerCase().contains(filterText);
    }
}
