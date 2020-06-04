package pl.lodz.p.it.ssbd2020.ssbd02.utils;

import pl.lodz.p.it.ssbd2020.ssbd02.mok.security.CurrentUser;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Klasa loggera, która zapisuje do dziennika zdarzeń moment logowania (login + adress IP)
 * oraz zmianę poziomu dostępu (login + adress IP).
 */
@SessionScoped
public class LoggerIP implements Serializable {
    private final Logger LOGGER = Logger.getGlobal();
    @Inject
    private FacesContext facesContext;
    @Inject
    private CurrentUser currentUser;

    /**
     * Metoda która zapisuje do dziennika zdarzeń informację o uwierzytelnieniu użytkownika.
     */
    public void login() {
        LOGGER.log(Level.INFO, "User: \""
                + facesContext.getExternalContext().getUserPrincipal().getName()
                + "\" starts the session with access level: \""
                + currentUser.getCurrentRole()
                + "\" and the IP address: "
                + getClientIpAddress());
    }

    /**
     * Metoda która zapisuje do dziennika zdarzeń informację o zmianie poziomu dostępu przez użytkownika.
     */
    public void accessLevelChange() {
        LOGGER.log(Level.INFO, "User: \""
                + facesContext.getExternalContext().getUserPrincipal().getName()
                + "\" has changed the access level to: \""
                + currentUser.getCurrentRole()
                + "\", with IP address: "
                + getClientIpAddress());
    }

    /**
     * Metoda wyciągająca z FacesContext żądanie http.
     *
     * @return obiekt HttpServletRequest przechowujący informację o żądaniu http
     */
    private HttpServletRequest getHttpRequestFromFacesContext() {
        return (HttpServletRequest) facesContext
                .getExternalContext()
                .getRequest();
    }

    /**
     * Metoda pobiera adress IP z żądania http.
     *
     * @return String z adresem IP, z którego nastąpiło żądanie
     */
    private String getClientIpAddress() {
        String xForwardedForHeader = getHttpRequestFromFacesContext().getHeader("X-Forwarded-For");
        if (xForwardedForHeader == null) {
            return getHttpRequestFromFacesContext().getRemoteAddr();
        } else {
            return new StringTokenizer(xForwardedForHeader, ",").nextToken().trim();
        }
    }
}
