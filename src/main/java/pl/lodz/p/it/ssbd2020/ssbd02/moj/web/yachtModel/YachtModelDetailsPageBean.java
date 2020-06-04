package pl.lodz.p.it.ssbd2020.ssbd02.moj.web.yachtModel;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.image.ImageDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yachtModel.YachtModelDetailsDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints.ImageEndpoint;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints.YachtModelEndpoint;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class YachtModelDetailsPageBean implements Serializable {
    @Inject
    private YachtModelEndpoint yachtModelEndpoint;
    @Inject
    private ImageEndpoint imageEndpoint;
    private Long yachtModelId;
    private Long imageId;
    private YachtModelDetailsDto yachtModelDetailsDto;
    private ImageDto imageDto;
    private List<Long> imageIds;
    private UploadedFile file;
    private byte[] contents;

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public byte[] getContents() {
        return contents;
    }

    public void setContents(byte[] contents) {
        this.contents = contents;
    }

    /**
     * Nk to uzupełni, bo ja nie wiem XD
     */
    public void handleFileUpload(FileUploadEvent event) throws AppBaseException, IOException {
        file = event.getFile();
        contents = file.getContent();
        addImage(contents);
        FacesMessage msg = new FacesMessage("Successful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    /**
     * Metoda inicjalizująca komponent.
     */
    public void init() throws AppBaseException {
        this.yachtModelDetailsDto = yachtModelEndpoint.getYachtModelById(yachtModelId);
        imageIds = imageEndpoint.getAllImagesByYachtModel(yachtModelId);
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

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public List<Long> getImageIds() {
        return imageIds;
    }

    public void addImage(byte[] contents) throws AppBaseException, IOException {
        imageEndpoint.addImage(contents, yachtModelId);
    }

    public void deleteImage() throws AppBaseException {
        imageEndpoint.deleteImage(imageId);
    }

    public StreamedContent getImage() throws IOException, AppBaseException {
        FacesContext context = FacesContext.getCurrentInstance();

        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            return new DefaultStreamedContent();
        } else {
            String id = context.getExternalContext().getRequestParameterMap().get("id");
            ImageDto imageDto = imageEndpoint.getImageById(Long.valueOf(id));
            return new DefaultStreamedContent(new ByteArrayInputStream(imageDto.getLob()));
        }
    }
}
