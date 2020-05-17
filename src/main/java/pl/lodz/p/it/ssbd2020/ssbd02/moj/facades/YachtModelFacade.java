package pl.lodz.p.it.ssbd2020.ssbd02.moj.facades;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.YachtModel;
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
import java.util.List;
import java.util.Optional;

@Stateless
@LocalBean
@Interceptors(LoggerInterceptor.class)
public class YachtModelFacade extends AbstractFacade<YachtModel> {
    @PersistenceContext(unitName = "ssbd02mojPU")
    private EntityManager entityManager;

    public YachtModelFacade() {
        super(YachtModel.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public void create(YachtModel entity) throws AppBaseException {
        super.create(entity);
    }

    @Override
    public void edit(YachtModel entity) throws AppBaseException {
        super.edit(entity);
    }

    @Override
    public void remove(YachtModel entity) {
        super.remove(entity);
    }

    @Override
    @RolesAllowed("addYacht")
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public Optional<YachtModel> find(Object id) {
        return super.find(id);
    }

    @Override
    public List<YachtModel> findAll() {
        return super.findAll();
    }
}
