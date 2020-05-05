package pl.lodz.p.it.ssbd2020.ssbd02.mok.web;


import pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints.UserEndpoint;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.ResourceBundle;

@Named
@RequestScoped
public class UserResetPasswordBean implements Serializable {
    @Inject
    private UserEndpoint userEndpoint;

    @Inject
    private FacesContext facesContext;

    @Pattern(regexp = "^[a-zA-Z0-9.!#$%&''*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?$",
            message = "{validation.email}")
    private String email;
    private ResourceBundle bundle;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @PostConstruct
    public void init() {

        bundle = ResourceBundle.getBundle("resource", getHttpRequestFromFacesContext().getLocale());
    }

    public String sendResetPasswordEmail() {

        return "login.xhtml?faces-redirect=true";
    }


    public ResourceBundle getBundle() {
        return bundle;
    }

    public void setBundle(ResourceBundle bundle) {
        this.bundle = bundle;
    }

    private HttpServletRequest getHttpRequestFromFacesContext() {
        return (HttpServletRequest) facesContext
                .getExternalContext()
                .getRequest();
    }
}
