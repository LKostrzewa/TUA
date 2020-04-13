package pl.lodz.p.it.ssbd2020.ssbd02.utils;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.Locale;
import java.util.ResourceBundle;

@FacesValidator("emailValidator")
public class EmailValidator implements Validator{

    ResourceBundle bundle = ResourceBundle.getBundle("resource", Locale.getDefault());

    @Override
    public void validate(FacesContext context, UIComponent component, Object obj) throws ValidatorException {
        String email = obj.toString();

        if (email.length() < 6 || email.length() > 30) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("validation.email.size"), null));
        }

        if (!email.matches("^(.+)@(.+)$")) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("validation.email.pattern"), null));
        }

    }
}
