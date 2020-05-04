package pl.lodz.p.it.ssbd2020.ssbd02.moj.web.opinion;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.opinion.EditOpinionDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints.OpinionEndpoint;

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
    private Long opinionId;
    private EditOpinionDto editOpinionDTO;

    public EditOpinionDto getEditOpinionDTO() {
        return editOpinionDTO;
    }

    public void setEditOpinionDTO(EditOpinionDto editOpinionDTO) {
        this.editOpinionDTO = editOpinionDTO;
    }

    public String openEditOpinionPage(Long opinionId) {
        conversation.begin();
        this.editOpinionDTO = opinionEndpoint.getOpinionById(opinionId);
        return "client/editOpinion.xhtml?faces-redirect=true";
    }

    public String editOpinion() throws AppBaseException {
        opinionEndpoint.editOpinion(opinionId, editOpinionDTO);
        conversation.end();
        return "client/rentalDetails.xhtml?faces-redirect=true";
    }
}
