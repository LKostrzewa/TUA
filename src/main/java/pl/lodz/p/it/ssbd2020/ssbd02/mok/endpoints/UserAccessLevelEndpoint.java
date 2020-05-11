package pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints;

import org.apache.commons.lang3.tuple.Pair;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.AccessLevel;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.User;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.UserAccessLevel;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.UserAccessLevelDto;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.managers.AccessLevelManager;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.managers.UserAccessLevelManager;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.PropertyReader;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Stateful
@LocalBean
@Interceptors(LoggerInterceptor.class)
public class UserAccessLevelEndpoint implements Serializable {
    private String ADMIN_ACCESS_LEVEL;
    private String MANAGER_ACCESS_LEVEL;
    private String CLIENT_ACCESS_LEVEL;
    @Inject
    private UserAccessLevelManager userAccessLevelManager;
    @Inject
    private AccessLevelManager accessLevelManager;

    List<AccessLevel> accessLevels;

    private User user;

    @PostConstruct
    private void init() {
        PropertyReader propertyReader = new PropertyReader();
        ADMIN_ACCESS_LEVEL = propertyReader.getProperty("config", "ADMIN_ACCESS_LEVEL");
        MANAGER_ACCESS_LEVEL = propertyReader.getProperty("config", "MANAGER_ACCESS_LEVEL");
        CLIENT_ACCESS_LEVEL = propertyReader.getProperty("config", "CLIENT_ACCESS_LEVEL");
        this.accessLevels = accessLevelManager.getAllAccessLevels();
    }

    public UserAccessLevelDto findAccessLevelById(Long userId) {
        this.user = userAccessLevelManager.findUserAccessLevelById(userId);
        UserAccessLevelDto userAccessLevelDto = new UserAccessLevelDto();
        for (UserAccessLevel level : user.getUserAccessLevels()) {
            if (level.getAccessLevel().getName().equals(ADMIN_ACCESS_LEVEL)) {
                userAccessLevelDto.getAdmin().setLeft(true);
                userAccessLevelDto.getAdmin().setRight(true);
            }
            if (level.getAccessLevel().getName().equals(MANAGER_ACCESS_LEVEL)) {
                userAccessLevelDto.getManager().setLeft(true);
                userAccessLevelDto.getManager().setRight(true);
            }
            if (level.getAccessLevel().getName().equals(CLIENT_ACCESS_LEVEL)) {
                userAccessLevelDto.getClient().setLeft(true);
                userAccessLevelDto.getClient().setRight(true);
            }
        }
        return userAccessLevelDto;
    }

    public UserAccessLevelDto findAccessLevelByLogin(String userLogin) throws AppBaseException {
        Collection<UserAccessLevel> userAccessLevels = userAccessLevelManager.findUserAccessLevelByLogin(userLogin);
        UserAccessLevelDto userAccessLevelDto = new UserAccessLevelDto();
        for (UserAccessLevel level : userAccessLevels) {
            if (level.getAccessLevel().getName().equals(ADMIN_ACCESS_LEVEL))
                userAccessLevelDto.getAdmin().setLeft(true);
            if (level.getAccessLevel().getName().equals(MANAGER_ACCESS_LEVEL))
                userAccessLevelDto.getManager().setLeft(true);
            if (level.getAccessLevel().getName().equals(CLIENT_ACCESS_LEVEL))
                userAccessLevelDto.getClient().setLeft(true);
        }
        return userAccessLevelDto;
    }

    public void editAccessLevels(UserAccessLevelDto userAccessLevelDto) throws AppBaseException {
        if (userAccessLevelDto.getAdmin().getLeft() ^ userAccessLevelDto.getAdmin().getRight()) {
            if (userAccessLevelDto.getAdmin().getRight()) {
                UserAccessLevel userAccessLevel = new UserAccessLevel(this.user, accessLevels.stream().filter(accessLevel -> accessLevel.getName().equals(ADMIN_ACCESS_LEVEL)).findAny().orElse(null));
                userAccessLevelManager.addUserAccessLevel(userAccessLevel);
            } else {
                userAccessLevelManager.removeUserAccessLevel(this.user.getUserAccessLevels().
                        stream().filter(userAccessLevel -> userAccessLevel.getAccessLevel().getName().equals(ADMIN_ACCESS_LEVEL)).findAny().orElse(null));
            }
        }
        if (userAccessLevelDto.getManager().getLeft() ^ userAccessLevelDto.getManager().getRight()) {
            if (userAccessLevelDto.getManager().getRight()) {
                UserAccessLevel userAccessLevel = new UserAccessLevel(this.user, accessLevels.stream().filter(accessLevel -> accessLevel.getName().equals(MANAGER_ACCESS_LEVEL)).findAny().orElse(null));
                userAccessLevelManager.addUserAccessLevel(userAccessLevel);
            } else {
                userAccessLevelManager.removeUserAccessLevel(this.user.getUserAccessLevels().
                        stream().filter(userAccessLevel -> userAccessLevel.getAccessLevel().getName().equals(MANAGER_ACCESS_LEVEL)).findAny().orElse(null));
            }
        }
        if (userAccessLevelDto.getClient().getLeft() ^ userAccessLevelDto.getClient().getRight()) {
            if (userAccessLevelDto.getClient().getRight()) {
                UserAccessLevel userAccessLevel = new UserAccessLevel(this.user, accessLevels.stream().filter(accessLevel -> accessLevel.getName().equals(CLIENT_ACCESS_LEVEL)).findAny().orElse(null));
                userAccessLevelManager.addUserAccessLevel(userAccessLevel);
            } else {
                userAccessLevelManager.removeUserAccessLevel(this.user.getUserAccessLevels().
                        stream().filter(userAccessLevel -> userAccessLevel.getAccessLevel().getName().equals(CLIENT_ACCESS_LEVEL)).findAny().orElse(null));
            }
        }
    }
}
