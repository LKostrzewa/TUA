package pl.lodz.p.it.ssbd2020.ssbd02.mok.security;


import pl.lodz.p.it.ssbd2020.ssbd02.utils.BCryptPasswordHash;

import javax.enterprise.context.RequestScoped;
import javax.faces.annotation.FacesConfig;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import javax.security.enterprise.authentication.mechanism.http.CustomFormAuthenticationMechanismDefinition;
import javax.security.enterprise.authentication.mechanism.http.LoginToContinue;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Named
@RequestScoped
public class LoginBacking {

    private String password;
    private String login;

    @Inject
    private SecurityContext securityContext;

    @Inject
    private ExternalContext externalContext;

    @Inject
    private FacesContext facesContext;

    public String submit() throws IOException {

        String page =  metoda();
        if(page.equals("main")){
            return "/shared/index.xhtml";
        }
        return "/login/errorLogin.xhtml?redirect=true";




    }
    private String metoda() throws IOException {
        switch (continueAuthentication()) {
            //The authentication mechanism was called and a multi-step authentication dialog with the caller has been started (for instance, the caller has been redirected to a login page).
            //Simply said authentication is "in progress".
            case SEND_CONTINUE:


                facesContext.responseComplete();
                System.out.println("-SEND_CONTINUE"); // chwilowo do czasu loggera
                return metoda();

            //The authentication mechanism was called but the caller was not successfully authenticated and therefor the caller principal will not be made available.
            case SEND_FAILURE:

                facesContext.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login or password failed", null));
           //     String string = externalContext.getRequestContextPath();
            //    externalContext.redirect(externalContext.getRequestContextPath() + "/login/errorLogin.xhtml");
                System.out.println("-SEND_FAILURE"); // chwilowo do czasu loggera
                return "/login/errorLogin.xhtml";

            //The authentication mechanism was called and the caller was successfully authenticated.
            case SUCCESS:
                facesContext.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Login succeed", null));
            //    String string2 = externalContext.getRequestContextPath();
            //    externalContext.redirect(externalContext.getRequestContextPath() + "/shared/index.xhtml");
                System.out.println("-SUCCESS"); // chwilowo do czasu loggera

                return "main";

            //The authentication mechanism was called, but decided not to authenticate.
            case NOT_DONE:
                System.out.println("-NOT_DONE"); // chwilowo do czasu loggera
                return "/login/errorLogin.xhtml";

        }
        return "/login/errorLogin.xhtml";
    }

    private AuthenticationStatus continueAuthentication() {
        return securityContext.authenticate(
                (HttpServletRequest) externalContext.getRequest(),
                (HttpServletResponse) externalContext.getResponse(),
                AuthenticationParameters.withParams().credential(new UsernamePasswordCredential(login, password))
        );
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }


}
