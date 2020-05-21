package pl.lodz.p.it.ssbd2020.ssbd02.mok.web;


import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints.UserEndpoint;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.PropertyReader;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ResourceBundle;


@Named
@RequestScoped
public class EmailPageBean {

    @Inject
    private UserEndpoint userEndpoint;

    private String key;
    private int valid;

    @Inject
    private FacesContext facesContext;
    private ResourceBundle resourceBundle;

    @PostConstruct
    public void init() {
        PropertyReader propertyReader = new PropertyReader();
        key = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("key");
        valid = Integer.parseInt(propertyReader.getProperty("config", "email_valid_time"));;
        try {
            userEndpoint.activateAccount(key);
            displayMessage();
        }catch (AppBaseException e){
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
