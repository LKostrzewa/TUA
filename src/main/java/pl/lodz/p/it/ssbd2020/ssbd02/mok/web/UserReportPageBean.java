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

/**
 * Klasa do obsługi widoku raportu użytkowników.
 */
@Named
@ViewScoped
public class UserReportPageBean implements Serializable {
    @Inject
    private @Named("UserEndpointImpl") UserEndpoint userEndpoint;
    private List<UserReportDto> users;

    public List<UserReportDto> getUsers() {
        return users;
    }

    public void setUsers(List<UserReportDto> users) {
        this.users = users;
    }

    /**
     * Metoda inicjalizująca komponent.
     */
    @PostConstruct
    private void init() {
        this.users = userEndpoint.getUserReport();
        Collections.sort(this.users);
    }
}
