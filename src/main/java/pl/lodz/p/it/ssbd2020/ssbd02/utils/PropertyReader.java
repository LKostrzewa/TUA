package pl.lodz.p.it.ssbd2020.ssbd02.utils;

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Klasa odczytująca własności z plików konfiguracyjnych.
 */
public class PropertyReader {

    /**
     * Metoda zwracająca właśność o podanym kluczu i z pakietu o podanej nazwie.
     *
     * @param bundleName   nazwa pakietu ze stałymi
     * @param propertyName klucz stałej do pobrania
     * @return wiadomość z pakietu znajdująca się pod podanym kluczem
     */
    public String getProperty(String bundleName, String propertyName) {
        return getBundle(bundleName).getString(propertyName);
    }

    /**
     * Metoda zwracająca właśność o podanym kluczu i z pakietu o podanej nazwie wstrzykując podany argument.
     *
     * @param bundleName   nazwa pakietu ze stałymi
     * @param propertyName klucz stałej do pobrania
     * @param argument     argument zawarty w wiadomości
     * @return wiadomość z pakietu znajdująca się pod podanym kluczem
     */
    public String getProperty(String bundleName, String propertyName, String argument) {
        String pattern = getBundle(bundleName).getString(propertyName);
        return MessageFormat.format(pattern, argument);
    }

    /**
     * Metoda zwracająca pakiet o podanej nazwie.
     *
     * @param bundleName nazwa pakietu ze stałymi
     * @return ResourceBundle o podanej nazwie
     */
    private ResourceBundle getBundle(String bundleName) {
        return ResourceBundle.getBundle(bundleName, getCurrentLocale());
    }

    /**
     * Metoda zwracająca aktualnie ustawiony region.
     *
     * @return aktualne Locale
     */
    public Locale getCurrentLocale() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        UIViewRoot viewRoot = facesContext.getViewRoot();
        return viewRoot.getLocale();
    }
}
