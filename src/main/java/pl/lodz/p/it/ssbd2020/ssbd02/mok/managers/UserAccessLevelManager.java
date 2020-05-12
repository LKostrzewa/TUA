package pl.lodz.p.it.ssbd2020.ssbd02.mok.managers;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.User;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.UserAccessLevel;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.exceptions.UserNotFoundException;
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

    public void addUserAccessLevel(UserAccessLevel userAccessLevel) {
        userAccessLevelFacade.create(userAccessLevel);
    }

    public void removeUserAccessLevel(UserAccessLevel userAccessLevel) {
        userAccessLevelFacade.remove(userAccessLevel);
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

    public User findUserAccessLevelById(Long userId) throws AppBaseException {
        //TODO poprawic na odpowiedni wyjątek
        return userFacade.find(userId).orElseThrow(() -> new AppBaseException("nie ma tego modelu"));
    }

    public Collection<UserAccessLevel> findUserAccessLevelByLogin(String userLogin) throws AppBaseException {
        return userFacade.findByLogin(userLogin).getUserAccessLevels();
    }
}