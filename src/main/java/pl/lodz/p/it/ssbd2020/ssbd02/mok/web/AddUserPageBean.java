package pl.lodz.p.it.ssbd2020.ssbd02.mok.web;

import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.NewUserDTO;
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
    private NewUserDTO userDTO;

    @PostConstruct
    public void init(){
        userDTO = new NewUserDTO();
    }

    public NewUserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(NewUserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public String onClick() {
        userEndpoint.addNewUser(userDTO);
        return "usersList.xhtml?faces-redirect=true";
    }


}
