package pl.lodz.p.it.ssbd2020.ssbd02.mok.web;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.UserAccessLevelDto;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.UserDetailsDto;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints.UserAccessLevelEndpoint;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints.UserEndpoint;

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

    public void init() throws AppBaseException{
        this.userDetailsDto = userEndpoint.getUserDetailsDtoById(userId);
        this.userAccessLevelDto = userAccessLevelEndpoint.findAccessLevelById(userId);
    }

    public String getAccessLevels() {
        String string = "";
        if (userAccessLevelDto.getAdmin().getLeft())
            string += "ADMINISTRATOR ";
        if (userAccessLevelDto.getManager().getLeft())
            string += "MANAGER ";
        if (userAccessLevelDto.getClient().getLeft())
            string += "CLIENT";
        return string;
    }

    public void lockAccount() throws AppBaseException{
        userDetailsDto.setLocked(true);
        userEndpoint.lockAccount(userId);
    }

    public void unlockAccount() throws AppBaseException {
        userDetailsDto.setLocked(false);
        userEndpoint.unlockAccount(userId);
    }
}
