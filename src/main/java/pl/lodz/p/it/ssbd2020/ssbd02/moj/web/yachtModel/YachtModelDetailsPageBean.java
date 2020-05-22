package pl.lodz.p.it.ssbd2020.ssbd02.moj.web.yachtModel;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.Image;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yachtModel.YachtModelDetailsDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints.ImageEndpoint;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints.YachtModelEndpoint;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.ObjectMapperUtils;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class YachtModelDetailsPageBean implements Serializable {


    @Inject
    private YachtModelEndpoint yachtModelEndpoint;
    private Long yachtModelId;
    private YachtModelDetailsDto yachtModelDetailsDto;


    public void init() throws AppBaseException {
        this.yachtModelDetailsDto = yachtModelEndpoint.getYachtModelById(yachtModelId);
    }

    public Long getYachtModelId() {
        return yachtModelId;
    }

    public void setYachtModelId(Long yachtModelId) {
        this.yachtModelId = yachtModelId;
    }

    public YachtModelDetailsDto getYachtModelDetailsDto() {
        return yachtModelDetailsDto;
    }

    public void setYachtModelDetailsDto(YachtModelDetailsDto yachtModelDetailsDto) {
        this.yachtModelDetailsDto = yachtModelDetailsDto;
    }
}
