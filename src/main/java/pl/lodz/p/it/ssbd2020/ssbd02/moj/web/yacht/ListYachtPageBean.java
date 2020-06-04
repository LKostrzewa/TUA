package pl.lodz.p.it.ssbd2020.ssbd02.moj.web.yacht;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yacht.YachtListDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints.YachtEndpoint;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * Klasa do obsługi widoku listy jachtów.
 */
@Named
@ViewScoped
public class ListYachtPageBean implements Serializable {
    @Inject
    private YachtEndpoint yachtEndpoint;
    private List<YachtListDto> yachts;

    public List<YachtListDto> getYachts() {
        return yachts;
    }

    public void setYachts(List<YachtListDto> yachts) {
        this.yachts = yachts;
    }

    /**
     * Metoda inicjalizująca komponent.
     */
    @PostConstruct
    private void init() {
        this.yachts = yachtEndpoint.getAllYachts();
    }

    /**
     * Metoda do deaktywacji jachtu.
     *
     * @param yachtID id jachtu, który ma zostać deaktywowany
     * @return strona, na którą użytkownik ma zostać przekierowany
     */
    public String deactivateYacht(Long yachtID) throws AppBaseException {
        yachtEndpoint.deactivateYacht(yachtID);
        return "listYachts";
    }
}
