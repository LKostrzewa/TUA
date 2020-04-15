package pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints;


import pl.lodz.p.it.ssbd2020.ssbd02.entities.AccessLevel;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.User;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.UserAccessLevel;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.*;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.managers.UserManager;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.ObjectMapperUtils;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

@Stateful
@LocalBean
@Interceptors(LoggerInterceptor.class)
public class UserEndpoint implements Serializable {
    public final static String ADMIN_ACCESS_LEVEL = "ADMINISTRATOR";
    public final static String MANAGER_ACCESS_LEVEL = "MANAGER";
    public final static String CLIENT_ACCESS_LEVEL = "CLIENT";

    @Inject
    private UserManager userManager;

    public void registerNewUser(UserDTO userDTO) {
        User user = ObjectMapperUtils.map(userDTO, User.class);
        userManager.registerNewUser(user);

    }

    public List<User> getAll() {
        return userManager.getAll();
    }

    public User find(Long id) {
        return userManager.find(id);
    }

    public void editUser(EditUserDTO editUserDTO) {
        User user = ObjectMapperUtils.map(editUserDTO, User.class);
        userManager.editUser(user);
    }

    public void editPassword(ChangePasswordDTO changePasswordDTO) {
        User user = ObjectMapperUtils.map(changePasswordDTO, User.class);
        userManager.editPassword(user);
    }

    public UserAccessLevelDTO findAccessLevelByUserID(long id){
        Collection<UserAccessLevel> userAccessLevels = userManager.findAccessLevelByUserID(id);
        UserAccessLevelDTO userAccessLevelDTO = new UserAccessLevelDTO();
        userAccessLevelDTO.setId(id);
        for (UserAccessLevel level : userAccessLevels) {
            if(level.getAccessLevel().getName().equals(ADMIN_ACCESS_LEVEL)) userAccessLevelDTO.setAdmin(true);
            if(level.getAccessLevel().getName().equals(MANAGER_ACCESS_LEVEL)) userAccessLevelDTO.setManager(true);
            if(level.getAccessLevel().getName().equals(CLIENT_ACCESS_LEVEL)) userAccessLevelDTO.setClient(true);

        }
        return userAccessLevelDTO;
    }

    public void editAccessLevels(UserAccessLevelDTO userAccessLevelDTO) {
        List<Boolean> levels = new ArrayList<>();
        levels.add(userAccessLevelDTO.getAdmin());
        levels.add(userAccessLevelDTO.getManager());
        levels.add(userAccessLevelDTO.getClient());
        userManager.editAccessLevels(userAccessLevelDTO.getId(),levels);
    }

}
