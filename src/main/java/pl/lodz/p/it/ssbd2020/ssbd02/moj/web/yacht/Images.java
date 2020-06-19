package pl.lodz.p.it.ssbd2020.ssbd02.moj.web.yacht;

import org.omnifaces.cdi.GraphicImageBean;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.image.ImageDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints.ImageEndpoint;

import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

@GraphicImageBean
public class Images {

    @Inject
    private ImageEndpoint imageEndpoint;

    public byte[] get(long id) {
        ImageDto imageDto = new ImageDto();
        try {
            imageDto = imageEndpoint.getImageById(id);
        } catch (AppBaseException e) {
            e.printStackTrace();
        }
        return imageDto.getLob();
    }

}
