package pl.lodz.p.it.ssbd2020.ssbd02.mok.web;

import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.UserAccessLevelDto;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.UserDetailsDto;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints.UserAccessLevelEndpoint;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints.UserEndpoint;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class UserDetailsPageBean implements Serializable {
    @Inject
    private UserEndpoint userEndpoint;
    @Inject
    private UserAccessLevelEndpoint userAccessLevelEndpoint;
    private UserDetailsDto userDetailsDto;
    private UserAccessLevelDto userAccessLevelDto;
    private Long userId;

    public UserAccessLevelDto getUserAccessLevelDto() {
        return userAccessLevelDto;
    }

    public void setUserAccessLevelDto(UserAccessLevelDto userAccessLevelDto) {
        this.userAccessLevelDto = userAccessLevelDto;
    }

    public UserDetailsDto getUserDetailsDto() {
        return userDetailsDto;
    }

    public void setUserDetailsDto(UserDetailsDto userDetailsDto) {
        this.userDetailsDto = userDetailsDto;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void init() {
        this.userDetailsDto = userEndpoint.getUserDetailsDtoById(userId);
        this.userAccessLevelDto = userAccessLevelEndpoint.findAccessLevelById(userId);
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

    public void lockAccount() throws Exception{
        userDetailsDto.setLocked(true);
        userEndpoint.lockAccount(userDetailsDto, userId);
    }

    public void unlockAccount() throws Exception{
        userDetailsDto.setLocked(false);
        userEndpoint.unlockAccount(userDetailsDto, userId);
    }
}
