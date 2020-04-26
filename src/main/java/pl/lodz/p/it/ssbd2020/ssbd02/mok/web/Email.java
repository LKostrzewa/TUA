package pl.lodz.p.it.ssbd2020.ssbd02.mok.web;


import pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints.UserEndpoint;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.logging.Logger;


@Named
@RequestScoped
public class Email {

    @Inject
    UserEndpoint userEndpoint;

    private String key;

    private static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private int valid = 5;

    @PostConstruct
    public void init() {

        key = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("key");
        logger.info("Klucz:" + key);
        userEndpoint.confirmActivationCode(key);

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
}
