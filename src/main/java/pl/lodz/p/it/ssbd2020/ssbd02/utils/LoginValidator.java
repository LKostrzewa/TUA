package pl.lodz.p.it.ssbd2020.ssbd02.utils;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("loginValidator")
public class LoginValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object obj) throws ValidatorException {
        String login = obj.toString();

        if (login.length() < 6 || login.length() > 15) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login needs to have length between 6 and 15", null));
        }

        if (!login.matches("^[a-zA-Z0-9]+$")) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login can contain letters and numbers only", null));
        }

    }
}
