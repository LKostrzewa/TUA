package pl.lodz.p.it.ssbd2020.ssbd02.moj.web.opinion;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.opinion.OpinionDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints.OpinionEndpoint;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Klasa do obsługi sortowania opinii.
 */
@Named
@RequestScoped
public class ListOpinionsByYachtPageBean {
    @Inject
    private OpinionEndpoint opinionEndpoint;
    @Inject
    private FacesContext facesContext;

    private ResourceBundle resourceBundle;

    private List<OpinionDto> yachtOpinions;

    public List<OpinionDto> getYachtOpinions() {
        return yachtOpinions;
    }

    public void setYachtOpinions(List<OpinionDto> yachtOpinions) {
        this.yachtOpinions = yachtOpinions;
    }

    /**
     * Metoda inicjalizująca komponent.
     */
    @PostConstruct
    private void init(Long id) {
        try {
            this.yachtOpinions = opinionEndpoint.getAllOpinionsByYacht(id);
        } catch (AppBaseException e) {
            displayError(e.getLocalizedMessage());
        }
    }

    /**
     * Metoda inicjalizująca wyświetlanie wiadomości.
     */
    public void displayInit() {
        facesContext.getExternalContext().getFlash().setKeepMessages(true);
        resourceBundle = ResourceBundle.getBundle("resource", facesContext.getViewRoot().getLocale());
    }

    /**
     * Metoda wyświetlająca wiadomość o zaistniałym błędzie.
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
