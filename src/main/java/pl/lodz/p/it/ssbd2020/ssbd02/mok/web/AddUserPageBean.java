package pl.lodz.p.it.ssbd2020.ssbd02.mok.web;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.AddUserDto;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints.UserEndpoint;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ResourceBundle;

/**
 * Klasa do obsługi widoku dodawania użytkownika
 */
@Named
@RequestScoped
public class AddUserPageBean implements Serializable {
    @Inject
    private UserEndpoint userEndpoint;
    private AddUserDto addUserDto;
    @Inject
    private FacesContext context;

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

    /**
     * Metoda obsługująca wciśnięcie guzika do dodania nowego użytkownika
     *
     * @return strona na którą zostanie przekierowany użytkownik
     */
    public String addUser() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("resource", context.getViewRoot().getLocale());
        try {
            userEndpoint.addNewUser(addUserDto);
        }
        catch (AppBaseException e){
            String msg = resourceBundle.getString(e.getLocalizedMessage());
            String head = resourceBundle.getString("error");
            context.getExternalContext().getFlash().setKeepMessages(true);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, head, msg));
            return "addUser.xhtml";
        }
        String msg = resourceBundle.getString("users.addInfo");
        String head = resourceBundle.getString("success");
        context.getExternalContext().getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, head, msg));
        return "listUsers.xhtml?faces-redirect=true";
    }
}
