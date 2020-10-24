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
import javax.faces.context.ExternalContext;
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


@Named
@SessionScoped
public class YachtModelDetailsPageBean implements Serializable {
    @Inject
    private @Named("YachtModelEndpoint") YachtModelEndpoint yachtModelEndpoint;
    @Inject
    private @Named("ImageEndpoint") ImageEndpoint imageEndpoint;

    private Long yachtModelId;
    private YachtModelDetailsDto yachtModelDetailsDto;
    private List<Long> imageIds;
    private UploadedFile file;

    @Inject
    private @Named("FacesContext") FacesContext facesContext;

    private ResourceBundle resourceBundle;


    /**
     * Metoda inicjalizująca komponent.
     *
     * @throws IOException wyjątek wejścia/wyjścia
     */
    public void init() throws IOException {
        try {
            this.yachtModelDetailsDto = yachtModelEndpoint.getYachtModelById(yachtModelId);
            imageIds = imageEndpoint.getAllImagesByYachtModel(yachtModelId);
        } catch (AppBaseException e) {
            displayError(e.getLocalizedMessage());
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.redirect(externalContext.getRequestContextPath() + "listYachtModels.xhtml");
        }
    }

    /**
     * Metoda ładująca zdjęcie z dysku a następnie dodająca zdjęcie do bazy wykorzystując metodę addImage
     *
     * @param event zmienna. która trzyma pobrane zdjęcie i jest następnie zamieniana na bajty
     */
    public void handleFileUpload(FileUploadEvent event) {
        file = event.getFile();
        byte[] contents = file.getContent();
        addImage(contents);

    }


    /**
     * Metoda, która dodaje wybrany obrazek do bazy danych
     *
     * @param contents tablica bajtów reprezentująca zdjęcie
     */
    public void addImage(byte[] contents) {
        try {
            imageEndpoint.addImage(contents, yachtModelId);
            displayAddImageMessage();
        } catch (AppBaseException e) {
            displayError(e.getLocalizedMessage());
        }
    }


    /**
     * Metoda, która usuwa wybrany obrazek z bazy danych
     */
    public void deleteImage() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Long imaId = Long.parseLong(params.get("id"));
        try {
            imageEndpoint.deleteImage(imaId);
            displayDeleteImageMessage();
        } catch (AppBaseException e) {
            displayError(e.getLocalizedMessage());
        }
    }

    /**
     * Metoda pomocna przy pobieraniu i wyświetlaniu zdjęcia w galerii za pomocą p:graphicImage
     * @return zmienna używana w primeface pomocna przy trzymaniu zawartosci załadowanego pliku
     */
    public StreamedContent getImage() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            return new DefaultStreamedContent();
        } else {
            try {
                String id = context.getExternalContext().getRequestParameterMap().get("id");
                ImageDto imageDto = imageEndpoint.getImageById(Long.valueOf(id));
                return DefaultStreamedContent.builder().stream(() -> new ByteArrayInputStream(imageDto.getLob())).build();
            } catch (AppBaseException e) {
                displayError(e.getLocalizedMessage());
            }
        }
        return null;
    }


    /**
     * Metoda inicjalizująca wyświetlanie wiadomości.
     */
    public void displayInit() {
        facesContext.getExternalContext().getFlash().setKeepMessages(true);
        resourceBundle = ResourceBundle.getBundle("resource", facesContext.getViewRoot().getLocale());
    }

    /**
     * Metoda wyświetlająca wiadomość o poprawnym wykonaniu operacji usunięcia zdjęcia.
     */
    public void displayDeleteImageMessage() {
        displayInit();
        String msg = resourceBundle.getString("image.deleteInfo");
        String head = resourceBundle.getString("success");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, head, msg));
    }

    /**
     * Metoda wyświetlająca wiadomość o poprawnym wykonaniu operacji dodania zdjęcia.
     */
    public void displayAddImageMessage() {
        displayInit();
        String msg = resourceBundle.getString("image.successful");
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


    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
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


    public List<Long> getImageIds() {
        return imageIds;
    }


}