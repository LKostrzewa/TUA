package pl.lodz.p.it.ssbd2020.ssbd02.mok.managers;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.User;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.UserAccessLevel;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppNotFoundException;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.facades.AccessLevelFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.facades.UserAccessLevelFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.facades.UserFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.PropertyReader;

import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.io.Serializable;
import java.util.Collection;


@Stateful
@LocalBean
@Interceptors(LoggerInterceptor.class)
public class UserAccessLevelManager implements Serializable {

    @Inject
    private UserAccessLevelFacade userAccessLevelFacade;
    @Inject
    private UserFacade userFacade;

    /**
     * Metoda, która dodaje poziom dostępu.
     *
     * @param userAccessLevel encja poziomu dostępu.
     */
    @RolesAllowed("editAccessLevels")
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void addUserAccessLevel(UserAccessLevel userAccessLevel) {
        userAccessLevelFacade.create(userAccessLevel);
    }

    /**
     * Metoda, która usuwa poziom dostępu.
     *
     * @param userAccessLevel encja poziomu dostępu.
     */
    @RolesAllowed("editAccessLevels")
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void removeUserAccessLevel(UserAccessLevel userAccessLevel) {
        userAccessLevelFacade.remove(userAccessLevel);
    }


    /**
     * Metoda zwracająca encje user o podanym identyfikatorze
     *
     * @param userId identyfikator użytkownika
     * @return Encja user o podanym identyfikatorze
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("findUserAccessLevelById")
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public User findUserById(Long userId) throws AppBaseException {
        return userFacade.find(userId).orElseThrow(AppNotFoundException::createUserNotFoundException);
    }

    /**
     * Metoda zwracająca encje user o wskazanym loginie
     *
     * @param userLogin login użytkownika
     * @return Encja usera o podanym loginie
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("findUserAccessLevelByLogin")
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public User findUserByLogin(String userLogin) throws AppBaseException {
        return userFacade.findByLogin(userLogin);
    }
}