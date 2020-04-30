package pl.lodz.p.it.ssbd2020.ssbd02.utils;

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class PropertyReader {

    public String getProperty(String bundleName, String propertyName) {
        return getBundle(bundleName).getString(propertyName);
    }

    public String getProperty(String bundleName, String propertyName, String argument) {
        String pattern = getBundle(bundleName).getString(propertyName);
        return MessageFormat.format(pattern, argument);
    }

    private ResourceBundle getBundle(String bundleName) {
        return ResourceBundle.getBundle(bundleName, getCurrentLocale());
    }

    private Locale getCurrentLocale(){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        UIViewRoot viewRoot = facesContext.getViewRoot();
        return viewRoot.getLocale();
    }
}
