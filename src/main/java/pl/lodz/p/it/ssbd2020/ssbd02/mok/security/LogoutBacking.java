package pl.lodz.p.it.ssbd2020.ssbd02.mok.security;



import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/***
 * Klasa której metoda powoduje powrót do głównej strony logowania (mechanizm wylogowywania)
 */


@Named
@RequestScoped
public class LogoutBacking {

    public String submit() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "login";
    }
}