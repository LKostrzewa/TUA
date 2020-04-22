package pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints;

import org.apache.commons.io.IOUtils;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.Image;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.managers.ImageManager;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.io.*;
import java.util.List;
import java.util.logging.Logger;

@Stateful
@LocalBean
@Interceptors(LoggerInterceptor.class)
public class ImageEndpoint implements Serializable {
    private final Logger LOGGER = Logger.getLogger(getClass().getName());
    @Inject
    private ImageManager imageManager;

    private static byte[] toByteArray(InputStream in) throws IOException {
        return IOUtils.toByteArray(in);
    }

    public void addImage(String path) throws IOException {
        InputStream inputStream = null;
        byte[] bytes = null;
        try {
            inputStream = new FileInputStream(new File(path));
            bytes = toByteArray(inputStream);
        } catch (FileNotFoundException ex) {
            LOGGER.info("Cannot read a File with path" + path);
        }
        //  tutaj powinien gdzies byc jacht model, zakladam ze bedzie w przyszlosci
        Image image = new Image(bytes, null);

        imageManager.addImage(image);
    }

    public void deleteImage(Long imageId) {
        imageManager.deleteImage(imageId);
    }

    public List<Image> getAllImagesByYachtMode(String model) {
        return imageManager.getAllImagesByYachtModel(model);
    }

    public Image getImageById(Long imageId) {
        return imageManager.getImageById(imageId);
    }
}
