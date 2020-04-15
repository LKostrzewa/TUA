package pl.lodz.p.it.ssbd2020.ssbd02.moj.web;

import org.modelmapper.ModelMapper;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.UpdateYachtDto;
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

    private UpdateYachtDto updateYachtDto;

    public String openEditYachtPage(Long yachtID) {
        conversation.begin();
        this.yachtID = yachtID;
        this.updateYachtDto = ObjectMapperUtils.map(yachtEndpoint.getYachtById(yachtID), UpdateYachtDto.class);
        return "editYacht";
    }

    public String updateYacht() {
        yachtEndpoint.updateYacht(yachtID, updateYachtDto);
        conversation.end();
        return "listYachts";
    }

    public UpdateYachtDto getUpdateYachtDto() {
        return updateYachtDto;
    }

    public void setUpdateYachtDto(UpdateYachtDto updateYachtDto) {
        this.updateYachtDto = updateYachtDto;
    }
}
