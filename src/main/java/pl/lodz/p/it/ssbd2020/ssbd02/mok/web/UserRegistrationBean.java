package pl.lodz.p.it.ssbd2020.ssbd02.mok.web;


import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.AddUserDto;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.UserDto;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints.UserEndpoint;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@RequestScoped
public class UserRegistrationBean implements Serializable {
    @Inject
    private UserEndpoint userEndpoint;
    private AddUserDto userDto;

    public AddUserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(AddUserDto userDto) {
        this.userDto = userDto;
    }

    @PostConstruct
    public void init() {
        userDto = new AddUserDto();
    }

    public String registerAccountAction() {
        try {
            userEndpoint.registerNewUser(userDto);
        }
        catch (AppBaseException e) {

        }
        return "login.xhtml?faces-redirect=true";
    }
}
