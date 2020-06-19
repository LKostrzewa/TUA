package pl.lodz.p.it.ssbd2020.ssbd02.moj.web.yachtModel;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yachtModel.ListYachtModelDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints.YachtModelEndpoint;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

/**
 * Klasa do obsługi widoku listy modeli jachtów.
 */
@Named
@ViewScoped
public class ListYachtModelPageBean implements Serializable {
    @Inject
    private YachtModelEndpoint yachtModelEndpoint;
    private List<ListYachtModelDto> yachtModels;

    public List<ListYachtModelDto> getYachtModels() {
        return yachtModels;
    }

    public void setYachtModels(List<ListYachtModelDto> yachtModels) {
        this.yachtModels = yachtModels;
    }

    /**
     * Metoda inicjalizująca komponent.
     */
    @PostConstruct
    private void init() {
        this.yachtModels = yachtModelEndpoint.getAllYachtModels();
        yachtModels.sort(Comparator.comparing(ListYachtModelDto::getModel, String::compareToIgnoreCase));
    }

    /**
     * Metoda do deaktywacji modelu jachtu.
     *
     * @param yachtModelId id modelu jachtu, który ma zostać deaktywowany
     * @return strona, na którą użytkownik ma zostać przekierowany
     */
    public String deactivateYachtModel(Long yachtModelId) throws AppBaseException {
        yachtModelEndpoint.deactivateYachtModel(yachtModelId);
        return "listYachtModels";
    }
}
