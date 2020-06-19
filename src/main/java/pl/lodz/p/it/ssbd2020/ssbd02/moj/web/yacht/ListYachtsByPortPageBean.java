package pl.lodz.p.it.ssbd2020.ssbd02.moj.web.yacht;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.image.ImageDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yacht.YachtDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints.YachtPortEndpoint;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Klasa do obsługi wyświetlania jachtów przypisanych do portu.
 */
@Named
@ViewScoped
public class ListYachtsByPortPageBean implements Serializable {
    @Inject
    private YachtPortEndpoint yachtPortEndpoint;
    private List<YachtDto> yachts;

    @Inject
    private FacesContext facesContext;
    private ResourceBundle resourceBundle;

    private Long portId;

    public List<YachtDto> getYachts() {
        return yachts;
    }

    public void setYachts(List<YachtDto> yachts) {
        this.yachts = yachts;
    }

    public Long getPortId() {
        return portId;
    }

    public void setPortId(Long portId) {
        this.portId = portId;
    }

    /**
     * Metoda inicjalizująca komponent.
     */
    public void init() throws IOException {
        try {
            this.yachts = yachtPortEndpoint.getAllYachtsByPort(portId);
        }
        catch (AppBaseException e) {
            displayError(e.getLocalizedMessage());
            ExternalContext externalContext = facesContext.getExternalContext();
            externalContext.redirect(externalContext.getRequestContextPath() + "listPorts.xhtml");
        }
    }

    /**
     * Metoda inicjalizująca wyświetlanie wiadomości.
     */
    private void displayInit() {
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
