package pl.lodz.p.it.ssbd2020.ssbd02.mok.managers;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.User;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.UserAccessLevel;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.exceptions.EmailNotUniqueException;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.exceptions.LoginNotUniqueException;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.facades.AccessLevelFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.facades.UserFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.BCryptPasswordHash;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.PropertyReader;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.SendEmail;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.SessionSynchronization;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.persistence.OptimisticLockException;
import java.util.List;
import java.util.UUID;

@Stateful
@LocalBean
@Interceptors(LoggerInterceptor.class)
public class UserManager extends AbstractManager implements SessionSynchronization {
    private String CLIENT_ACCESS_LEVEL;
    @Inject
    private AccessLevelFacade accessLevelFacade;
    @Inject
    private UserFacade userFacade;
    @Inject
    private BCryptPasswordHash bCryptPasswordHash;

    private final SendEmail sendEmail = new SendEmail();

    private User userEntityEdit;

    @PostConstruct
    private void init() {
        PropertyReader propertyReader= new PropertyReader();
        CLIENT_ACCESS_LEVEL = propertyReader.getProperty("config","CLIENT_ACCESS_LEVEL");
    }

    private void addUser(User user, boolean active) throws AppBaseException {
        PropertyReader propertyReader = new PropertyReader();
        String passwordHash = bCryptPasswordHash.generate(user.getPassword().toCharArray());
        if(userFacade.existByLogin(user.getLogin())) {
            throw new LoginNotUniqueException("exception.loginNotUnique");
        }
        if(userFacade.existByEmail(user.getEmail())) {
            throw new EmailNotUniqueException("exception.emailNotUnique");
        }
        user.setActivated(active);
        user.setLocked(false);
        user.setPassword(passwordHash);
        user.setActivationCode(UUID.randomUUID().toString().replace("-", ""));
        user.setResetPasswordCode(UUID.randomUUID().toString());

        UserAccessLevel userAccessLevel = new UserAccessLevel(user, accessLevelFacade.findByAccessLevelName(CLIENT_ACCESS_LEVEL));

        user.getUserAccessLevels().add(userAccessLevel);

        userFacade.create(user);
    }

    public void registerNewUser(User user) throws AppBaseException{
        addUser(user, false);

        sendEmailWithCode(user);
    }

    public void addNewUser(User user) throws AppBaseException{
        addUser(user, true);
    }

    public List<User> getAll() {
        return userFacade.findAll();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public User getUserById(Long id) {
        this.userEntityEdit = userFacade.find(id);
        return userEntityEdit;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void editUser(User user, long id){
        userFacade.edit(user);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
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

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void editUserLastLogin(User user, Long userId) {
        User userToEdit = userFacade.find(userId);
        userToEdit.setLastValidLogin(user.getLastValidLogin());
        userToEdit.setLastInvalidLogin(user.getLastInvalidLogin());
        userToEdit.setLastLoginIp(user.getLastLoginIp());
        userFacade.edit(userToEdit);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
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

    private String createVerificationLink(User user) {
        String activationCode = user.getActivationCode();
        return "<a href=" + "\"http://localhost:8080/login/activate.xhtml?key=" + activationCode + "\">Link</a>";
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void confirmActivationCode(String code) {
        User user = userFacade.findByActivationCode(code);
        user.setActivated(true);
        userFacade.edit(user);
    }

    public void sendEmailWithCode(User user) {
        String email = user.getEmail();
        String userName = user.getFirstName();
        sendEmail.sendEmail(createVerificationLink(user), userName, email);
    }
}
