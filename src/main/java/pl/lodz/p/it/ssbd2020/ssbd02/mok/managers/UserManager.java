package pl.lodz.p.it.ssbd2020.ssbd02.mok.managers;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.User;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.UserAccessLevel;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.facades.AccessLevelFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.facades.UserFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.BCryptPasswordHash;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.List;
import java.util.UUID;

@Stateful
@LocalBean
@Interceptors(LoggerInterceptor.class)
public class UserManager {
    public final static String CLIENT_ACCESS_LEVEL = "CLIENT";
    @Inject
    private AccessLevelFacade accessLevelFacade;
    @Inject
    private UserFacade userFacade;
    @Inject
    private BCryptPasswordHash bCryptPasswordHash;

    private void addUser(User user, boolean active) {
        String passwordHash = bCryptPasswordHash.generate(user.getPassword().toCharArray());

        user.setActivated(active);
        user.setLocked(false);
        user.setPassword(passwordHash);
        user.setActivationCode(UUID.randomUUID().toString());
        user.setResetPasswordCode(UUID.randomUUID());

        UserAccessLevel userAccessLevel = new UserAccessLevel(user, accessLevelFacade.findByAccessLevelName(CLIENT_ACCESS_LEVEL));

        List<UserAccessLevel> userAccessLevels = List.of(userAccessLevel);
        user.setUserAccessLevels(userAccessLevels);

        userFacade.create(user);
    }

    public void registerNewUser(User user) {
        addUser(user, false);
    }

    public void addNewUser(User user) {
        addUser(user, true);
    }

    public List<User> getAll() {
        return userFacade.findAll();
    }

    public User getUserById(Long id) {
        return userFacade.find(id);
    }

    public void editUser(User user, Long userId) {
        User userToEdit = userFacade.find(userId);
        userToEdit.setFirstName(user.getFirstName());
        userToEdit.setLastName(user.getLastName());
        userToEdit.setEmail(user.getEmail());
        userToEdit.setPhoneNumber(user.getPhoneNumber());
        userToEdit.setLocked(user.getLocked());
        userFacade.edit(userToEdit);
    }

    public void editUserPassword(User user, Long userId) {
        User userToEdit = userFacade.find(userId);
        BCryptPasswordHash bCryptPasswordHash = new BCryptPasswordHash();
        String passwordHash = bCryptPasswordHash.generate(user.getPassword().toCharArray());
        userToEdit.setPassword(passwordHash);
        userFacade.edit(userToEdit);
    }
}
