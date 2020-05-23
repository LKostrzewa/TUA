package pl.lodz.p.it.ssbd2020.ssbd02.mok.managers;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.UserAccessLevel;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.facades.UserAccessLevelFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;


import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.io.Serializable;

/**
 * Klasa menadżera do obsługi operacji związanych z połączeniem użytkowników z poziomami dostępu
 */
@Stateful
@LocalBean
@Interceptors(LoggerInterceptor.class)
public class UserAccessLevelManager implements Serializable {

    @Inject
    private UserAccessLevelFacade userAccessLevelFacade;

    /**
     * Metoda, która dodaje poziom dostępu.
     *
     * @param userAccessLevel encja poziomu dostępu.
     */
    @RolesAllowed("editUserAccessLevels")
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void addUserAccessLevel(UserAccessLevel userAccessLevel) throws AppBaseException {
        userAccessLevelFacade.create(userAccessLevel);
    }

    /**
     * Metoda, która usuwa poziom dostępu.
     *
     * @param userAccessLevel encja poziomu dostępu.
     */
    @RolesAllowed("editUserAccessLevels")
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void removeUserAccessLevel(UserAccessLevel userAccessLevel) {
        userAccessLevelFacade.remove(userAccessLevel);
    }
}