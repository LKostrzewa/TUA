package pl.lodz.p.it.ssbd2020.ssbd02.moj.web.rental;

import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.AddRentalDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints.RentalEndpoint;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.security.CurrentUser;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ConversationScoped
public class AddRentalPageBean implements Serializable {
    @Inject
    private Conversation conversation;
    @Inject
    private RentalEndpoint rentalEndpoint;
    @Inject
    private CurrentUser currentUser;
    private AddRentalDto addRentalDto;

    public AddRentalDto getAddRentalDto() {
        return addRentalDto;
    }

    public void setAddRentalDto(AddRentalDto addRentalDto) {
        this.addRentalDto = addRentalDto;
    }

    public String onClick(String yachtName) {
        conversation.begin();
        this.addRentalDto.setYachtName(yachtName);
        this.addRentalDto.setUserLogin(currentUser.getCurrentUserLogin());
        return "addRental.xhtml?faces-redirect=true";
    }

    public String onFinish() {
        rentalEndpoint.addRental(addRentalDto);
        conversation.end();
        return "listRentals.xhtml?faces-redirect=true";
    }
}
