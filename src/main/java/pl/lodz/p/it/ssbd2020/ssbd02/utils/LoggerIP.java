package pl.lodz.p.it.ssbd2020.ssbd02.utils;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

@SessionScoped
public class LoggerIP implements Serializable {
    @Inject
    private FacesContext facesContext;

    private final Logger LOGGER = Logger.getGlobal();

    public void login(){
        LOGGER.log(Level.INFO,"User: \""
                + facesContext.getExternalContext().getUserPrincipal().getName()
                + "\" starts the session with the IP address: "
                + getClientIpAddress());
    }

    public void accessLevelChange(){
        LOGGER.log(Level.INFO, "User: \""
                + FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName()
                + "\" has changed the access level with IP address: "
                + getClientIpAddress());
    }

    private HttpServletRequest getHttpRequestFromFacesContext() {
        return (HttpServletRequest) facesContext
                .getExternalContext()
                .getRequest();
    }

    private String getClientIpAddress() {
        String xForwardedForHeader = getHttpRequestFromFacesContext().getHeader("X-Forwarded-For");
        if (xForwardedForHeader == null) {
            return getHttpRequestFromFacesContext().getRemoteAddr();
        } else {
            return new StringTokenizer(xForwardedForHeader, ",").nextToken().trim();
        }
    }
}
