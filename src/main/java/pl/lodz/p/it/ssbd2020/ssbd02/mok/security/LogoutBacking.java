package pl.lodz.p.it.ssbd2020.ssbd02.mok.security;

import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import java.io.Serializable;

/***
 * Klasa której metoda powoduje powrót do głównej strony logowania (mechanizm wylogowywania)
 */


@Named
@RequestScoped
@Interceptors(LoggerInterceptor.class)
public class LogoutBacking implements Serializable {

    public String submit() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "login";
    }
}