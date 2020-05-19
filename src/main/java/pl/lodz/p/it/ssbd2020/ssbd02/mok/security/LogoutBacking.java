package pl.lodz.p.it.ssbd2020.ssbd02.mok.security;

import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import java.io.IOException;
import java.io.Serializable;

/***
 * Klasa której metoda powoduje powrót do głównej strony logowania (mechanizm wylogowywania)
 */

@Named
@RequestScoped
@Interceptors(LoggerInterceptor.class)
public class LogoutBacking implements Serializable {
    @Inject
    private ExternalContext externalContext;
    public void submit() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect(externalContext.getRequestContextPath() + "/login/login.xhtml");
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
    }
}