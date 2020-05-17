package pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.AccessLevel;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.User;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.UserAccessLevel;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.UserAccessLevelDto;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.UserDetailsDto;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.managers.AccessLevelManager;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.managers.UserAccessLevelManager;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.ObjectMapperUtils;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.PropertyReader;

import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.io.Serializable;
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
    /**
     * Metoda, która zwraca obiekt UserAccessLevelDto zawierający informacje o poziomach dostępu danego użytkownika
     *
     * @param userId identyfikator użytkownika.
     * @return obiekt klasy UserAccessLevelDto
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem.
     */
    @RolesAllowed("findUserAccessLevelById")
    public UserAccessLevelDto findUserAccessLevelById(Long userId) throws AppBaseException{
        user = userAccessLevelManager.findUserById(userId);
        UserAccessLevelDto userAccessLevelDto = new UserAccessLevelDto();
        userAccessLevelDto.setUserDetailsDto(ObjectMapperUtils.map(user, UserDetailsDto.class));
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

    /**
     * Metoda, która zwraca obiekt UserAccessLevelDto zawierający informacje o poziomach dostępu danego użytkownika
     *
     * @return obiekt klasy UserAccessLevelDto
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem.
     */
    @RolesAllowed("findUserAccessLevelByLogin")
    public UserAccessLevelDto findUserAccessLevelByLogin() throws AppBaseException {
        user = userAccessLevelManager.findUserByLogin();
        Collection<UserAccessLevel> userAccessLevels = user.getUserAccessLevels();
        UserAccessLevelDto userAccessLevelDto = new UserAccessLevelDto();
        userAccessLevelDto.setUserDetailsDto(ObjectMapperUtils.map(user, UserDetailsDto.class));
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

    /**
     * Metoda, która modyfikuje przypisane do użytkownika poziomy dostępu.
     *
     * @param userAccessLevelDto obiekt zawierająy informacje o obecnych i pożądanych poziomach dotępu.
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem.
     */
    @RolesAllowed("editUserAccessLevels")
    public void editUserAccessLevels(UserAccessLevelDto userAccessLevelDto) throws AppBaseException {
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
