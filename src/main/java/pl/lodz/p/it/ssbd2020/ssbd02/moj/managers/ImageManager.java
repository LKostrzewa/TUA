package pl.lodz.p.it.ssbd2020.ssbd02.moj.managers;


import pl.lodz.p.it.ssbd2020.ssbd02.entities.Image;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.Opinion;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.Rental;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.facade.ImageFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.facade.YachtModelFacade;
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

    public List<Image> getAllImagesByYachtModel(String modelName){
        return imageFacade.findAll().stream()
                .filter(image -> image.getYachtModel().getModel().equals(modelName))
                .collect(Collectors.toList());
    }

    public Image getImageById(Long imageId) {
        return imageFacade.find(imageId);
    }

    public void deleteImage(Long imageId) {
        imageFacade.remove(imageFacade.find(imageId));
    }

    public void addImage(Image image) {
        imageFacade.create(image);
    }
}
