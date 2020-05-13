package pl.lodz.p.it.ssbd2020.ssbd02.moj.managers;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.Image;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.facades.ImageFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.facades.YachtModelFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.List;
import java.util.stream.Collectors;

@Stateful
@LocalBean
@Interceptors(LoggerInterceptor.class)
public class ImageManager {
    @Inject
    private ImageFacade imageFacade;
    @Inject
    private YachtModelFacade yachtModelFacade;

    public List<Image> getAllImagesByYachtModel(String modelName) {
        return imageFacade.findAll().stream()
                .filter(image -> image.getYachtModel().getModel().equals(modelName))
                .collect(Collectors.toList());
    }

    public Image getImageById(Long imageId) throws AppBaseException {
        //TODO poprawic na odpowiedni wyjątek
        return imageFacade.find(imageId).orElseThrow(() -> new AppBaseException("nie ma tego obrazka"));
    }

    public void deleteImage(Long imageId) throws AppBaseException {
        //TODO poprawic na odpowiedni wyjątek
        imageFacade.remove(imageFacade.find(imageId).orElseThrow(() -> new AppBaseException("nie ma tego jachtu")));
    }

    public void addImage(Image image) {
        imageFacade.create(image);
    }
}
