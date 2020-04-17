package pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.UserAccessLevel;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.UserAccessLevelDto;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.managers.UserAccessLevelManager;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;

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
    public final static String ADMIN_ACCESS_LEVEL = "ADMINISTRATOR";
    public final static String MANAGER_ACCESS_LEVEL = "MANAGER";
    public final static String CLIENT_ACCESS_LEVEL = "CLIENT";
    @Inject
    private UserAccessLevelManager userAccessLevelManager;

    public UserAccessLevelDto findAccessLevelById(Long userId) {
        Collection<UserAccessLevel> userAccessLevels = userAccessLevelManager.findUserAccessLevelById(userId);
        UserAccessLevelDto userAccessLevelDto = new UserAccessLevelDto();
        userAccessLevelDto.setId(userId);
        for (UserAccessLevel level : userAccessLevels) {
            if (level.getAccessLevel().getName().equals(ADMIN_ACCESS_LEVEL)) userAccessLevelDto.setAdmin(true);
            if (level.getAccessLevel().getName().equals(MANAGER_ACCESS_LEVEL)) userAccessLevelDto.setManager(true);
            if (level.getAccessLevel().getName().equals(CLIENT_ACCESS_LEVEL)) userAccessLevelDto.setClient(true);
        }
        return userAccessLevelDto;
    }

    public void editAccessLevels(UserAccessLevelDto userAccessLevelDto) {
        List<Boolean> levels = new ArrayList<>();
        levels.add(userAccessLevelDto.getAdmin());
        levels.add(userAccessLevelDto.getManager());
        levels.add(userAccessLevelDto.getClient());
        userAccessLevelManager.editAccessLevels(userAccessLevelDto.getId(), levels);
    }
}
