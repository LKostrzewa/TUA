package pl.lodz.p.it.ssbd2020.ssbd02.moj.web.port;

import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.port.UpdatePortDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints.PortEndpoint;

import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ConversationScoped
public class EditPortPageBean implements Serializable {

    @EJB
    private PortEndpoint portEndpoint;

    @Inject
    private Conversation conversation;

    private UpdatePortDto updatePortDto;

    public EditPortPageBean() {
    }

    public String onClick(UpdatePortDto user) {
        conversation.begin();
        this.updatePortDto = user;
        return "/manager/editPort.xhtml?faces-redirect=true";
    }

    public String onFinish() {
        //portEndpoint.updatePort(updatePortDto.getId(),updatePortDto);
        conversation.end();
        return "/manager/listPorts.xhtml?faces-redirect=true";
    }

    public UpdatePortDto getUpdatePortDto() {
        return updatePortDto;
    }

    public void setUpdatePortDto(UpdatePortDto updatePortDto) {
        this.updatePortDto = updatePortDto;
    }


}
