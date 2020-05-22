package pl.lodz.p.it.ssbd2020.ssbd02.moj.web.rental;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.rental.ListAllRentalsDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints.RentalEndpoint;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;

@Named
@ViewScoped
public class ListRentalsByYachtPageBean implements Serializable {
    @Inject
    private RentalEndpoint rentalEndpoint;
    @Inject
    private FacesContext facesContext;

    private List<ListAllRentalsDto> rentals;

    private ResourceBundle resourceBundle;

    public List<ListAllRentalsDto> getRentals() {
        return rentals;
    }

    public void setRentals(List<ListAllRentalsDto> rentals) {
        this.rentals = rentals;
    }

    @PostConstruct
    private void init(String yachtName){
        try {
            this.rentals = rentalEndpoint.getRentalsByYacht(yachtName);
            displayMessage();
        } catch (AppBaseException e){
            displayError(e.getLocalizedMessage());
        }
    }

    public void displayInit(){
        facesContext.getExternalContext().getFlash().setKeepMessages(true);
        resourceBundle = ResourceBundle.getBundle("resource", facesContext.getViewRoot().getLocale());
    }

    public void displayMessage() {
        displayInit();
        String msg = resourceBundle.getString("CHyba tyy");
        String head = resourceBundle.getString("success");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, head, msg));
    }

    private void displayError(String message) {
        displayInit();
        String msg = resourceBundle.getString(message);
        String head = resourceBundle.getString("error");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, head, msg));
    }
}
