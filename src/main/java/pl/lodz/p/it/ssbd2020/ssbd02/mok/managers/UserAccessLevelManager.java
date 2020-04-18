package pl.lodz.p.it.ssbd2020.ssbd02.mok.managers;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.UserAccessLevel;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.facades.AccessLevelFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.facades.UserAccessLevelFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.facades.UserFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Stateful
@LocalBean
@Interceptors(LoggerInterceptor.class)
public class UserAccessLevelManager implements Serializable {
    public final static String ADMIN_ACCESS_LEVEL = "ADMINISTRATOR";
    public final static String MANAGER_ACCESS_LEVEL = "MANAGER";
    public final static String CLIENT_ACCESS_LEVEL = "CLIENT";
    @Inject
    private UserAccessLevelFacade userAccessLevelFacade;
    @Inject
    private AccessLevelFacade accessLevelFacade;
    @Inject
    private UserFacade userFacade;

    public void editAccessLevels(Long id, List<Boolean> levels) {
        Collection<UserAccessLevel> accessLevelsForUser = findUserAccessLevelById(id);

        if (levels.get(0).equals(true) && !isInRole(accessLevelsForUser, ADMIN_ACCESS_LEVEL)) {
            UserAccessLevel userAccessLevel = new UserAccessLevel();
            setUpAccessLevel(userAccessLevel, id);
            userAccessLevel.setAccessLevel(accessLevelFacade.findByAccessLevelName(ADMIN_ACCESS_LEVEL));
            userAccessLevelFacade.create(userAccessLevel);
        }
        if (levels.get(0).equals(false) && isInRole(accessLevelsForUser, ADMIN_ACCESS_LEVEL)) {
            userAccessLevelFacade.remove(findLevelByName(accessLevelsForUser, ADMIN_ACCESS_LEVEL));
        }
        if (levels.get(1).equals(true) && !isInRole(accessLevelsForUser, MANAGER_ACCESS_LEVEL)) {
            UserAccessLevel userAccessLevel = new UserAccessLevel();
            setUpAccessLevel(userAccessLevel, id);
            userAccessLevel.setAccessLevel(accessLevelFacade.findByAccessLevelName(MANAGER_ACCESS_LEVEL));
            userAccessLevelFacade.create(userAccessLevel);
        }
        if (levels.get(1).equals(false) && isInRole(accessLevelsForUser, MANAGER_ACCESS_LEVEL)) {
            userAccessLevelFacade.remove(findLevelByName(accessLevelsForUser, MANAGER_ACCESS_LEVEL));
        }
        if (levels.get(2).equals(true) && !isInRole(accessLevelsForUser, CLIENT_ACCESS_LEVEL)) {
            UserAccessLevel userAccessLevel = new UserAccessLevel();
            setUpAccessLevel(userAccessLevel, id);
            userAccessLevel.setAccessLevel(accessLevelFacade.findByAccessLevelName(CLIENT_ACCESS_LEVEL));
            userAccessLevelFacade.create(userAccessLevel);
        }
        if (levels.get(2).equals(false) && isInRole(accessLevelsForUser, CLIENT_ACCESS_LEVEL)) {
            userAccessLevelFacade.remove(findLevelByName(accessLevelsForUser, CLIENT_ACCESS_LEVEL));
        }

    }

    public void setUpAccessLevel(UserAccessLevel userAccessLevel, Long id) {
        //userAccessLevel.setId(id);
        //userAccessLevel.setVersion(1);
        userAccessLevel.setBusinessKey(UUID.randomUUID());
        userAccessLevel.setUser(userFacade.find(id));
    }

    private Boolean isInRole(Collection<UserAccessLevel> accessLevelsForUser, String role) {
        for (UserAccessLevel level : accessLevelsForUser) {
            if (level.getAccessLevel().getName().equals(role)) {
                return true;
            }
        }
        return false;
    }

    private UserAccessLevel findLevelByName(Collection<UserAccessLevel> accessLevelsForUser, String role) {
        for (UserAccessLevel level : accessLevelsForUser) {
            if (level.getAccessLevel().getName().equals(role)) {
                return level;
            }
        }
        return null;
    }

    public Collection<UserAccessLevel> findUserAccessLevelById(Long userId) {
        return userFacade.find(userId).getUserAccessLevels();
    }
}