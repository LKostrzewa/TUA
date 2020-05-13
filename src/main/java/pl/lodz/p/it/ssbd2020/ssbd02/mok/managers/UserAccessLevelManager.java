package pl.lodz.p.it.ssbd2020.ssbd02.mok.managers;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.User;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.UserAccessLevel;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.facades.UserAccessLevelFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.facades.UserFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;
import javax.annotation.security.PermitAll;
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
     * Metoda, która zwraca encję użytkownika o danym identyfikatorze.
     *
     * @param userId login użytkownika.
     * @return encja User
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem.
     */
    @RolesAllowed("findAccessLevelById")
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public User findUserAccessLevelById(Long userId) throws AppBaseException {
        //TODO poprawic na odpowiedni wyjątek
        return userFacade.find(userId).orElseThrow(() -> new AppBaseException("nie ma tego modelu"));
    }

    /**
     * Metoda, która zwraca kolekcję wszystkich poziomów dostępu przypisanych do użytkownika o danym loginie.
     *
     * @param userLogin login użytkownika.
     * @return kolekcja encji UserAccessLevel.
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem.
     */
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Collection<UserAccessLevel> findUserAccessLevelByLogin(String userLogin) throws AppBaseException {
        return userFacade.findByLogin(userLogin).getUserAccessLevels();
    }
}