package pl.lodz.p.it.ssbd2020.ssbd02.moj.web.port;

import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.port.EditPortDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints.PortEndpoint;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ConversationScoped
public class EditPortPageBean implements Serializable {
    @Inject
    private PortEndpoint portEndpoint;
    @Inject
    private Conversation conversation;
    private EditPortDto editPortDto;

    public EditPortDto getEditPortDto() {
        return editPortDto;
    }

    public void setEditPortDto(EditPortDto editPortDto) {
        this.editPortDto = editPortDto;
    }

    public String openEditPortPage(EditPortDto user) {
        conversation.begin();
        this.editPortDto = user;
        return "/manager/editPort.xhtml?faces-redirect=true";
    }

    public String editPort() {
        //portEndpoint.editPort(editPortDto.getId(),editPortDto);
        conversation.end();
        return "/manager/listPorts.xhtml?faces-redirect=true";
    }
}
