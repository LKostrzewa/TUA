package pl.lodz.p.it.ssbd2020.ssbd02.mok.web;


import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.ResetPasswordDto;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints.UserEndpoint;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.ResourceBundle;

/**
 * Klasa do obsługi widoku resetowania hasła
 */
@Named
@ViewScoped
public class ResetPasswordPageBean implements Serializable {

    @Inject
    private UserEndpoint userEndpoint;
    @Inject
    private FacesContext facesContext;

    private ResetPasswordDto resetPasswordDto;

    private ResourceBundle resourceBundle;


    public ResetPasswordDto getResetPasswordDto() {
        return resetPasswordDto;
    }

    public void setResetPasswordDto(ResetPasswordDto resetPasswordDto) {
        this.resetPasswordDto = resetPasswordDto;
    }

    /**
     * Metoda inicjalizująca komponent
     */
    @PostConstruct
    public void init() {
        resetPasswordDto = new ResetPasswordDto();
        resetPasswordDto.setResetPasswordCode(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("key"));
    }

    /**
     * Metoda obsługująca wciśnięcie guzika do resetowania hasła
     *
     * @return strona na którą zostanie przekierowany użytkownik
     */
    public String resetPassword() {
        try {
            userEndpoint.resetPassword(resetPasswordDto);
            displayMessage();
        } catch (AppBaseException e) {
            displayError(e.getLocalizedMessage());
        }
        return "login.xhtml?faces-redirect=true";
    }

    /**
     * Metoda inicjalizująca wyświetlanie wiadomości
     */
    private void displayInit(){
        facesContext.getExternalContext().getFlash().setKeepMessages(true);
        resourceBundle = ResourceBundle.getBundle("resource", facesContext.getViewRoot().getLocale());
    }

    /**
     * Metoda wyświetlająca wiadomość o poprawnym wykonaniu operacji
     */
    private void displayMessage() {
        displayInit();
        String msg = resourceBundle.getString("resetPassword.resetPasswordSuccess");
        String head = resourceBundle.getString("success");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, head, msg));
    }

    /**
     * Metoda wyświetlająca wiadomość o zaistniałym błędzie
     *
     * @param message wiadomość do wyświetlenia
     */
    private void displayError(String message) {
        displayInit();
        String msg = resourceBundle.getString(message);
        String head = resourceBundle.getString("error");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, head, msg));
    }


}
