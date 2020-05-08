package pl.lodz.p.it.ssbd2020.ssbd02.mok.managers;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.SortOrder;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.User;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.UserAccessLevel;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.RepeatedRollBackException;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.exceptions.*;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.facades.AccessLevelFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.facades.UserFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.BCryptPasswordHash;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.PropertyReader;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.SendEmail;
import static java.util.concurrent.TimeUnit.*;

import javax.annotation.PostConstruct;
import javax.ejb.*;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Stateful
@LocalBean
@Interceptors(LoggerInterceptor.class)
public class UserManager extends AbstractManager implements SessionSynchronization {
    private final SendEmail sendEmail = new SendEmail();
    private String CLIENT_ACCESS_LEVEL;
    @Inject
    private AccessLevelFacade accessLevelFacade;
    @Inject
    private UserFacade userFacade;
    @Inject
    private BCryptPasswordHash bCryptPasswordHash;
    private User userEntityEdit;

    private int methodInvocationCounter = 0;

    @PostConstruct
    private void init() {
        PropertyReader propertyReader = new PropertyReader();
        CLIENT_ACCESS_LEVEL = propertyReader.getProperty("config", "CLIENT_ACCESS_LEVEL");
    }

    private void addUser(User user, boolean active) throws AppBaseException {
        PropertyReader propertyReader = new PropertyReader();
        String passwordHash = bCryptPasswordHash.generate(user.getPassword().toCharArray());
        if (userFacade.existByLogin(user.getLogin())) {
            throw new LoginNotUniqueException("exception.loginNotUnique");
        }
        if (userFacade.existByEmail(user.getEmail())) {
            throw new EmailNotUniqueException("exception.emailNotUnique");
        }
        user.setActivated(active);
        user.setLocked(false);
        user.setPassword(passwordHash);
        user.setActivationCode(UUID.randomUUID().toString().replace("-", ""));

        UserAccessLevel userAccessLevel = new UserAccessLevel(user, accessLevelFacade.findByAccessLevelName(CLIENT_ACCESS_LEVEL));

        user.getUserAccessLevels().add(userAccessLevel);

        userFacade.create(user);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void registerNewUser(User user) throws AppBaseException {
    methodInvocationCounter++;
    if(methodInvocationCounter==METHOD_INVOCATION_LIMIT) {
        throw new RepeatedRollBackException("exception.repeated.rollback");
    }
        String passwordHash = bCryptPasswordHash.generate(user.getPassword().toCharArray());
        if (userFacade.existByLogin(user.getLogin())) {
            throw new LoginNotUniqueException("exception.loginNotUnique");
        }
        if (userFacade.existByEmail(user.getEmail())) {
            throw new EmailNotUniqueException("exception.emailNotUnique");
        }
        user.setActivated(false);
        user.setLocked(false);
        user.setPassword(passwordHash);
        user.setActivationCode(UUID.randomUUID().toString().replace("-", ""));

        UserAccessLevel userAccessLevel = new UserAccessLevel(user, accessLevelFacade.findByAccessLevelName(CLIENT_ACCESS_LEVEL));

        user.getUserAccessLevels().add(userAccessLevel);

        userFacade.create(user);

        sendEmailWithCode(user);
    }

    public void addNewUser(User user) throws AppBaseException {
        addUser(user, true);
    }

    public List<User> getAll() {
        return userFacade.findAll();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public User getUserById(Long id) {
        return userFacade.find(id);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void editUser(User user, Long id) throws AppBaseException{
        userFacade.edit(user);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void editUserPassword(User user, Long userId) throws AppBaseException {
        User userToEdit = userFacade.find(userId);
        String passwordHash = bCryptPasswordHash.generate(user.getPassword().toCharArray());
        userToEdit.setPassword(passwordHash);
        userFacade.edit(userToEdit);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void editOwnPassword(User user, String userLogin, String givenOldPassword) throws AppBaseException {
        User userToEdit = userFacade.findByLogin(userLogin);
        BCryptPasswordHash bCryptPasswordHash = new BCryptPasswordHash();
        if(!bCryptPasswordHash.verify(givenOldPassword.toCharArray(), userToEdit.getPassword())) {
            throw new IncorrectPasswordException("exception.incorrectPassword");
        }
        if(bCryptPasswordHash.verify(user.getPassword().toCharArray(), userToEdit.getPassword())) {
            throw new PasswordIdenticalException("exception.passwordIdentical");
        }
        String passwordHash = bCryptPasswordHash.generate(user.getPassword().toCharArray());
        userToEdit.setPassword(passwordHash);
        userFacade.edit(userToEdit);
    }

    public void lockAccount(Long userId) throws AppBaseException {
        User userToEdit = userFacade.find(userId);
        userToEdit.setLocked(true);
        userFacade.edit(userToEdit);

        sendEmail.lockInfoEmail(userToEdit.getEmail());
    }

    public void unlockAccount(Long userId) throws AppBaseException {
        User userToEdit = userFacade.find(userId);
        userToEdit.setLocked(false);
        userFacade.edit(userToEdit);

        sendEmail.unlockInfoEmail(userToEdit.getEmail());
    }


    public User getUserByLogin(String userLogin) throws AppBaseException {
         return userFacade.findByLogin(userLogin);
    }

    public Integer getUserInvalidLoginAttempts(Long ID) {
        User user = getUserById(ID);
        return user.getInvalidLoginAttempts();
    }

    private String createVerificationLink(User user) {
        String activationCode = user.getActivationCode();
        return "<a href=" + "\"http://studapp.it.p.lodz.pl:8002/login/activate.xhtml?key=" + activationCode + "\">Link</a>";
        //http://studapp.it.p.lodz.pl:8002
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void confirmActivationCode(String code) throws AppBaseException {
        User user = userFacade.findByActivationCode(code);
        user.setActivated(true);
        userFacade.edit(user);
        sendEmail.activationInfoEmail(user.getEmail());
    }

    public void sendEmailWithCode(User user) {
        String email = user.getEmail();
        String userName = user.getFirstName();
        sendEmail.sendActivationEmail(createVerificationLink(user), userName, email);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void editUserLastLoginAndInvalidLoginAttempts(User user, Long userId, Integer attempts) throws AppBaseException {
        User userToEdit = userFacade.find(userId);
        userToEdit.setLastValidLogin(user.getLastValidLogin());
        userToEdit.setLastInvalidLogin(user.getLastInvalidLogin());
        userToEdit.setLastLoginIp(user.getLastLoginIp());
        userToEdit.setInvalidLoginAttempts(attempts);
        if (attempts == 3) {
            userToEdit.setInvalidLoginAttempts(0);
            userToEdit.setLocked(true);
        }
        userFacade.edit(userToEdit);
    }

    public int getFilteredRowCount(Map<String, FilterMeta> filters) {
        return userFacade.getFilteredRowCount(filters);
    }

    public List<User> getResultList(int first, int pageSize, Map<String, FilterMeta> filters) {
        return userFacade.getResultList(first, pageSize, filters);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void sendResetPasswordEmail(String email) throws AppBaseException {
        User userToEdit = userFacade.findByEmail(email);
        String resetPasswordCode = UUID.randomUUID().toString().replace("-", "");
        userToEdit.setResetPasswordCode(resetPasswordCode);
        userToEdit.setResetPasswordCodeAddDate(new Date());
        userFacade.edit(userToEdit);
        // TODO tutaj hashowanie resetPasswordCode?

        String link = "<a href=" + "\"http://studapp.it.p.lodz.pl:8002/login/resetPassword.xhtml?key=" + resetPasswordCode + "\">Link</a>";

        sendEmail.sendResetPasswordEmail(email,link);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void resetPassword(String resetPasswordCode, String password) throws AppBaseException {
        User userToEdit = userFacade.findByResetPasswordCode(resetPasswordCode);

        Date resetPasswordCodeAddDate = userToEdit.getResetPasswordCodeAddDate();
        Date now = new Date();
        long MAX_DURATION = MILLISECONDS.convert(15, MINUTES);
        long duration = now.getTime()-resetPasswordCodeAddDate.getTime();
        if(duration>=MAX_DURATION){
            throw new ResetPasswordCodeExpiredException("exception.codeExpired");
        }
        String passwordHash = bCryptPasswordHash.generate(password.toCharArray());
        userToEdit.setPassword(passwordHash);
        userFacade.edit(userToEdit);
    }
}
