package pl.lodz.p.it.ssbd2020.ssbd02.mok.managers;

import org.apache.commons.lang3.tuple.MutablePair;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.AccessLevel;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.User;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.UserAccessLevel;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppNotFoundException;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.facades.AccessLevelFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.facades.UserAccessLevelFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;
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
import java.util.List;

/**
 * Klasa menadżera do obsługi operacji związanych z połączeniem użytkowników z poziomami dostępu
 */
@Stateful
@LocalBean
@Interceptors(LoggerInterceptor.class)
public class UserAccessLevelManager implements Serializable {

    @Inject
    private UserAccessLevelFacade userAccessLevelFacade;

    @Inject
    private AccessLevelFacade accessLevelFacade;

    private String ADMIN_ACCESS_LEVEL;
    private String MANAGER_ACCESS_LEVEL;
    private String CLIENT_ACCESS_LEVEL;

    @PostConstruct
    public void init() {
        PropertyReader propertyReader = new PropertyReader();
        ADMIN_ACCESS_LEVEL = propertyReader.getProperty("config", "ADMIN_ACCESS_LEVEL");
        MANAGER_ACCESS_LEVEL = propertyReader.getProperty("config", "MANAGER_ACCESS_LEVEL");
        CLIENT_ACCESS_LEVEL = propertyReader.getProperty("config", "CLIENT_ACCESS_LEVEL");
    }

    /**
     * Metoda, która modyfikuje przypisane do użytkownika poziomy dostępu.
     *
     * @param user encja user.
     * @param userAccessLevelList lista zawierająca informacje o obencych oraz nowych poziomach dostępu.
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @RolesAllowed("editUserAccessLevels")
    public void editUserAccessLevel(User user, List<MutablePair<Boolean, Boolean>> userAccessLevelList) throws AppBaseException {
        List<AccessLevel> accessLevels = accessLevelFacade.findAll();

        if (userAccessLevelList.get(0).getLeft() ^ userAccessLevelList.get(0).getRight()) {
            if (userAccessLevelList.get(0).getRight()) {
                UserAccessLevel userAccessLevel = new UserAccessLevel(user, accessLevels.stream().filter(accessLevel -> accessLevel.getName().equals(ADMIN_ACCESS_LEVEL)).findAny()
                        .orElseThrow(AppNotFoundException::createAccessLevelNotFoundException));
                userAccessLevelFacade.create(userAccessLevel);
            } else {
                userAccessLevelFacade.remove(user.getUserAccessLevels().
                        stream().filter(userAccessLevel -> userAccessLevel.getAccessLevel().getName().equals(ADMIN_ACCESS_LEVEL)).findAny()
                        .orElseThrow(AppNotFoundException::createAccessLevelNotFoundException));
            }
        }

        if (userAccessLevelList.get(1).getLeft() ^ userAccessLevelList.get(1).getRight()) {
            if (userAccessLevelList.get(1).getRight()) {
                UserAccessLevel userAccessLevel = new UserAccessLevel(user, accessLevels.stream().filter(accessLevel -> accessLevel.getName().equals(MANAGER_ACCESS_LEVEL)).findAny()
                        .orElseThrow(AppNotFoundException::createAccessLevelNotFoundException));
                userAccessLevelFacade.create(userAccessLevel);
            } else {
                userAccessLevelFacade.remove(user.getUserAccessLevels().
                        stream().filter(userAccessLevel -> userAccessLevel.getAccessLevel().getName().equals(MANAGER_ACCESS_LEVEL)).findAny()
                        .orElseThrow(AppNotFoundException::createAccessLevelNotFoundException));
            }
        }

        if (userAccessLevelList.get(2).getLeft() ^ userAccessLevelList.get(2).getRight()) {
            if (userAccessLevelList.get(2).getRight()) {
                UserAccessLevel userAccessLevel = new UserAccessLevel(user, accessLevels.stream().filter(accessLevel -> accessLevel.getName().equals(CLIENT_ACCESS_LEVEL)).findAny()
                        .orElseThrow(AppNotFoundException::createAccessLevelNotFoundException));
                userAccessLevelFacade.create(userAccessLevel);
            } else {
                userAccessLevelFacade.remove(user.getUserAccessLevels().
                        stream().filter(userAccessLevel -> userAccessLevel.getAccessLevel().getName().equals(CLIENT_ACCESS_LEVEL)).findAny()
                        .orElseThrow(AppNotFoundException::createAccessLevelNotFoundException));
            }
        }

    }
}