package pl.lodz.p.it.ssbd2020.ssbd02.moj.web.yacht;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.rental.RentalDetailsDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yacht.YachtDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints.YachtEndpoint;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints.YachtPortEndpoint;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Klasa od obsługi widoku szczegółowych informacji jachtu.
 */
@Named
@ViewScoped
public class YachtDetailsPageBean implements Serializable {
    @Inject
    private YachtEndpoint yachtEndpoint;
    @Inject
    private YachtPortEndpoint yachtPortEndpoint;

    @Inject
    private FacesContext facesContext;
    private ResourceBundle resourceBundle;

    private Long yachtId;
    private YachtDto yachtDto;


    public Long getYachtId() {
        return yachtId;
    }

    public void setYachtId(Long yachtId) {
        this.yachtId = yachtId;
    }

    public YachtDto getYachtDto() {
        return yachtDto;
    }

    public void setYachtDto(YachtDto yachtDto) {
        this.yachtDto = yachtDto;
    }

    public List<RentalDetailsDto> getOpinionFromRentals() {
        return yachtDto.getRentals().stream()
                .filter(r -> r.getOpinion()!=null)
                .collect(Collectors.toList());
    }

    /**
     * Metoda inicjalizująca komponent.
     *
     * @throws IOException wyjątek wejścia/wyjścia
     */
    public void init() throws IOException {
        try {
            this.yachtDto = yachtEndpoint.getYachtById(yachtId);
        } catch (AppBaseException e) {
            displayError(e.getLocalizedMessage());
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.redirect(externalContext.getRequestContextPath() + "listYachts.xhtml");
        }
    }

    /**
     * Metoda obsługująca wciśnięcie guzika do odpisania jachtu z portu.
     */
    public void retractYachtFromPort() {
        try {
            yachtPortEndpoint.retractYachtFromPort(yachtId);
            displayMessage();
        }
        catch (AppBaseException e) {
            displayError(e.getLocalizedMessage());
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
     * Metoda wyświetlająca wiadomość o poprawnym wykonaniu operacji.
     */
    private void displayMessage() {
        displayInit();
        String msg = resourceBundle.getString("yacht.retractInfo");
        String head = resourceBundle.getString("success");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, head, msg));
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
