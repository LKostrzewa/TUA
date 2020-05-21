package pl.lodz.p.it.ssbd2020.ssbd02.mok.security;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.UserLoginDto;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints.UserEndpoint;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerIP;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.PropertyReader;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.annotation.FacesConfig;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.Password;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import static javax.faces.application.FacesMessage.SEVERITY_ERROR;
import static javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters.withParams;

@FacesConfig
@Named
@SessionScoped
@Interceptors(LoggerInterceptor.class)
public class LoginPageBean implements Serializable {

    private String ADMIN_ACCESS_LEVEL;
    private String MANAGER_ACCESS_LEVEL;
    private String CLIENT_ACCESS_LEVEL;
    @Inject
    private UserEndpoint userEndpoint;
    @Inject
    private SecurityContext securityContext;
    @Inject
    private FacesContext facesContext;
    @Inject
    private ExternalContext externalContext;
    @Inject
    private LoggerIP loggerIP;

    private UserLoginDto userLoginDto;
    public UserLoginDto getUserLoginDto() {
        return userLoginDto;
    }

    public void setUserLoginDto(UserLoginDto userLoginDto) {
        this.userLoginDto = userLoginDto;
    }

    @PostConstruct
    private void init() {
        userLoginDto = new UserLoginDto();
        PropertyReader propertyReader = new PropertyReader();
        ADMIN_ACCESS_LEVEL = propertyReader.getProperty("config", "ADMIN_ACCESS_LEVEL");
        MANAGER_ACCESS_LEVEL = propertyReader.getProperty("config", "MANAGER_ACCESS_LEVEL");
        CLIENT_ACCESS_LEVEL = propertyReader.getProperty("config", "CLIENT_ACCESS_LEVEL");
    }

    public void login() throws IOException {
        ResourceBundle bundle = ResourceBundle.getBundle("resource", getHttpRequestFromFacesContext().getLocale());
        Credential credential = new UsernamePasswordCredential(userLoginDto.getUsername(), new Password(userLoginDto.getPassword()));
        AuthenticationStatus status = securityContext.authenticate(
                getHttpRequestFromFacesContext(),
                getHttpResponseFromFacesContext(),
                withParams()
                        .credential(credential)
                        .newAuthentication(true));

        facesContext.getExternalContext().getFlash().setKeepMessages(true);
        switch (status) {
            case SEND_CONTINUE:
                facesContext.responseComplete();
                break;
            case SUCCESS:
                try {
                    userLoginDto = userEndpoint.getLoginDtoByLogin();
                } catch (AppBaseException e) {
                    displayError(e.getLocalizedMessage());
                }
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("lastValidLogin"), String.valueOf(userLoginDto.getLastValidLogin())));
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("lastInvalidLogin"), String.valueOf(userLoginDto.getLastInvalidLogin())));

                loggerIP.login();

                try {
                    userEndpoint.saveSuccessAuthenticate();
                } catch (AppBaseException e) {
                    displayError(e.getLocalizedMessage());
                }

                if (FacesContext.getCurrentInstance().getExternalContext().isUserInRole(CLIENT_ACCESS_LEVEL)) {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(externalContext.getRequestContextPath() + "/client/index.xhtml");
                    break;
                }
                if (FacesContext.getCurrentInstance().getExternalContext().isUserInRole(MANAGER_ACCESS_LEVEL)) {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(externalContext.getRequestContextPath() + "/manager/index.xhtml");
                    break;
                }
                if (FacesContext.getCurrentInstance().getExternalContext().isUserInRole(ADMIN_ACCESS_LEVEL)) {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(externalContext.getRequestContextPath() + "/admin/index.xhtml");
                    break;
                }
                break;
            case SEND_FAILURE:
                try {
                    userEndpoint.saveFailureAuthenticate();
                } catch (AppBaseException e) {
                    facesContext.addMessage(null,
                            new FacesMessage(SEVERITY_ERROR, bundle.getString("error"), bundle.getString("authenticationFailed")));

                }
                externalContext.redirect(externalContext.getRequestContextPath() + "/login/errorLogin.xhtml");
                break;
            case NOT_DONE:
                break;
        }
    }

    private void displayError(String message) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("resource", facesContext.getViewRoot().getLocale());
        String msg = resourceBundle.getString(message);
        String head = resourceBundle.getString("error");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, head, msg));

    }

    private HttpServletRequest getHttpRequestFromFacesContext() {
        return (HttpServletRequest) facesContext
                .getExternalContext()
                .getRequest();
    }

    private HttpServletResponse getHttpResponseFromFacesContext() {
        return (HttpServletResponse) facesContext
                .getExternalContext()
                .getResponse();
    }
}
