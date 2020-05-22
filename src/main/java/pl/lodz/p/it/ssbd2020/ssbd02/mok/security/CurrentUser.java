package pl.lodz.p.it.ssbd2020.ssbd02.mok.security;

import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerIP;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.PropertyReader;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.ExcludeClassInterceptors;
import javax.interceptor.Interceptors;
import java.io.IOException;
import java.io.Serializable;

@SessionScoped
@Named
//@Interceptors(LoggerInterceptor.class)
public class CurrentUser implements Serializable {
    private String ADMIN_ACCESS_LEVEL;
    private String MANAGER_ACCESS_LEVEL;
    private String CLIENT_ACCESS_LEVEL;
    private String currentRole;

    @Inject
    private LoggerIP loggerIP;

    public String getCurrentRole() {
        return currentRole;
    }

    public void setCurrentRole(String currentRole) {
        this.currentRole = currentRole;
    }

    @PostConstruct
    private void init() {
        PropertyReader propertyReader = new PropertyReader();
        ADMIN_ACCESS_LEVEL = propertyReader.getProperty("config", "ADMIN_ACCESS_LEVEL");
        MANAGER_ACCESS_LEVEL = propertyReader.getProperty("config", "MANAGER_ACCESS_LEVEL");
        CLIENT_ACCESS_LEVEL = propertyReader.getProperty("config", "CLIENT_ACCESS_LEVEL");

        if (isClient() && currentRole == null) currentRole = CLIENT_ACCESS_LEVEL;
        if (isManager() && currentRole == null) currentRole = MANAGER_ACCESS_LEVEL;
        if (isAdministrator() && currentRole == null) currentRole = ADMIN_ACCESS_LEVEL;
    }

    public boolean isUserInRole(String role) {  // sprawdza jaka jest rola zalogowanego uzytkownika
        return FacesContext.getCurrentInstance().getExternalContext().isUserInRole(role);
    }

    public boolean isAdministrator() {
        return isUserInRole(ADMIN_ACCESS_LEVEL);
    }

    public boolean isManager() {
        return isUserInRole(MANAGER_ACCESS_LEVEL);
    }

    public boolean isClient() {
        return isUserInRole(CLIENT_ACCESS_LEVEL);
    }

    public String getCurrentUserLogin() {
        return FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName();
    }

    public boolean isNowAdministrator() {
        return currentRole.equals(ADMIN_ACCESS_LEVEL);
    }

    public boolean isNowManager() {
        return currentRole.equals(MANAGER_ACCESS_LEVEL);
    }

    public boolean isNowClient() {
        return currentRole.equals(CLIENT_ACCESS_LEVEL);
    }

    public String redirectAdmin() {
        currentRole = ADMIN_ACCESS_LEVEL;
        return "/admin/index.xhtml";
    }

    public String redirectManager() {
        currentRole = MANAGER_ACCESS_LEVEL;
        return "/manager/index.xhtml";
    }

    public String redirectClient() {
        currentRole = CLIENT_ACCESS_LEVEL;
        return "/client/index.xhtml";
    }

    /**
     * Metoda do zmiany aktualnego poziomu dostępu użytkownika
     *
     * @throws IOException wyjątek jeżeli przekierowanie zakończy się niepowodzeniem
     * (nie powinien wystąpić)
     */
    public void redirectToCurrentRole() throws IOException {
        changeAccessLevel();
        loggerIP.accessLevelChange();
    }

    /**
     * Metoda do przekierowania użytkownika na swoją główną stronę
     *
     * @throws IOException wyjątek jeżeli przekierowanie zakończy się niepowodzeniem
     * (nie powinien wystąpić)
     */
    public void redirectToMain() throws IOException {
        if(currentRole == null) {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/login/login.xhtml");
        }
        else changeAccessLevel();
    }

    /**
     * Metoda prywatna do przekierowania użytkownika na odpowiednią stronę w zależności od poziomu dostępu
     *
     * @throws IOException wyjątek jeżeli przekierowanie zakończy się niepowodzeniem
     * (nie powinien wystąpić)
     */
    private void changeAccessLevel() throws IOException {
        if (currentRole.equals(ADMIN_ACCESS_LEVEL)) {
                FacesContext.getCurrentInstance()
                        .getExternalContext()
                        .redirect(redirectAdmin());
        }
        else if (currentRole.equals(MANAGER_ACCESS_LEVEL)) {
                FacesContext.getCurrentInstance()
                        .getExternalContext()
                        .redirect(redirectManager());
        }
        else if (currentRole.equals(CLIENT_ACCESS_LEVEL)) {
                FacesContext.getCurrentInstance()
                        .getExternalContext()
                        .redirect(redirectClient());
        }
    }
}

