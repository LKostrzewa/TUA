package pl.lodz.p.it.ssbd2020.ssbd02.mok.managers;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.AccessLevel;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.facades.AccessLevelFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.List;

@Stateful
@LocalBean
@Interceptors(LoggerInterceptor.class)
public class AccessLevelManager {
    @Inject
    private AccessLevelFacade accessLevelFacade;
    /**
     * Metoda, która zwraca wszystkie poziomy dostępu.
     *
     * @return Lista encji AccessLevel
     */
    @RolesAllowed("editUserAccessLevels")
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List<AccessLevel> getAllAccessLevels(){
        return accessLevelFacade.findAll();
    }
}
