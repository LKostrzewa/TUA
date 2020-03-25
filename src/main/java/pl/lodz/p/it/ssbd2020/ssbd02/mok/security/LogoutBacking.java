package pl.lodz.p.it.ssbd2020.ssbd02.mok.security;



import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
@RequestScoped
public class LogoutBacking {

    public String submit() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "main";
    }
}