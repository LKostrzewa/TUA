package pl.lodz.p.it.ssbd2020.ssbd02.mok.security;

import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerIP;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.PropertyReader;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;

/**
 * Klasa do obsługi operacji na rolach obecnie zalogowanego użytkownika.
 */
@SessionScoped
@Named
//@Interceptors(LoggerInterceptor.class)
public class CurrentUser implements Serializable {
    private String ADMIN_ACCESS_LEVEL;
    private String MANAGER_ACCESS_LEVEL;
    private String CLIENT_ACCESS_LEVEL;
    private String currentRole;

    @Inject
    private @Named("LoggerIP") LoggerIP loggerIP;

    public String getCurrentRole() {
        return currentRole;
    }

    public void setCurrentRole(String currentRole) {
        this.currentRole = currentRole;
    }

    /**
     * Metoda inicjalizująca komponent.
     */
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

    /**
     * Metoda sprawdza czy obecnie zalogowany użytkownik ma daną rolę.
     *
     * @param role Rola do sprawdzenia
     * @return true lub false w zależności, czy zalogowany użytkownik ma daną rolę
     */
    public boolean isUserInRole(String role) {
        return FacesContext.getCurrentInstance().getExternalContext().isUserInRole(role);
    }

    /**
     * Metoda sprawdza czy obecnie zalogowany użytkownik ma rolę administratora.
     *
     * @return true lub false w zależności, czy zalogowany użytkownik ma daną rolę
     */
    public boolean isAdministrator() {
        return isUserInRole(ADMIN_ACCESS_LEVEL);
    }

    /**
     * Metoda sprawdza czy obecnie zalogowany użytkownik ma rolę menadżera.
     *
     * @return true lub false w zależności, czy zalogowany użytkownik ma daną rolę
     */
    public boolean isManager() {
        return isUserInRole(MANAGER_ACCESS_LEVEL);
    }

    /**
     * Metoda sprawdza czy obecnie zalogowany użytkownik ma rolę klienta.
     *
     * @return true lub false w zależności, czy zalogowany użytkownik ma daną rolę
     */
    public boolean isClient() {
        return isUserInRole(CLIENT_ACCESS_LEVEL);
    }

    public String getCurrentUserLogin() {
        return FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName();
    }

    /**
     * Metoda sprawdza czy zalogowany użytkownik ma aktualnie wybraną rolę administratora.
     *
     * @return true lub false w zależności, czy zalogowany użytkownik ma aktualnie wybraną daną rolę
     */
    public boolean isNowAdministrator() {
        return currentRole.equals(ADMIN_ACCESS_LEVEL);
    }

    /**
     * Metoda sprawdza czy zalogowany użytkownik ma aktualnie wybraną rolę menadżera.
     *
     * @return true lub false w zależności, czy zalogowany użytkownik ma aktualnie wybraną daną rolę
     */
    public boolean isNowManager() {
        return currentRole.equals(MANAGER_ACCESS_LEVEL);
    }

    /**
     * Metoda sprawdza czy zalogowany użytkownik ma aktualnie wybraną rolę klienta.
     *
     * @return true lub false w zależności, czy zalogowany użytkownik ma aktualnie wybraną daną rolę
     */
    public boolean isNowClient() {
        return currentRole.equals(CLIENT_ACCESS_LEVEL);
    }

    /**
     * Metoda przekierowuje użytkownika na główną stronę administratora.
     *
     * @return strona, na którą ma zostać przekierowany użytkownik
     */
    public String redirectAdmin() {
        currentRole = ADMIN_ACCESS_LEVEL;
        return "/admin/index.xhtml";
    }

    /**
     * Metoda przekierowuje użytkownika na główną stronę menadżera.
     *
     * @return strona, na którą ma zostać przekierowany użytkownik
     */
    public String redirectManager() {
        currentRole = MANAGER_ACCESS_LEVEL;
        return "/manager/index.xhtml";
    }

    /**
     * Metoda przekierowuje użytkownika na główną stronę klienta.
     *
     * @return strona, na którą ma zostać przekierowany użytkownik
     */
    public String redirectClient() {
        currentRole = CLIENT_ACCESS_LEVEL;
        return "/client/index.xhtml";
    }

    /**
     * Metoda do zmiany aktualnego poziomu dostępu użytkownika.
     *
     * @throws IOException wyjątek jeśli przekierowanie zakończy się niepowodzeniem
     *                     (nie powinien wystąpić)
     */
    public void redirectToCurrentRole() throws IOException {
        changeAccessLevel();
        loggerIP.accessLevelChange();
    }

    /**
     * Metoda do przekierowania użytkownika na swoją główną stronę.
     *
     * @throws IOException wyjątek jeśli przekierowanie zakończy się niepowodzeniem
     *                     (nie powinien wystąpić)
     */
    public void redirectToMain() throws IOException {
        if (currentRole == null) {
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.redirect(externalContext.getRequestContextPath() + "/login/login.xhtml");
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        } else changeAccessLevel();
    }

    /**
     * Metoda prywatna do przekierowania użytkownika na odpowiednią stronę w zależności od poziomu dostępu.
     *
     * @throws IOException wyjątek jeśli przekierowanie zakończy się niepowodzeniem
     *                     (nie powinien wystąpić)
     */
    private void changeAccessLevel() throws IOException {
        if (currentRole.equals(ADMIN_ACCESS_LEVEL)) {
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.redirect(externalContext.getRequestContextPath() + redirectAdmin());
        } else if (currentRole.equals(MANAGER_ACCESS_LEVEL)) {
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.redirect(externalContext.getRequestContextPath() + redirectManager());
        } else if (currentRole.equals(CLIENT_ACCESS_LEVEL)) {
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.redirect(externalContext.getRequestContextPath() + redirectClient());
        }
    }
}

