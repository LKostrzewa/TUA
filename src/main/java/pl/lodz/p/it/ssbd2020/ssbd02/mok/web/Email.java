package pl.lodz.p.it.ssbd2020.ssbd02.mok.web;


import pl.lodz.p.it.ssbd2020.ssbd02.mok.managers.UserManager;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.annotation.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.logging.Logger;


@Named
@RequestScoped
public class Email {

    @Inject
   UserManager userManager;

    //@ManagedProperty(value="#{param.key}")
    private String key;

    private static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private boolean valid=true;

    @PostConstruct
    public void init() {
        // Get User based on activation key.
        // Delete activation key from database.
        // Login user.
        //userManager.confirmActivationCode(key);
        key =FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("key");
        logger.info("GIT");
        logger.info("Klucz:"+key);
        userManager.confirmActivationCode(key);

    }


    public static String getCurrentUrlFromRequest(ServletRequest request)
    {
        if (! (request instanceof HttpServletRequest))
            return null;

        return getCurrentUrlFromRequest((HttpServletRequest)request);
    }

    public static String getCurrentUrlFromRequest(HttpServletRequest request)
    {
        StringBuffer requestURL = request.getRequestURL();
        String queryString = request.getQueryString();

        if (queryString == null)
            return requestURL.toString();

        return requestURL.append('?').append(queryString).toString();
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}
