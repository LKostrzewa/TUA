package pl.lodz.p.it.ssbd2020.ssbd02.moj.web.yacht;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.port.ListPortsDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.port.PortDetailsDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints.PortEndpoint;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints.YachtPortEndpoint;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Named
@ViewScoped
public class AssignYachtToPortPageBean implements Serializable {
    @Inject
    private @Named("PortEndpoint") PortEndpoint portEndpoint;
    @Inject
    private @Named("YachtPortEndpoint") YachtPortEndpoint yachtPortEndpoint;
    @Inject
    private @Named("FacesContext") FacesContext facesContext;
    private ResourceBundle resourceBundle;

    private List<ListPortsDto> ports;
    private Long portId;
    private Long yachtId;

    public void init() {
        this.ports = portEndpoint.getAllPorts().stream()
                .filter(p -> p.getActive().equals(true))
                .collect(Collectors.toList());
    }

    /**
     * Metoda obsługująca wciśnięcie guzika do przypisania jachtu do danego portu.
     *
     * @return strona na którą zostanie przekierowany użytkownik
     */
    public String assignYachtToPort() {
        try {
            yachtPortEndpoint.assignYachtToPort(portId, yachtId);
            displayMessage();
        }
        catch (AppBaseException e) {
            displayError(e.getLocalizedMessage());
        }
        return "yachtDetails.xhtml?faces-redirect=true?includeViewParams=true";
    }

    public List<ListPortsDto> getPorts() {
        return ports;
    }

    public void setPorts(List<ListPortsDto> ports) {
        this.ports = ports;
    }

    public Long getPortId() {
        return portId;
    }

    public void setPortId(Long portId) {
        this.portId = portId;
    }

    public Long getYachtId() {
        return yachtId;
    }

    public void setYachtId(Long yachtId) {
        this.yachtId = yachtId;
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
        String msg = resourceBundle.getString("yacht.assignInfo");
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
