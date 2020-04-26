package pl.lodz.p.it.ssbd2020.ssbd02.mok.web;

import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.UserAccessLevelDto;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.UserDetailsDto;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints.UserAccessLevelEndpoint;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints.UserEndpoint;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Klasa od sasa do lasa hihi
 */
@Named
@ViewScoped
public class MyDetailsPageBean implements Serializable {
    @Inject
    private UserEndpoint userEndpoint;
    @Inject
    private UserAccessLevelEndpoint userAccessLevelEndpoint;
    private UserDetailsDto userDetailsDto;
    private UserAccessLevelDto userAccessLevelDto;
    private Long userId;
    private String userLogin;

    public UserDetailsDto getUserDetailsDto() {
        return userDetailsDto;
    }

    public void setUserDetailsDto(UserDetailsDto userDetailsDto) {
        this.userDetailsDto = userDetailsDto;
    }

    public UserAccessLevelDto getUserAccessLevelDto() {
        return userAccessLevelDto;
    }

    public void setUserAccessLevelDto(UserAccessLevelDto userAccessLevelDto) {
        this.userAccessLevelDto = userAccessLevelDto;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    @PostConstruct
    public void init(){
        userLogin = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
        this.userDetailsDto = userEndpoint.getOwnDetailsDtoByLogin(userLogin);
        this.userAccessLevelDto = userAccessLevelEndpoint.findAccessLevelByLogin(userLogin);
        userId = userDetailsDto.getId();
    }

    public String getAccessLevels() {
        String string = "";
        if (userAccessLevelDto.getAdmin())
            string += "ADMINISTRATOR ";
        if (userAccessLevelDto.getManager())
            string += "MANAGER ";
        if (userAccessLevelDto.getClient())
            string += "CLIENT";
        return string;
    }
}
