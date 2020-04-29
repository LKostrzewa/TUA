package pl.lodz.p.it.ssbd2020.ssbd02.mok.managers;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.User;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.UserAccessLevel;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.facades.AccessLevelFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.facades.UserFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.BCryptPasswordHash;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.SendEmail;

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

    private final SendEmail sendEmail = new SendEmail();

    private void addUser(User user, boolean active) {
        String passwordHash = bCryptPasswordHash.generate(user.getPassword().toCharArray());

        user.setActivated(active);
        user.setLocked(false);
        user.setPassword(passwordHash);
        user.setActivationCode(UUID.randomUUID().toString().replace("-", ""));
        user.setResetPasswordCode(UUID.randomUUID().toString());

        UserAccessLevel userAccessLevel = new UserAccessLevel(user, accessLevelFacade.findByAccessLevelName(CLIENT_ACCESS_LEVEL));

        List<UserAccessLevel> userAccessLevels = List.of(userAccessLevel);
        user.setUserAccessLevels(userAccessLevels);

        userFacade.create(user);

        sendEmailWithCode(user);
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

    public User getUserByLogin(String userLogin) {
        return userFacade.findByLogin(userLogin);
    }

    public void editUserLastLogin(User user, Long userId) {
        User userToEdit = userFacade.find(userId);
        userToEdit.setLastValidLogin(user.getLastValidLogin());
        userToEdit.setLastInvalidLogin(user.getLastInvalidLogin());
        userToEdit.setLastLoginIp(user.getLastLoginIp());
        userFacade.edit(userToEdit);
    }

    public void editInvalidLoginAttempts(Integer counter, Long userId) {
        User userToEdit = userFacade.find(userId);
        userToEdit.setInvalidLoginAttempts(counter);
        if(counter==3) {
            userToEdit.setInvalidLoginAttempts(0);
            userToEdit.setLocked(true);
        }
        userFacade.edit(userToEdit);
    }

    public Integer getUserInvalidLoginAttempts(Long ID) {
        User user = getUserById(ID);
        return user.getInvalidLoginAttempts();
    }

    private String createVeryficationLink(User user) {
        String activationCode = user.getActivationCode();
        return "<a href=" + "\"http://localhost:8080/login/activate.xhtml?key=" + activationCode + "\">Link</a>";
    }

    public void confirmActivationCode(String code) {
        User user = userFacade.findByActivationCode(code);
        user.setActivated(true);
        userFacade.edit(user);
    }

    public void sendEmailWithCode(User user) {
        String email = user.getEmail();
        String userName = user.getFirstName();
        sendEmail.sendEmail(createVeryficationLink(user), userName, email);
    }
}
