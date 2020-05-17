package pl.lodz.p.it.ssbd2020.ssbd02.mok.web;


import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints.UserEndpoint;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ResourceBundle;
import java.util.logging.Logger;


@Named
@RequestScoped
public class EmailPageBean {

    @Inject
    UserEndpoint userEndpoint;

    private String key;

    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private int valid = 5;

    @Inject
    private FacesContext facesContext;
    private ResourceBundle resourceBundle;

    @PostConstruct
    public void init() {
        key = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("key");
        logger.info("Klucz:" + key);
        try {
            userEndpoint.activeAccount(key);
            displayMessage();
        }catch (Exception e){
            displayError(e.getLocalizedMessage());
        }

    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getValid() {
        return valid;
    }

    public void setValid(int valid) {
        this.valid = valid;
    }

    public void displayInit(){
        facesContext.getExternalContext().getFlash().setKeepMessages(true);
        resourceBundle = ResourceBundle.getBundle("resource", facesContext.getViewRoot().getLocale());
    }

    public void displayMessage() {
        displayInit();
        String msg = resourceBundle.getString("activateUser");
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
