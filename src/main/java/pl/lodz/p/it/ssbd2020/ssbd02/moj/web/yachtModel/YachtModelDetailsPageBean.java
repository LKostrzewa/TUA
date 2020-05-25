package pl.lodz.p.it.ssbd2020.ssbd02.moj.web.yachtModel;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yachtModel.YachtModelDetailsDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints.ImageEndpoint;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints.YachtModelEndpoint;

import javax.ejb.ApplicationException;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Named
@ApplicationScoped
public class YachtModelDetailsPageBean implements Serializable {


    @Inject
    private YachtModelEndpoint yachtModelEndpoint;
    @Inject
    private ImageEndpoint imageEndpoint;
    private Long yachtModelId;
    private Long imageId;
    private YachtModelDetailsDto yachtModelDetailsDto;

    private List<byte[]> uploadedImages;
    private UploadedFile uploadedFile;
    private byte[] fileContents;

    private List<Long> imageIds;

    public void init() throws AppBaseException {
        this.yachtModelDetailsDto = yachtModelEndpoint.getYachtModelById(yachtModelId);
        //uploadedImages = new ArrayList<byte[]>();
        imageIds = imageEndpoint.getImagesbyYachtModel(yachtModelId);
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
    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public byte[] getFileContents() {
        return fileContents;
    }

    public void setFileContents(byte[] fileContents) {
        this.fileContents = fileContents;
    }

    public List<byte[]> getUploadedImages() {
        return uploadedImages;
    }

    public void setUploadedImages(List<byte[]> uploadedImages) {
        this.uploadedImages = uploadedImages;
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

    public void addImage() throws AppBaseException, IOException {
        fileContents = uploadedFile.getContent();
        uploadedImages.add(fileContents);
        imageEndpoint.addImage(fileContents, yachtModelId);
    }

    public void deleteImage() throws AppBaseException {
        imageEndpoint.deleteImage(imageId);
    }

    public StreamedContent getImage() {
        if (uploadedFile == null) {
            return new DefaultStreamedContent();
        } else {
            return new DefaultStreamedContent(new ByteArrayInputStream(uploadedFile.getContent()), "image/png");
        }
    }
    public String getImageContentsAsBase64() {
        return Base64.getEncoder().encodeToString(fileContents);
    }


}
