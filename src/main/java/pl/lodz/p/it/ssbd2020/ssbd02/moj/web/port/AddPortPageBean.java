package pl.lodz.p.it.ssbd2020.ssbd02.moj.web.port;


import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.port.NewPortDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints.PortEndpoint;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ResourceBundle;

@Named
@RequestScoped
public class AddPortPageBean {
    @Inject
    private PortEndpoint portEndpoint;
    @Inject
    private FacesContext facesContext;

    private NewPortDto newPortDto;
    private ResourceBundle resourceBundle;

    public NewPortDto getNewPortDto() {
        return newPortDto;
    }

    public void setNewPortDto(NewPortDto newPortDto) {
        this.newPortDto = newPortDto;
    }

    @PostConstruct
    public void init() {
        newPortDto = new NewPortDto();
    }

    //throws tymczasowe bedzie obslugiwane na widoku
    public String addPort() throws AppBaseException {
        try {
            portEndpoint.addPort(newPortDto);
            displayMessage();
        } catch (AppBaseException e) {
            displayError(e.getLocalizedMessage());
        }
        return "listPorts.xhtml?faces-redirect=true?includeViewParams=true";
    }

    public void displayInit(){
        facesContext.getExternalContext().getFlash().setKeepMessages(true);
        resourceBundle = ResourceBundle.getBundle("resource", facesContext.getViewRoot().getLocale());
    }

    public void displayMessage() {
        displayInit();
        String msg = resourceBundle.getString("port.addInfo");
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
