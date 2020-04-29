package pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.UserAccessLevel;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.UserAccessLevelDto;
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



    @PostConstruct
    private void init() {
        PropertyReader propertyReader= new PropertyReader();
        ADMIN_ACCESS_LEVEL = propertyReader.getProperty("config","ADMIN_ACCESS_LEVEL");
        MANAGER_ACCESS_LEVEL = propertyReader.getProperty("config","MANAGER_ACCESS_LEVEL");
        CLIENT_ACCESS_LEVEL = propertyReader.getProperty("config","CLIENT_ACCESS_LEVEL");
    }

    public UserAccessLevelDto findAccessLevelById(Long userId) {
        Collection<UserAccessLevel> userAccessLevels = userAccessLevelManager.findUserAccessLevelById(userId);
        UserAccessLevelDto userAccessLevelDto = new UserAccessLevelDto();
        for (UserAccessLevel level : userAccessLevels) {
            if (level.getAccessLevel().getName().equals(ADMIN_ACCESS_LEVEL)) userAccessLevelDto.setAdmin(true);
            if (level.getAccessLevel().getName().equals(MANAGER_ACCESS_LEVEL)) userAccessLevelDto.setManager(true);
            if (level.getAccessLevel().getName().equals(CLIENT_ACCESS_LEVEL)) userAccessLevelDto.setClient(true);
        }
        return userAccessLevelDto;
    }

    public UserAccessLevelDto findAccessLevelByLogin(String userLogin) {
        Collection<UserAccessLevel> userAccessLevels = userAccessLevelManager.findUserAccessLevelByLogin(userLogin);
        UserAccessLevelDto userAccessLevelDto = new UserAccessLevelDto();
        for (UserAccessLevel level : userAccessLevels) {
            if (level.getAccessLevel().getName().equals(ADMIN_ACCESS_LEVEL)) userAccessLevelDto.setAdmin(true);
            if (level.getAccessLevel().getName().equals(MANAGER_ACCESS_LEVEL)) userAccessLevelDto.setManager(true);
            if (level.getAccessLevel().getName().equals(CLIENT_ACCESS_LEVEL)) userAccessLevelDto.setClient(true);
        }
        return userAccessLevelDto;
    }

    public void editAccessLevels(UserAccessLevelDto userAccessLevelDto,Long userId) {
        List<Boolean> levels = new ArrayList<>();
        levels.add(userAccessLevelDto.getAdmin());
        levels.add(userAccessLevelDto.getManager());
        levels.add(userAccessLevelDto.getClient());
        userAccessLevelManager.editAccessLevels(userId, levels);
    }
}
