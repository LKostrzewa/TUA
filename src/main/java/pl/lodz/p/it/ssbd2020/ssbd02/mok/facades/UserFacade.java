package pl.lodz.p.it.ssbd2020.ssbd02.mok.facades;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.User;
import pl.lodz.p.it.ssbd2020.ssbd02.facades.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;

import javax.annotation.security.PermitAll;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Klasa fasadowa powiązana z encją User
 */
@Stateless
@LocalBean
@Interceptors(LoggerInterceptor.class)
public class UserFacade extends AbstractFacade<User> {
    @PersistenceContext(unitName = "ssbd02mokPU")
    private EntityManager entityManager;

    public UserFacade() {
        super(User.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public List<User> findAll() {
        return super.findAll();
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public User find(Object id) {
        return super.find(id);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public void edit(User user) {
        super.edit(user);
    }

    public User findByLogin(String userLogin){
        return getEntityManager().createNamedQuery("User.findByLogin", User.class)
                .setParameter("login",userLogin).getSingleResult();
    }

    @PermitAll
    public User findByActivationCode(String activationCode) {
        TypedQuery<User> typedQuery = entityManager.createNamedQuery("User.findByActivationCode", User.class);
        typedQuery.setParameter("activationCode", activationCode);
        return typedQuery.getSingleResult();
    }
}
