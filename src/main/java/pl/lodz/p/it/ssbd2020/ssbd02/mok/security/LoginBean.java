package pl.lodz.p.it.ssbd2020.ssbd02.mok.security;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.UserAccessLevelDto;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.UserLoginDto;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints.UserAccessLevelEndpoint;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints.UserEndpoint;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.PropertyReader;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.SendEmail;

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
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import static javax.faces.application.FacesMessage.SEVERITY_ERROR;
import static javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters.withParams;

@FacesConfig
@Named
@SessionScoped
@Interceptors(LoggerInterceptor.class)
public class LoginBean implements Serializable {
    private final Logger LOGGER = Logger.getGlobal();
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
    private UserAccessLevelEndpoint userAccessLevelEndpoint;
    @NotBlank(message = "{username.message}")
    private String username;
    @NotBlank(message = "{password.message}")
    private String password;
    private UserLoginDto userLoginDto;

    public UserLoginDto getUserLoginDto() {
        return userLoginDto;
    }

    public void setUserLoginDto(UserLoginDto userLoginDto) {
        this.userLoginDto = userLoginDto;
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

    @PostConstruct
    private void init() {
        PropertyReader propertyReader = new PropertyReader();
        ADMIN_ACCESS_LEVEL = propertyReader.getProperty("config", "ADMIN_ACCESS_LEVEL");
        MANAGER_ACCESS_LEVEL = propertyReader.getProperty("config", "MANAGER_ACCESS_LEVEL");
        CLIENT_ACCESS_LEVEL = propertyReader.getProperty("config", "CLIENT_ACCESS_LEVEL");
    }

    public void login() throws IOException {
        ResourceBundle bundle = ResourceBundle.getBundle("resource", getHttpRequestFromFacesContext().getLocale());
        Credential credential = new UsernamePasswordCredential(username, new Password(password));
        AuthenticationStatus status = securityContext.authenticate(
                getHttpRequestFromFacesContext(),
                getHttpResponseFromFacesContext(),
                withParams()
                        .credential(credential)
                        .newAuthentication(true));
        displayMessage();

        switch (status) {
            case SEND_CONTINUE:
                facesContext.responseComplete();
                break;
            case SUCCESS:
                try {
                    userLoginDto = userEndpoint.getLoginDtoByLogin(username);
                } catch (AppBaseException e) {
                    displayError(e.getLocalizedMessage());
                    break;
                }

                if (userLoginDto.getLastValidLogin() != null) {
                    facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                            bundle.getString("lastValidLogin"),
                            String.valueOf(userLoginDto.getLastValidLogin())));
                }

                if (userLoginDto.getLastInvalidLogin() != null) {
                    facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                            bundle.getString("lastInvalidLogin"),
                            String.valueOf(userLoginDto.getLastInvalidLogin())));
                }


                userLoginDto.setLastLoginIp(getClientIpAddress());
                userLoginDto.setLastValidLogin(new Date());
                userLoginDto.setInvalidLoginAttempts(0);

                try {
                    userEndpoint.editUserLastLoginAndInvalidLoginAttempts(userLoginDto, userLoginDto.getId(), 0);
                } catch (AppBaseException e) {
                    displayError(e.getLocalizedMessage());
                    break;
                }

                LOGGER.log(Level.INFO, "User: " + username + " starts the session with the IP address: " + getClientIpAddress());

                UserAccessLevelDto userAccessLevelDto = userAccessLevelEndpoint.findAccessLevelById(userLoginDto.getId());
                if (userAccessLevelDto.getAdmin().getLeft()) {
                    SendEmail sendEmail = new SendEmail();
                    sendEmail.sendEmailNotificationAboutNewAdminAuthentication(userLoginDto.getEmail(), getClientIpAddress());
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
                    userLoginDto = userEndpoint.getLoginDtoByLogin(username);
                } catch (AppBaseException e) {
                    displayError(e.getLocalizedMessage());
                    externalContext.redirect(externalContext.getRequestContextPath() + "/login/errorLogin.xhtml");
                    break;
                }


                if (userLoginDto != null && !userLoginDto.getLocked()) {
                    Integer attempts = userLoginDto.getInvalidLoginAttempts();
                    attempts += 1;

                    if (attempts <= 2) {
                        facesContext.addMessage(null, new FacesMessage(SEVERITY_ERROR, bundle.getString("error"), attempts + " " + bundle.getString("invalidLoginAttempts")));
                    } else {
                        facesContext.addMessage(null, new FacesMessage(SEVERITY_ERROR, bundle.getString("block"), attempts + " " + bundle.getString("invalidLoginAttempts") + bundle.getString("blockAccount")));
                    }
                    userLoginDto.setLastInvalidLogin(new Date());

                    try {
                        userEndpoint.editUserLastLoginAndInvalidLoginAttempts(userLoginDto, userLoginDto.getId(), attempts);
                    } catch (AppBaseException e) {
                        displayError(e.getLocalizedMessage());
                        externalContext.redirect(externalContext.getRequestContextPath() + "/login/errorLogin.xhtml");
                        break;
                    }


                    facesContext.addMessage(null,
                            new FacesMessage(SEVERITY_ERROR, bundle.getString("error"), bundle.getString("authenticationFailed")));
                }

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

    private void displayError(String message) {
        facesContext.getExternalContext().getFlash().setKeepMessages(true);
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

    public String getClientIpAddress() {
        String xForwardedForHeader = getHttpRequestFromFacesContext().getHeader("X-Forwarded-For");
        if (xForwardedForHeader == null) {
            return getHttpRequestFromFacesContext().getRemoteAddr();
        } else {
            return new StringTokenizer(xForwardedForHeader, ",").nextToken().trim();
        }
    }
}
