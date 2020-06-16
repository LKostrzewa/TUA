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
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Logger;

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
    private List<Long> imageIds;
    private UploadedFile file;
    private byte[] contents;

    @Inject
    private FacesContext facesContext;

    private ResourceBundle resourceBundle;

    Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

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
     * Metoda ładująca zdjęcie z dysku a następnie dodająca zdjęcie do bazy wykorzystując metodę addImage
     * @param event zmienna. która trzyma pobrane zdjęcie i jest następnie zamieniana na bajty
     * @throws AppBaseException wyjątek aplikacyjny
     * @throws IOException wyjątek wejścia/wyjścia
     */
    public void handleFileUpload(FileUploadEvent event) throws AppBaseException, IOException {
        file = event.getFile();
        contents = file.getContent();
        addImage(contents);
        displayInit();
        String msg = resourceBundle.getString("image.successful");
        String head = resourceBundle.getString("success");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, head, msg));
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


    /**
     * Metoda
     * @param contents tablica bajtów reprezentująca zdjęcie
     * @throws AppBaseException wyjątek aplikacyjny
     * @throws IOException wyjątek wejścia/wyjścia
     */
    public void addImage(byte[] contents) throws AppBaseException, IOException {
        imageEndpoint.addImage(contents, yachtModelId);
    }

    public void deleteImage() throws AppBaseException {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Long imaId = Long.parseLong(params.get("id"));
        try {
            imageEndpoint.deleteImage(imaId);
            displayMessage();
        } catch (AppBaseException e) {
            displayError(e.getLocalizedMessage());
        }
    }

    /**
     * Metoda pomocna przy pobieraniu i wyświetlaniu zdjęcia w galerii za pomocą p:graphicImage
     * @return zmienna używana w primeface pomocna przy trzymaniu zawartosci załadowanego pliku
     * @throws AppBaseException
     */
    public StreamedContent getImage() throws IOException, AppBaseException {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            return new DefaultStreamedContent();
        } else {
            String id = context.getExternalContext().getRequestParameterMap().get("id");
            ImageDto imageDto = imageEndpoint.getImageById(Long.valueOf(id));
            return DefaultStreamedContent.builder().stream(()-> new ByteArrayInputStream(imageDto.getLob())).build();
            //return new DefaultStreamedContent(new ByteArrayInputStream(imageDto.getLob()));
        }
    }



    /**
     * Metoda inicjalizująca wyświetlanie wiadomości.
     */
    public void displayInit(){
        facesContext.getExternalContext().getFlash().setKeepMessages(true);
        resourceBundle = ResourceBundle.getBundle("resource", facesContext.getViewRoot().getLocale());
    }

    /**
     * Metoda wyświetlająca wiadomość o poprawnym wykonaniu operacji.
     */
    public void displayMessage() {
        displayInit();
        String msg = resourceBundle.getString("image.deleteInfo");
        String head = resourceBundle.getString("success");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, head, msg));
    }

    /**
     * Metoda wyświetlająca wiadomość o zaistniałym błędzie.
     *
     * @param message wiadomość do wyświetlenia
     */
    private void displayError(String message) {
        displayInit();
        String msg = resourceBundle.getString(message);
        String head = resourceBundle.getString("error");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, head, msg));
    }
}