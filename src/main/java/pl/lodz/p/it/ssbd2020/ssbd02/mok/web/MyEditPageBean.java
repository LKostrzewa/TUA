package pl.lodz.p.it.ssbd2020.ssbd02.mok.web;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.EditUserDto;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints.UserEndpoint;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.ResourceBundle;

/**
 * Klasa do obsługi widoku edycji własnych danych
 */
@Named
@ViewScoped
public class MyEditPageBean implements Serializable {
    @Inject
    private UserEndpoint userEndpoint;
    @Inject
    private FacesContext facesContext;

    private EditUserDto editUserDto;
    private ResourceBundle bundle;

    public EditUserDto getEditUserDto() {
        return editUserDto;
    }

    public void setEditUserDto(EditUserDto editUserDto) {
        this.editUserDto = editUserDto;
    }

    public ResourceBundle getBundle() {
        return bundle;
    }

    public void setBundle(ResourceBundle bundle) {
        this.bundle = bundle;
    }

    /**
     * Metoda inicjalizująca komponent
     */
    public void init() throws IOException {
        try{
            this.editUserDto = userEndpoint.getEditUserDtoByLogin();
        } catch (AppBaseException e) {
            displayError(e.getLocalizedMessage());
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.redirect(externalContext.getRequestContextPath()+"account.xhtml?faces-redirect=true?includeViewParams=true");
        }
    }

    /**
     * Metoda obsługująca wciśnięcie guzika do edycji
     *
     * @return strona na którą zostanie przekierowany użytkownik
     */
    public String editUser() {
        try {
            userEndpoint.editOwnData(editUserDto);
            displayMessage();
        } catch (AppBaseException e) {
            displayError(e.getLocalizedMessage());
        }
        return "account.xhtml?faces-redirect=true?includeViewParams=true";
    }

    /**
     * Metoda inicjalizująca wyświetlanie wiadomości
     */
    private void displayInit(){
        facesContext.getExternalContext().getFlash().setKeepMessages(true);
        bundle = ResourceBundle.getBundle("resource", facesContext.getViewRoot().getLocale());
    }

    /**
     * Metoda wyświetlająca wiadomość o poprawnym wykonaniu operacji
     */
    private void displayMessage() {
        displayInit();
        String msg = bundle.getString("users.editInfo");
        String head = bundle.getString("success");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, head, msg));
    }

    /**
     * Metoda wyświetlająca wiadomość o zaistniałym błędzie
     *
     * @param message wiadomość do wyświetlenia
     */
    private void displayError(String message) {
        displayInit();
        String msg = bundle.getString(message);
        String head = bundle.getString("error");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, head, msg));
    }
}
