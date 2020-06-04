package pl.lodz.p.it.ssbd2020.ssbd02.moj.web.port;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.port.PortDetailsDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints.PortEndpoint;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Klasa do obsługi widoku listy portów.
 */
@Named
@RequestScoped
public class ListPortsPageBean {
    @Inject
    private PortEndpoint portEndpoint;
    @Inject
    private FacesContext facesContext;
    private ResourceBundle resourceBundle;
    private List<PortDetailsDto> ports;
    private List<PortDetailsDto> activePorts;

    /**
     * Metoda inicjalizująca komponent.
     */
    @PostConstruct
    private void init() {
        this.ports = portEndpoint.getAllPorts();
        this.activePorts = ports.stream().filter(portDetailsDto -> portDetailsDto.getActive().equals(true)).collect(Collectors.toList());
    }

    /**
     * Metoda do deaktywacji portu.
     *
     * @param portId id portu, który ma zostać deaktywowany
     * @return strona, na którą użytkownik ma zostać przekierowany
     */
    public String deactivatePort(long portId){
        try{
            portEndpoint.deactivatePort(portId);
            displayMessage("deactivate.success");
            return "listPorts.xhtml?faces-redirect=true";
        }catch (AppBaseException e){
            displayError(e.getLocalizedMessage());
            return "listPorts.xhtml?faces-redirect=true";
        }
    }

    /**
     * Metoda inicjalizująca wyświetlanie wiadomości
     */
    public void displayInit(){
        facesContext.getExternalContext().getFlash().setKeepMessages(true);
        resourceBundle = ResourceBundle.getBundle("resource", facesContext.getViewRoot().getLocale());
    }

    /**
     * Metoda wyświetlająca wiadomość o poprawnym wykonaniu operacji
     *
     * @param message wiadomość do wyświetlenia
     */
    public void displayMessage(String message) {
        displayInit();
        String msg = resourceBundle.getString(message);
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

    public List<PortDetailsDto> getPorts() {
        return ports;
    }

    public void setPorts(List<PortDetailsDto> ports) {
        this.ports = ports;
    }

    public List<PortDetailsDto> getActivePorts() {
        return activePorts;
    }

    public void setActivePorts(List<PortDetailsDto> activePorts) {
        this.activePorts = activePorts;
    }
}
