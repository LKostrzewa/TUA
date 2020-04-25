package pl.lodz.p.it.ssbd2020.ssbd02.mok.security;

import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.UserLoginDto;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints.UserEndpoint;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;

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
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import static javax.faces.application.FacesMessage.SEVERITY_ERROR;
import static javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters.withParams;

@FacesConfig
@Named
@SessionScoped
@Interceptors(LoggerInterceptor.class)
public class LoginBean implements Serializable {
    public final static String ADMIN_ACCESS_LEVEL = "ADMINISTRATOR";
    public final static String MANAGER_ACCESS_LEVEL = "MANAGER";
    public final static String CLIENT_ACCESS_LEVEL = "CLIENT";
    @Inject
    private UserEndpoint userEndpoint;
    @Inject
    private SecurityContext securityContext;
    @Inject
    private FacesContext facesContext;
    @Inject
    private ExternalContext externalContext;
    @NotBlank(message = "{username.message}")
    private String username;
    @NotBlank(message = "{password.message}")
    private String password;
    ResourceBundle bundle = ResourceBundle.getBundle("resource", Locale.getDefault());

    private UserLoginDto userLoginDto;

    public UserLoginDto getUserLoginDto() {
        return userLoginDto;
    }

    public void setUserLoginDto(UserLoginDto userLoginDto) {
        this.userLoginDto = userLoginDto;
    }

    public void login() throws IOException {
        Credential credential = new UsernamePasswordCredential(username, new Password(password));
        AuthenticationStatus status = securityContext.authenticate(
                getHttpRequestFromFacesContext(),
                getHttpResponseFromFacesContext(),
                withParams()
                        .credential(credential)
                        .newAuthentication(true));
        displayMessage();
        userLoginDto = userEndpoint.getLoginDtoByLogin(username);
        switch (status) {
            case SEND_CONTINUE:
                facesContext.responseComplete();
                break;
            case SUCCESS:
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("last_valid_login"), String.valueOf(userLoginDto.getLastValidLogin())));
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("last_invalid_login"), String.valueOf(userLoginDto.getLastInvalidLogin())));
                userLoginDto.setLastValidLogin(new Date());
                userEndpoint.editUserLastLogin(userLoginDto, userLoginDto.getId());
                if(FacesContext.getCurrentInstance().getExternalContext().isUserInRole(CLIENT_ACCESS_LEVEL)) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(externalContext.getRequestContextPath() + "/client/index.xhtml");
                    break;
                }
                if(FacesContext.getCurrentInstance().getExternalContext().isUserInRole(MANAGER_ACCESS_LEVEL)){
                    FacesContext.getCurrentInstance().getExternalContext().redirect(externalContext.getRequestContextPath() + "/manager/index.xhtml");
                    break;
                }
                if(FacesContext.getCurrentInstance().getExternalContext().isUserInRole(ADMIN_ACCESS_LEVEL)){
                    FacesContext.getCurrentInstance().getExternalContext().redirect(externalContext.getRequestContextPath() + "/admin/index.xhtml");
                    break;
                }
                break;
            case SEND_FAILURE:
                userLoginDto.setLastInvalidLogin(new Date());
                userEndpoint.editUserLastLogin(userLoginDto, userLoginDto.getId());
                facesContext.addMessage(null,
                        new FacesMessage(SEVERITY_ERROR, bundle.getString("error"), bundle.getString("authentication_failed")));
                externalContext.redirect(externalContext.getRequestContextPath() + "/login/errorLogin.xhtml");
                break;
            case NOT_DONE:
                break;
        }
    }

    public void displayMessage() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getFlash().setKeepMessages(true);
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
