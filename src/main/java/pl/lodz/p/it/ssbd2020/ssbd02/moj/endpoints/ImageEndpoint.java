package pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.Image;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;

import java.io.IOException;
import java.util.List;

public interface ImageEndpoint {
    void addImage(String path) throws IOException, AppBaseException;
    void deleteImage(Long imageId) throws AppBaseException;
    List<Image> getAllImagesByYachtMode(String model);
    Image getImageById(Long imageId) throws AppBaseException;
}
