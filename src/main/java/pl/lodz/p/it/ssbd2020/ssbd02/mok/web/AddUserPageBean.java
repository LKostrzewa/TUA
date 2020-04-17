package pl.lodz.p.it.ssbd2020.ssbd02.mok.web;

import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.AddUserDto;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints.UserEndpoint;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@RequestScoped
public class AddUserPageBean implements Serializable {
    @Inject
    private UserEndpoint userEndpoint;
    private AddUserDto addUserDto;

    @PostConstruct
    public void init() {
        addUserDto = new AddUserDto();
    }

    public AddUserDto getAddUserDto() {
        return addUserDto;
    }

    public void setAddUserDto(AddUserDto addUserDto) {
        this.addUserDto = addUserDto;
    }

    public String addUser() {
        userEndpoint.addNewUser(addUserDto);
        return "listUsers.xhtml?faces-redirect=true";
    }
}
