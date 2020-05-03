package pl.lodz.p.it.ssbd2020.ssbd02.mok.managers;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.UserAccessLevel;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.facades.AccessLevelFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.facades.UserAccessLevelFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.facades.UserFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.PropertyReader;

import javax.annotation.PostConstruct;
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
    private String ADMIN_ACCESS_LEVEL;
    private String MANAGER_ACCESS_LEVEL;
    private String CLIENT_ACCESS_LEVEL;
    @Inject
    private UserAccessLevelFacade userAccessLevelFacade;
    @Inject
    private AccessLevelFacade accessLevelFacade;
    @Inject
    private UserFacade userFacade;

    @PostConstruct
    private void init() {
        PropertyReader propertyReader = new PropertyReader();
        ADMIN_ACCESS_LEVEL = propertyReader.getProperty("config", "ADMIN_ACCESS_LEVEL");
        MANAGER_ACCESS_LEVEL = propertyReader.getProperty("config", "MANAGER_ACCESS_LEVEL");
        CLIENT_ACCESS_LEVEL = propertyReader.getProperty("config", "CLIENT_ACCESS_LEVEL");
    }

    public void editAccessLevels(Long id, List<Boolean> levels) {
        Collection<UserAccessLevel> accessLevelsForUser = findUserAccessLevelById(id);

        if (levels.get(0).equals(true) && !isInRole(accessLevelsForUser, ADMIN_ACCESS_LEVEL)) {
            UserAccessLevel userAccessLevel = new UserAccessLevel(userFacade.find(id), accessLevelFacade.findByAccessLevelName(ADMIN_ACCESS_LEVEL));
            userAccessLevelFacade.create(userAccessLevel);
        }
        if (levels.get(0).equals(false) && isInRole(accessLevelsForUser, ADMIN_ACCESS_LEVEL)) {
            userAccessLevelFacade.remove(findLevelByName(accessLevelsForUser, ADMIN_ACCESS_LEVEL));
        }
        if (levels.get(1).equals(true) && !isInRole(accessLevelsForUser, MANAGER_ACCESS_LEVEL)) {
            UserAccessLevel userAccessLevel = new UserAccessLevel(userFacade.find(id), accessLevelFacade.findByAccessLevelName(MANAGER_ACCESS_LEVEL));
            userAccessLevelFacade.create(userAccessLevel);
        }
        if (levels.get(1).equals(false) && isInRole(accessLevelsForUser, MANAGER_ACCESS_LEVEL)) {
            userAccessLevelFacade.remove(findLevelByName(accessLevelsForUser, MANAGER_ACCESS_LEVEL));
        }
        if (levels.get(2).equals(true) && !isInRole(accessLevelsForUser, CLIENT_ACCESS_LEVEL)) {
            UserAccessLevel userAccessLevel = new UserAccessLevel( userFacade.find(id), accessLevelFacade.findByAccessLevelName(CLIENT_ACCESS_LEVEL));
            userAccessLevelFacade.create(userAccessLevel);
        }
        if (levels.get(2).equals(false) && isInRole(accessLevelsForUser, CLIENT_ACCESS_LEVEL)) {
            userAccessLevelFacade.remove(findLevelByName(accessLevelsForUser, CLIENT_ACCESS_LEVEL));
        }

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

    public Collection<UserAccessLevel> findUserAccessLevelByLogin(String userLogin) {
        return userFacade.findByLogin(userLogin).getUserAccessLevels();
    }
}