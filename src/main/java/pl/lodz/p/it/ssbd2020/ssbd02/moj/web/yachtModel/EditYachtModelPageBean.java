package pl.lodz.p.it.ssbd2020.ssbd02.moj.web.yachtModel;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yachtModel.EditYachtModelDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints.YachtModelEndpoint;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.ObjectMapperUtils;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ConversationScoped
public class EditYachtModelPageBean implements Serializable {
    @Inject
    private YachtModelEndpoint yachtModelEndpoint;
    @Inject
    private Conversation conversation;
    private Long yachtModelId;
    private EditYachtModelDto editYachtModelDto;

    public EditYachtModelDto getEditYachtModelDto() {
        return editYachtModelDto;
    }

    public void setEditYachtModelDto(EditYachtModelDto editYachtModelDto) {
        this.editYachtModelDto = editYachtModelDto;
    }

    public String openEditYachtModelPage(Long yachtModelID) {
        conversation.begin();
        this.yachtModelId = yachtModelID;
        this.editYachtModelDto = ObjectMapperUtils.map(yachtModelEndpoint.getYachtModelById(yachtModelID), EditYachtModelDto.class);
        return "/manager/editYachtModel.xhtml?faces-redirect=true";
    }

    public String editYachtModel() throws AppBaseException {
        yachtModelEndpoint.editYachtModel(yachtModelId, editYachtModelDto);
        conversation.end();
        return "/manager/yachtModelDetails.xhtml?faces-redirect=true";
    }
}
