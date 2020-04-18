package pl.lodz.p.it.ssbd2020.ssbd02.moj.facade;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.Image;
import pl.lodz.p.it.ssbd2020.ssbd02.facades.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
@Interceptors(LoggerInterceptor.class)
public class ImageFacade extends AbstractFacade<Image> {
    @PersistenceContext(unitName = "ssbd02mojPU")
    private EntityManager entityManager;

    public ImageFacade() {
        super(Image.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }
}
