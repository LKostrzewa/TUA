package pl.lodz.p.it.ssbd2020.ssbd02.moj.web.yacht;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.port.PortDetailsDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yacht.YachtDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yachtModel.YachtModelDetailsDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints.PortEndpoint;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints.YachtEndpoint;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ResourceBundle;

/**
 * Klasa od obsługi widoku szczegółowych informacji jachtu.
 */
@Named
@ViewScoped
public class YachtDetailsPageBean implements Serializable {
    @Inject
    private YachtEndpoint yachtEndpoint;
    @Inject
    private YachtModelEndpoint yachtModelEndpoint;
    @Inject
    private PortEndpoint portEndpoint;
    @Inject
    private YachtPortEndpoint yachtPortEndpoint;

    @Inject
    private FacesContext facesContext;
    private ResourceBundle resourceBundle;

    private Long yachtId;
    private YachtDto yachtDto;
    private String yachtModelName;
    private String yachtPortName;


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

    public String getYachtModelName() {
        return yachtModelName;
    }

    public void setYachtModelName(String yachtModelName) {
        this.yachtModelName = yachtModelName;
    }

    public String getYachtPortName() {
        return yachtPortName;
    }

    public void setYachtPortName(String yachtPortName) {
        this.yachtPortName = yachtPortName;
    }

    /**
     * Metoda inicjalizująca komponent.
     */
    public void init() throws AppBaseException {
        this.yachtDto = yachtEndpoint.getYachtById(yachtId);
    }

    public void retractYachtFromPort() {
        try {
            yachtPortEndpoint.retractYachtFromPort(yachtDto.getCurrentPortId(), yachtId);
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
    private void displayMessage(String message) {
        displayInit();
        String msg = resourceBundle.getString(message);
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
