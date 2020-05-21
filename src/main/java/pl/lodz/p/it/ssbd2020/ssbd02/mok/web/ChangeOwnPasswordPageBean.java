package pl.lodz.p.it.ssbd2020.ssbd02.mok.web;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.ChangeOwnPasswordDto;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints.UserEndpoint;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.ResourceBundle;

@Named
@ViewScoped
public class ChangeOwnPasswordPageBean implements Serializable {
    @Inject
    private UserEndpoint userEndpoint;

    @Inject
    private FacesContext facesContext;

    private ChangeOwnPasswordDto changeOwnPasswordDto;
    private String userLogin;

    private ResourceBundle bundle;

    public ChangeOwnPasswordDto getChangeOwnPasswordDto() {
        return changeOwnPasswordDto;
    }

    public void setChangeOwnPasswordDto(ChangeOwnPasswordDto changeOwnPasswordDto) {
        this.changeOwnPasswordDto = changeOwnPasswordDto;
    }

    @PostConstruct
    public void init() {
        userLogin = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
        this.changeOwnPasswordDto = new ChangeOwnPasswordDto();
        bundle = ResourceBundle.getBundle("resource", getHttpRequestFromFacesContext().getLocale());
    }

    public String changePassword() {
        try {
            userEndpoint.changeOwnPassword(changeOwnPasswordDto);
        } catch (AppBaseException e) {
            String msg = bundle.getString(e.getLocalizedMessage());
            String head = bundle.getString("error");
            facesContext.getExternalContext().getFlash().setKeepMessages(true);
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, head, msg));
            return "changeOwnPassword.xhtml";
        }
        String msg = bundle.getString("shared.password.changeSuccess");
        String head = bundle.getString("success");
        facesContext.getExternalContext().getFlash().setKeepMessages(true);
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, head, msg));

        return "account.xhtml?faces-redirect=true?includeViewParams=true";
    }

    private HttpServletRequest getHttpRequestFromFacesContext() {
        return (HttpServletRequest) facesContext
                .getExternalContext()
                .getRequest();
    }

    public ResourceBundle getBundle() {
        return bundle;
    }
}
