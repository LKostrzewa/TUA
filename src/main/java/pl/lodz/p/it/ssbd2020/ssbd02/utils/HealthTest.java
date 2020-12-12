package pl.lodz.p.it.ssbd2020.ssbd02.utils;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.User;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.facades.UserFacade;

import javax.annotation.security.PermitAll;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import java.util.List;

@Stateless(name = "HealthTest")
@Named("HealthTest")
@LocalBean
@Interceptors(LoggerInterceptor.class)
public class HealthTest {

    @Inject
    private UserFacade userFacade;

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @PermitAll
    public boolean tua(){
        List<User> users = userFacade.findAll();
        User user = users.stream().filter(u -> u.getLogin().equals("client")).findAny().orElse(null);
        return user != null && !user.isLocked();
    }
}
