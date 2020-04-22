package pl.lodz.p.it.ssbd2020.ssbd02.moj.facades;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.YachtModel;
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
}
