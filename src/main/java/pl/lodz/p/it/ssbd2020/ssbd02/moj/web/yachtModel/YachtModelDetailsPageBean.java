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
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@Named
@ConversationScoped
public class YachtModelDetailsPageBean implements Serializable {
    @Inject
    private Conversation conversation;
    @Inject
    private ImageEndpoint imageEndpoint;
    @Inject
    private YachtModelEndpoint yachtModelEndpoint;
    private Long yachtModelId;
    private YachtModelDetailsDto yachtModelDetailsDto;
    private List<Image> images;

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    @PostConstruct
    private void init(String modelName) {

        this.images = imageEndpoint.getAllImagesByYachtMode(modelName);
    }

    public String openYachtModelDetailsPage(Long yachtModelId) throws AppBaseException {
        conversation.begin();
        this.yachtModelId = yachtModelId;
        this.yachtModelDetailsDto = ObjectMapperUtils.map(yachtModelEndpoint.getYachtModelById(yachtModelId), YachtModelDetailsDto.class);
        return "/manager/editYachtModel.xhtml?faces-redirect=true";
    }

    public String closeYachtModelDetailsPage() {
        conversation.end();
        return "/manager/listYachtModels.xhtml?faces-redirect=true";
    }

    public String addImage(String path) throws IOException {
        imageEndpoint.addImage(path);
        return "/manager/yachtModelDetails.xhtml?faces-redirect=true";
    }

    public String deleteImage(Long id) throws AppBaseException{
        imageEndpoint.deleteImage(id);
        return "/manager/yachtModelDetails.xhtml?faces-redirect=true";
    }
}
