package pl.lodz.p.it.ssbd2020.ssbd02.mok.managers;

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
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Stateful
@LocalBean
@Interceptors(LoggerInterceptor.class)
public class UserManager {

    public final static String CLIENT_ACCESS_LEVEL = "CLIENT";

    @Inject
    private UserAccessLevelFacade userAccessLevelFacade;

    @Inject
    private AccessLevelFacade accessLevelFacade;

    @Inject
    private UserFacade userFacade;

    public void registerNewUser(User user) {
        BCryptPasswordHash bCryptPasswordHash = new BCryptPasswordHash();
        String passwordHash = bCryptPasswordHash.generate(user.getPassword().toCharArray());

        user.setVersion(1);
        user.setVersion_user_details(1);
        user.setBusinessKey_user_details(UUID.randomUUID());
        user.setBusinessKey(UUID.randomUUID());
        user.setActivated(false);
        user.setLocked(false);
        user.setCreated(new Date());
        user.setInvalidLoginAttemps(0);
        user.setPassword(passwordHash);
        user.setActivationCode(UUID.randomUUID());
        user.setResetPasswordCode(UUID.randomUUID());
        userFacade.create(user);

        UserAccessLevel userAccessLevel = new UserAccessLevel();
        userAccessLevel.setVersion(1);
        userAccessLevel.setBusinessKey(UUID.randomUUID());
        userAccessLevel.setAccessLevelId(accessLevelFacade.findByAccessLevelName(CLIENT_ACCESS_LEVEL));
        userAccessLevel.setUserId(user);
        userAccessLevelFacade.create(userAccessLevel);
    }

    public List<User> getAll() {
        return userFacade.findAll();
    }

    public void edit(User user) {
        BCryptPasswordHash bCryptPasswordHash = new BCryptPasswordHash();
        String passwordHash = bCryptPasswordHash.generate(user.getPassword().toCharArray());
        user.setPassword(passwordHash);
        userFacade.edit(user);
    }
}
