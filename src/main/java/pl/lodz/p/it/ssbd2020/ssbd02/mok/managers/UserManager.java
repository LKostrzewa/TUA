package pl.lodz.p.it.ssbd2020.ssbd02.mok.managers;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.AccessLevel;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.User;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.UserAccessLevel;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.facades.AccessLevelFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.facades.UserAccessLevelFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.facades.UserFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.BCryptPasswordHash;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.*;

@Stateful
@LocalBean
@Interceptors(LoggerInterceptor.class)
public class UserManager {
    public final static String ADMIN_ACCESS_LEVEL = "ADMINISTRATOR";
    public final static String MANAGER_ACCESS_LEVEL = "MANAGER";
    public final static String CLIENT_ACCESS_LEVEL = "CLIENT";

    @Inject
    private UserAccessLevelFacade userAccessLevelFacade;

    @Inject
    private AccessLevelFacade accessLevelFacade;

    @Inject
    private UserFacade userFacade;

    @Inject
    private BCryptPasswordHash bCryptPasswordHash;

    public void registerNewUser(User user) {
        String passwordHash = bCryptPasswordHash.generate(user.getPassword().toCharArray());

        user.setVersion(1);
        user.setVersion_user_details(1);
        user.setBusinessKey_user_details(UUID.randomUUID());
        user.setBusinessKey(UUID.randomUUID());
        user.setActivated(false);
        user.setLocked(false);
        user.setCreated(new Date());
        user.setInvalidLoginAttempts(0);
        user.setPassword(passwordHash);
        user.setActivationCode(UUID.randomUUID().toString());
        user.setResetPasswordCode(UUID.randomUUID());

        UserAccessLevel userAccessLevel = new UserAccessLevel();
        userAccessLevel.setVersion(1);
        userAccessLevel.setBusinessKey(UUID.randomUUID());
        userAccessLevel.setAccessLevel(accessLevelFacade.findByAccessLevelName(CLIENT_ACCESS_LEVEL));
        userAccessLevel.setUser(user);


        List<UserAccessLevel> userAccessLevels = List.of(userAccessLevel);
        user.setUserAccessLevels(userAccessLevels);

        userFacade.create(user);

        //userAccessLevelFacade.create(userAccessLevel);
    }

    public List<User> getAll() {
        return userFacade.findAll();
    }

    public User find(Long id) {
        return userFacade.find(id);
    }

    public void editUser(User user) {
        User userFromRepository = userFacade.find(user.getId());
        userFromRepository.setEmail(user.getEmail());
        userFromRepository.setFirstName(user.getFirstName());
        userFromRepository.setLastName(user.getLastName());
        userFromRepository.setPhoneNumber(user.getPhoneNumber());
        userFacade.edit(userFromRepository);
    }

    public void editPassword(User user) {
        User userFromRepository = userFacade.find(user.getId());
        BCryptPasswordHash bCryptPasswordHash = new BCryptPasswordHash();
        String passwordHash = bCryptPasswordHash.generate(user.getPassword().toCharArray());
        userFromRepository.setPassword(passwordHash);
        userFacade.edit(userFromRepository);
    }

    public Collection<UserAccessLevel> findAccessLevelByUserID(long id){
        Collection<UserAccessLevel> accessLevelsForUser = new ArrayList<>();
        for (UserAccessLevel userAccessLevel: userAccessLevelFacade.findAll()) {
            if(userAccessLevel.getUser().getId().equals(id)) accessLevelsForUser.add(userAccessLevel);
        }
        return accessLevelsForUser;
    }

    private UserAccessLevel accessLevelToRemove;

    public void editAccessLevels(long id, List<Boolean> levels){
        Collection<UserAccessLevel> accessLevelsForUser = findAccessLevelByUserID(id);

        if(levels.get(0).equals(true) && !isInRole(accessLevelsForUser,ADMIN_ACCESS_LEVEL)){
            UserAccessLevel userAccessLevel = new UserAccessLevel();
            setUpAccessLevel(userAccessLevel,id);
            userAccessLevel.setAccessLevel(accessLevelFacade.findByAccessLevelName(ADMIN_ACCESS_LEVEL));
            userAccessLevelFacade.create(userAccessLevel);
        }
        if(levels.get(0).equals(false) && isInRole(accessLevelsForUser,ADMIN_ACCESS_LEVEL)){
            userAccessLevelFacade.remove(findLevelByName(accessLevelsForUser,ADMIN_ACCESS_LEVEL));
        }
        if(levels.get(1).equals(true) && !isInRole(accessLevelsForUser,MANAGER_ACCESS_LEVEL)){
            UserAccessLevel userAccessLevel = new UserAccessLevel();
            setUpAccessLevel(userAccessLevel,id);
            userAccessLevel.setAccessLevel(accessLevelFacade.findByAccessLevelName(MANAGER_ACCESS_LEVEL));
            userAccessLevelFacade.create(userAccessLevel);
        }
        if(levels.get(1).equals(false) && isInRole(accessLevelsForUser,MANAGER_ACCESS_LEVEL)){
            userAccessLevelFacade.remove(findLevelByName(accessLevelsForUser,MANAGER_ACCESS_LEVEL));
        }
        if(levels.get(2).equals(true) && !isInRole(accessLevelsForUser,CLIENT_ACCESS_LEVEL)){
            UserAccessLevel userAccessLevel = new UserAccessLevel();
            setUpAccessLevel(userAccessLevel,id);
            userAccessLevel.setAccessLevel(accessLevelFacade.findByAccessLevelName(CLIENT_ACCESS_LEVEL));
            userAccessLevelFacade.create(userAccessLevel);
        }
        if(levels.get(2).equals(false) && isInRole(accessLevelsForUser,CLIENT_ACCESS_LEVEL)){
            userAccessLevelFacade.remove(findLevelByName(accessLevelsForUser,CLIENT_ACCESS_LEVEL));
        }

    }
    public void setUpAccessLevel(UserAccessLevel userAccessLevel,long id){
        userAccessLevel.setId(id);
        userAccessLevel.setVersion(1);
        userAccessLevel.setBusinessKey(UUID.randomUUID());
        userAccessLevel.setUser(userFacade.find(id));
    }

    private Boolean isInRole(Collection<UserAccessLevel> accessLevelsForUser, String role){
        for (UserAccessLevel level: accessLevelsForUser) {
            if(level.getAccessLevel().getName().equals(role)){
                return true;
            }
        }
        return false;
    }

    private UserAccessLevel findLevelByName(Collection<UserAccessLevel> accessLevelsForUser, String role){
        for (UserAccessLevel level: accessLevelsForUser) {
            if(level.getAccessLevel().getName().equals(role)){
                return level;
            }
        }
        return null;
    }

}
