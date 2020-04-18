package pl.lodz.p.it.ssbd2020.ssbd02.moj.facade;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.RentalStatus;
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
public class RentalStatusFacade extends AbstractFacade<RentalStatus> {
    @PersistenceContext(unitName = "ssbd02mojPU")
    private EntityManager entityManager;

    public RentalStatusFacade() {
        super(RentalStatus.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }
}
