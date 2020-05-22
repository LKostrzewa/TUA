package pl.lodz.p.it.ssbd2020.ssbd02.moj.facades;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.Image;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.facades.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;

import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

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


    @Override
    @RolesAllowed("addImage")
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public void create(Image entity) throws AppBaseException {
        super.create(entity);
    }

    @Override
    @RolesAllowed("deleteImage")
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public void remove(Image entity) {
        super.remove(entity);
    }


    @Override
    @RolesAllowed("deleteImage")
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public Optional<Image> find(Object id) {
        return super.find(id);
    }
}
