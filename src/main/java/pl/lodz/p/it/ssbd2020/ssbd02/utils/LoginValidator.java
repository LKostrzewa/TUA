package pl.lodz.p.it.ssbd2020.ssbd02.utils;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.Locale;
import java.util.ResourceBundle;

@FacesValidator("loginValidator")
public class LoginValidator implements Validator {

    ResourceBundle bundle = ResourceBundle.getBundle("resource", Locale.getDefault());

    @Override
    public void validate(FacesContext context, UIComponent component, Object obj) throws ValidatorException {
        String login = obj.toString();

        if (login.length() < 6 || login.length() > 15) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("validation.login.size"), null));
        }

        if (!login.matches("^[a-zA-Z0-9]+$")) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("validation.login.pattern"), null));
        }

    }
}
