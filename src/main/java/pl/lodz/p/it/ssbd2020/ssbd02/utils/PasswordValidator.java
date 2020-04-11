package pl.lodz.p.it.ssbd2020.ssbd02.utils;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("passwordValidator")
public class PasswordValidator implements Validator{

    @Override
    public void validate(FacesContext context, UIComponent component, Object obj) throws ValidatorException {
        String email = obj.toString();

        if (email.length() < 8 || email.length() > 20) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Password needs to have length between 8 and 20", null));
        }

        if (!email.matches("^[a-zA-Z0-9]+$")) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Email does not match any pattern", null));
        }

    }
}
