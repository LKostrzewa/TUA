package pl.lodz.p.it.ssbd2020.ssbd02.moj.web.yacht;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yacht.EditYachtDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints.YachtEndpoint;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.ObjectMapperUtils;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ConversationScoped
public class EditYachtPageBean implements Serializable {
    @Inject
    private YachtEndpoint yachtEndpoint;
    @Inject
    private Conversation conversation;
    private Long yachtID;
    private EditYachtDto editYachtDto;

    public EditYachtDto getEditYachtDto() {
        return editYachtDto;
    }

    public void setEditYachtDto(EditYachtDto editYachtDto) {
        this.editYachtDto = editYachtDto;
    }

    public String openEditYachtPage(Long yachtID) throws AppBaseException{
        conversation.begin();
        this.yachtID = yachtID;
        this.editYachtDto = ObjectMapperUtils.map(yachtEndpoint.getYachtById(yachtID), EditYachtDto.class);
        return "editYacht";
    }

    public String updateYacht() throws AppBaseException {
        yachtEndpoint.editYacht(yachtID, editYachtDto);
        conversation.end();
        return "listYachts";
    }
}
