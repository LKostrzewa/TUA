package pl.lodz.p.it.ssbd2020.ssbd02.moj.web.yacht;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yacht.NewYachtDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints.YachtEndpoint;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ResourceBundle;

@Named
@RequestScoped
public class AddYachtPageBean {
    @Inject
    private YachtEndpoint yachtEndpoint;
    @Inject
    private FacesContext facesContext;

    private NewYachtDto newYachtDto;
    private ResourceBundle resourceBundle;

    public NewYachtDto getNewYachtDto() {
        return newYachtDto;
    }

    public void setNewYachtDto(NewYachtDto newYachtDto) {
        this.newYachtDto = newYachtDto;
    }

    @PostConstruct
    public void init() {
        newYachtDto = new NewYachtDto();
    }

    public String addNewYacht() throws AppBaseException {
        try {
            yachtEndpoint.addYacht(newYachtDto);
            displayMessage();
        } catch (AppBaseException e) {
            displayError(e.getLocalizedMessage());
        }
        return "listYachts.xhtml?faces-redirect=true?includeViewParams=true";
    }

    public void displayInit(){
        facesContext.getExternalContext().getFlash().setKeepMessages(true);
        resourceBundle = ResourceBundle.getBundle("resource", facesContext.getViewRoot().getLocale());
    }

    public void displayMessage() {
        displayInit();
        String msg = resourceBundle.getString("users.addInfo");
        String head = resourceBundle.getString("success");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, head, msg));
    }

    private void displayError(String message) {
        displayInit();
        String msg = resourceBundle.getString(message);
        String head = resourceBundle.getString("error");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, head, msg));
    }
}
