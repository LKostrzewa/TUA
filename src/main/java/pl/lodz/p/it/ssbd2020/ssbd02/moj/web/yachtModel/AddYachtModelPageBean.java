package pl.lodz.p.it.ssbd2020.ssbd02.moj.web.yachtModel;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yachtModel.NewYachtModelDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints.YachtModelEndpoint;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Klasa do obsługi widoku dodawania modelu jachtu.
 */
@Named
@RequestScoped
public class AddYachtModelPageBean {
    @Inject
    private YachtModelEndpoint yachtModelEndpoint;
    private NewYachtModelDto newYachtModelDto;

    public NewYachtModelDto getNewYachtModelDto() {
        return newYachtModelDto;
    }

    public void setNewYachtModelDto(NewYachtModelDto newYachtModelDto) {
        this.newYachtModelDto = newYachtModelDto;
    }

    /**
     * Metoda inicjalizująca komponent.
     */
    @PostConstruct
    public void init() {
        newYachtModelDto = new NewYachtModelDto();
    }

    /**
     * Metoda obsługująca wciśnięcie guzika do dodania nowego modelu jachtu.
     *
     * @return strona na którą zostanie przekierowany użytkownik
     */
    public String addNewYachtModel() throws AppBaseException {
        yachtModelEndpoint.addYachtModel(newYachtModelDto);
        return "/manager/listYachtModels.xhtml?faces-redirect=true";
    }
}
