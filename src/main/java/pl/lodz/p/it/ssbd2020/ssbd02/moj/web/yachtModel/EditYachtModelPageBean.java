package pl.lodz.p.it.ssbd2020.ssbd02.moj.web.yachtModel;



import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yachtModel.UpdateYachtModelDTO;
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
    private Long yachtModelID;
    private UpdateYachtModelDTO updateYachtModelDTO;

    public String openEditYachtModelPage(Long yachtModelID) {
        conversation.begin();
        this.yachtModelID = yachtModelID;
        this.updateYachtModelDTO = ObjectMapperUtils.map(yachtModelEndpoint.getYachtById(yachtModelID), UpdateYachtModelDTO.class);
        return "editYachtModel";
    }

    public String updateYachtModel() {
        yachtModelEndpoint.updateYachtModel(yachtModelID,updateYachtModelDTO);
        conversation.end();
        return "listYachtModels";
    }

    public UpdateYachtModelDTO getUpdateYachtModelDTO() {
        return updateYachtModelDTO;
    }

    public void setUpdateYachtModelDTO(UpdateYachtModelDTO updateYachtModelDTO) {
        this.updateYachtModelDTO = updateYachtModelDTO;
    }
}
