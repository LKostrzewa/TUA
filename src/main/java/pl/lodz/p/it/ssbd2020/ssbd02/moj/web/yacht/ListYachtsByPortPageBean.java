package pl.lodz.p.it.ssbd2020.ssbd02.moj.web.yacht;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppNotFoundException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.image.ImageDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yacht.YachtByPortDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yacht.YachtDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints.YachtPortEndpoint;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Klasa do obsługi wyświetlania jachtów przypisanych do portu.
 */
@Named
@ApplicationScoped
public class ListYachtsByPortPageBean implements Serializable {
    @Inject
    private YachtPortEndpoint yachtPortEndpoint;
    private List<YachtByPortDto> yachts;
    private List<YachtByPortDto> activeYachts;

    @Inject
    private FacesContext facesContext;
    private ResourceBundle resourceBundle;

    private Long portId;

    public List<YachtByPortDto> getYachts() {
        return yachts;
    }

    public void setYachts(List<YachtByPortDto> yachts) {
        this.yachts = yachts;
    }

    public Long getPortId() {
        return portId;
    }

    public void setPortId(Long portId) {
        this.portId = portId;
    }

    public List<YachtByPortDto> getActiveYachts() {
        return activeYachts;
    }

    public void setActiveYachts(List<YachtByPortDto> activeYachts) {
        this.activeYachts = activeYachts;
    }

    public byte[] getImage(long imageId, long yachtId) throws IOException {
        YachtByPortDto yachtDto = new YachtByPortDto();
        try {
            yachtDto = activeYachts.stream().filter(y -> y.getId().equals(yachtId))
                    .findFirst().orElseThrow(AppNotFoundException::createYachtNotFoundException);
        } catch (AppNotFoundException e) {
            displayError(e.getLocalizedMessage());
            ExternalContext externalContext = facesContext.getExternalContext();
            externalContext.redirect(externalContext.getRequestContextPath() + "listPorts.xhtml");
        }
        Optional<ImageDto> first = yachtDto.getYachtModel().getImages().stream().filter(i -> i.getId().equals(imageId)).findFirst();
        return first.map(ImageDto::getLob).orElse(null);
    }

    /**
     * Metoda inicjalizująca komponent.
     *
     * @throws IOException wyjątek wejścia/wyjścia
     */
    public void init() throws IOException {
        try {
            this.yachts = yachtPortEndpoint.getAllYachtsByPort(portId);
            this.yachts.sort(Comparator.comparing(YachtByPortDto::getName, String::compareToIgnoreCase));
            this.activeYachts = yachts.stream().filter(YachtByPortDto::isActive).collect(Collectors.toList());

        } catch (AppBaseException e) {
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
