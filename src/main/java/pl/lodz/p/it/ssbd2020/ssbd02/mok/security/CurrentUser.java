package pl.lodz.p.it.ssbd2020.ssbd02.mok.security;

import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import java.io.IOException;
import java.io.Serializable;

@SessionScoped
@Named
@Interceptors(LoggerInterceptor.class)
public class CurrentUser implements Serializable {
    public final static String ADMIN_ACCESS_LEVEL = "ADMINISTRATOR";
    public final static String MANAGER_ACCESS_LEVEL = "MANAGER";
    public final static String CLIENT_ACCESS_LEVEL = "CLIENT";
    private String currentRole;

    public String getCurrentRole() {
        return currentRole;
    }

    public void setCurrentRole(String currentRole) {
        this.currentRole = currentRole;
    }

    @PostConstruct
    private void init() {
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

    public String allUserAccessLevel() {
        String string = "";
        if (isAdministrator())
            string += ADMIN_ACCESS_LEVEL + " ";
        if (isManager())
            string += MANAGER_ACCESS_LEVEL + " ";
        if (isClient())
            string += CLIENT_ACCESS_LEVEL;

        return string;
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
        return "adminMain";
    }

    public String redirectManager() {
        currentRole = MANAGER_ACCESS_LEVEL;
        return "managerMain";
    }

    public String redirectClient() {
        currentRole = CLIENT_ACCESS_LEVEL;
        return "clientMain";
    }
}
