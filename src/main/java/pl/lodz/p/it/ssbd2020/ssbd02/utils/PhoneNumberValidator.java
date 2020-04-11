package pl.lodz.p.it.ssbd2020.ssbd02.utils;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("phoneNumberValidator")
public class PhoneNumberValidator implements Validator{

    @Override
    public void validate(FacesContext context, UIComponent component, Object obj) throws ValidatorException {
        String number = obj.toString();

        if (!number.matches("^[0-9]{9}")) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Phone number does not match any pattern", null));
        }

    }
}
