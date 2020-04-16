package pl.lodz.p.it.ssbd2020.ssbd02.moj.web.opinion;

import org.modelmapper.ModelMapper;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.opinion.UpdateOpinionDTO;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints.OpinionEndpoint;

import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ConversationScoped
public class EditOpinionPageBean implements Serializable {

    @Inject
    private OpinionEndpoint opinionEndpoint;
    @Inject
    private Conversation conversation;
    @Inject
    private ModelMapper modelMapper;

    private Long opinionId;

    private UpdateOpinionDTO updateOpinionDTO;

    public String openEditOpinionPage(Long opinionId) {
        conversation.begin();
        this.updateOpinionDTO = modelMapper.map(opinionEndpoint.getOpinionByID(opinionId), UpdateOpinionDTO.class);
        return "client/editOpinion.xhtml?faces-redirect=true";
    }

    public String updateOpinion(){
        opinionEndpoint.updateOpinion(opinionId, updateOpinionDTO);
        conversation.end();
        return "client/rentalDetails.xhtml?faces-redirect=true";
    }

    public UpdateOpinionDTO getUpdateOpinionDTO() {
        return updateOpinionDTO;
    }

    public void setUpdateOpinionDTO(UpdateOpinionDTO updateOpinionDTO) {
        this.updateOpinionDTO = updateOpinionDTO;
    }
}
