package pl.lodz.p.it.ssbd2020.ssbd02.mok.managers;

import org.primefaces.model.FilterMeta;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.User;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.UserAccessLevel;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.*;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.ResetPasswordDto;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.facades.AccessLevelFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.facades.UserFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.BCryptPasswordHash;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.PropertyReader;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.SendEmail;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.*;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.MINUTES;
/**
 * Klasa menadżera do obsługi operacji związanych z użytkownikami
 */
@Stateful
@LocalBean
@Interceptors(LoggerInterceptor.class)
public class UserManager extends AbstractManager implements SessionSynchronization {
    private final SendEmail sendEmail = new SendEmail();
    private String ADMIN_ACCESS_LEVEL;
    private String CLIENT_ACCESS_LEVEL;
    @Inject
    private AccessLevelFacade accessLevelFacade;
    @Inject
    private UserFacade userFacade;
    @Inject
    private BCryptPasswordHash bCryptPasswordHash;

    private PropertyReader propertyReader = new PropertyReader();


    @PostConstruct
    private void init() {
        ADMIN_ACCESS_LEVEL = propertyReader.getProperty("config", "ADMIN_ACCESS_LEVEL");
        CLIENT_ACCESS_LEVEL = propertyReader.getProperty("config", "CLIENT_ACCESS_LEVEL");
    }

    /**
     * Metoda, która dodaje nowego użytkownika do bazy danych poprzez userFacade.
     *
     * @param user Encja użytkownika do dodania.
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("addNewUser")
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void addNewUser(User user) throws AppBaseException {
        try {
            String passwordHash = bCryptPasswordHash.generate(user.getPassword().toCharArray());
            if (userFacade.existByLogin(user.getLogin())) {
                throw ValueNotUniqueException.createLoginNotUniqueException(user);
            }
            if (userFacade.existByEmail(user.getEmail())) {
                throw ValueNotUniqueException.createEmailNotUniqueException(user);
            }
            user.setActivated(true);
            user.setLocked(false);
            user.setPassword(passwordHash);
            user.setActivationCode(UUID.randomUUID().toString().replace("-", ""));

            UserAccessLevel userAccessLevel = new UserAccessLevel(user, accessLevelFacade.findByAccessLevelByName(CLIENT_ACCESS_LEVEL));

            user.getUserAccessLevels().add(userAccessLevel);

            userFacade.create(user);
        } catch (EJBTransactionRolledbackException e) {
            throw AppEJBTransactionRolledbackException.createAppEJBTransactionRolledbackException(e);
        }
    }

    /**
     * Metoda rejestrujaca nowego uzytkownika poprzez dodanie go do bazy danych za pomocą userFacade
     *
     * @param user Encja użytkownika do zarejestrowania.
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void registerNewUser(User user) throws AppBaseException {
        try {
            String passwordHash = bCryptPasswordHash.generate(user.getPassword().toCharArray());
            if (userFacade.existByLogin(user.getLogin())) {
                throw ValueNotUniqueException.createLoginNotUniqueException(user);
            }
            if (userFacade.existByEmail(user.getEmail())) {
                throw ValueNotUniqueException.createEmailNotUniqueException(user);
            }
            user.setActivated(false);
            user.setLocked(false);
            user.setPassword(passwordHash);
            user.setActivationCode(UUID.randomUUID().toString().replace("-", ""));

            UserAccessLevel userAccessLevel = new UserAccessLevel(user, accessLevelFacade.findByAccessLevelByName(CLIENT_ACCESS_LEVEL));

            user.getUserAccessLevels().add(userAccessLevel);

            userFacade.create(user);

            sendEmailWithCode(user);
        } catch (EJBTransactionRolledbackException e) {
            throw AppEJBTransactionRolledbackException.createAppEJBTransactionRolledbackException(e);
        }
    }


    /**
     * Metoda, która pobiera z bazy listę obiektów.
     *
     * @return lista obiektów
     */
    @RolesAllowed("getUserReport")
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List<User> getAll() {
        return userFacade.findAll();
    }


    /**
     * Metoda, która pobiera z użytkownika na podstawie jego identyfikatora w bazie
     *
     * @param id identyfikator Użytkownika
     * @return encja User
     */
    @RolesAllowed({"getEditUserDtoById", "findUserAccessLevelById"})
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public User getUserById(Long id) throws AppBaseException {
        return userFacade.find(id).orElseThrow(AppNotFoundException::createUserNotFoundException);
    }

    /**
     * Metoda wykorzystywana do zmiany danych konta użytkownika
     *
     * @param user obiekt przechowujący dane wprowadzone w formularzu
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed({"editUser", "editOwnData"})
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void editUser(User user) throws AppBaseException {
        try {
            userFacade.edit(user);
        } catch (EJBTransactionRolledbackException e) {
            throw AppEJBTransactionRolledbackException.createAppEJBTransactionRolledbackException(e);
        }
    }

    /**
     * Metoda wykorzystywana do zmiany hasła innego użytkownika zgodnie z przekazanymi parametrami.
     *
     * @param user   obiekt przechowujący dane wprowadzone w formularzu
     * @param userId id użytkownika, którego hasło ulegnie modyfikacji
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("changeUserPassword")
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void changeUserPassword(User user, Long userId) throws AppBaseException {
        try {
            User userToEdit = userFacade.find(userId).orElseThrow(AppNotFoundException::createUserNotFoundException);
            String passwordHash = bCryptPasswordHash.generate(user.getPassword().toCharArray());
            userToEdit.setPassword(passwordHash);
            userFacade.edit(userToEdit);
        } catch (EJBTransactionRolledbackException e) {
            throw AppEJBTransactionRolledbackException.createAppEJBTransactionRolledbackException(e);
        }
    }

    /**
     * Metoda wykorzystywana do zmiany własnego hasła zgodnie z przekazanymi parametrami.
     *
     * @param user             obiekt przechowujący dane wprowadzone w formularzu
     * @param givenOldPassword hasło podane w formularzu wykorzystywane przy weryfikacji użytkownika
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("changeOwnPassword")
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void changeOwnPassword(User user, String givenOldPassword) throws AppBaseException {
        try {
            User userToEdit = userFacade.findByLogin();
            BCryptPasswordHash bCryptPasswordHash = new BCryptPasswordHash();
            if (!bCryptPasswordHash.verify(givenOldPassword.toCharArray(), userToEdit.getPassword())) {
                throw IncorrectPasswordException.createIncorrectPasswordException(user);
            }
            if (bCryptPasswordHash.verify(user.getPassword().toCharArray(), userToEdit.getPassword())) {
                throw PasswordIdenticalException.createPasswordIdenticalException(user);
            }
            String passwordHash = bCryptPasswordHash.generate(user.getPassword().toCharArray());
            userToEdit.setPassword(passwordHash);
            userFacade.edit(userToEdit);
        } catch (EJBTransactionRolledbackException e) {
            throw AppEJBTransactionRolledbackException.createAppEJBTransactionRolledbackException(e);
        }
    }

    /**
     * Metoda, która blokuje konto o podanym id.
     *
     * @param userId id użytkownika.
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("lockAccount")
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void lockAccount(Long userId) throws AppBaseException {
        try {
            User userToEdit = userFacade.find(userId).orElseThrow(AppNotFoundException::createUserNotFoundException);
            if (userToEdit.getLocked()) {
                throw UserDataChangedException.lockValueChanged(userToEdit);
            }
            userToEdit.setLocked(true);
            userFacade.edit(userToEdit);
            sendEmail.lockInfoEmail(userToEdit.getEmail());
        } catch (EJBTransactionRolledbackException e) {
            throw AppEJBTransactionRolledbackException.createAppEJBTransactionRolledbackException(e);
        }
    }

    /**
     * Metoda, która odblokowywuje konto o podanym id.
     *
     * @param userId id użytkownika.
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("unlockAccount")
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void unlockAccount(Long userId) throws AppBaseException {
        try {
            User userToEdit = userFacade.find(userId).orElseThrow(AppNotFoundException::createUserNotFoundException);
            if (!userToEdit.getLocked()) {
                throw UserDataChangedException.unlockValueChanged(userToEdit);
            }
            userToEdit.setLocked(false);
            userFacade.edit(userToEdit);

            sendEmail.unlockInfoEmail(userToEdit.getEmail());
        } catch (EJBTransactionRolledbackException e) {
            throw AppEJBTransactionRolledbackException.createAppEJBTransactionRolledbackException(e);
        }
    }

    /**
     * Metoda, która zwraca aktualnie zalogowanego użytkownika
     *
     * @return encje User
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed({"getLoginDtoByLogin", "getEditUserDtoByLogin", "findUserAccessLevelByLogin"})
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public User getUserByLogin() throws AppBaseException {
        return userFacade.findByLogin();
    }

    /**
     * Metoda prywatna do tworzenia linku weryfikującego konto
     *
     * @param user użytkownik dla którego link należy utworzyć
     * @return utworzony link
     */
    private String createVerificationLink(User user) {
        String activationCode = user.getActivationCode();

        String url = propertyReader.getProperty("config", "link_to_activate_account");
        return  "<a href=" + "\"" + url + activationCode + "\">Link</a>";
        //return "<a href=" + "\"http://studapp.it.p.lodz.pl:8002/login/activate.xhtml?key=" + activationCode + "\">Link</a>";
        //http://studapp.it.p.lodz.pl:8002
    }

    /**
     * Metoda aktywująca użytkownika o danym kodzie aktywacyjnym
     *
     * @param code kod aktywacyjny
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void confirmActivationCode(String code) throws AppBaseException {
        try {
            User user = userFacade.findByActivationCode(code);
            user.setActivated(true);
            userFacade.edit(user);
            sendEmail.activationInfoEmail(user.getEmail());
        } catch (EJBTransactionRolledbackException e) {
            throw AppEJBTransactionRolledbackException.createAppEJBTransactionRolledbackException(e);
        }
    }

    /**
     * Metoda wysyłająca email aktywacyjny
     *
     * @param user użytkownik do którego zostanie wysłany email
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    public void sendEmailWithCode(User user) throws AppBaseException {
        try {
            String email = user.getEmail();
            String userName = user.getFirstName();
            sendEmail.sendActivationEmail(createVerificationLink(user), userName, email);
        } catch (EJBTransactionRolledbackException e) {
            throw AppEJBTransactionRolledbackException.createAppEJBTransactionRolledbackException(e);
        }
    }

    /**
     * Metoda, która zapisuje informacje o poprawnym uwierzytelnianiu( adres ip użytkownika, data logowania).
     * Ustawia ilość niepoprawnych logować na 0. Wysyła mail do użytkownika jeśli zalogował się administrator.
     *
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("saveSuccessAuthenticate")
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void saveSuccessAuthenticate() throws AppBaseException {
        try {
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .getRequest();
            String xForwardedForHeader = request.getHeader("X-Forwarded-For");

            User userToEdit = userFacade.findByLogin();
            String clientIpAddress;
            if (xForwardedForHeader == null) {
                clientIpAddress = request.getRemoteAddr();
            } else {
                clientIpAddress = new StringTokenizer(xForwardedForHeader, ",").nextToken().trim();
            }
            userToEdit.setLastLoginIp(clientIpAddress);
            userToEdit.setLastValidLogin(new Date());
            userToEdit.setInvalidLoginAttempts(0);
            userFacade.edit(userToEdit);

            if (userToEdit.getUserAccessLevels().stream().anyMatch(n -> n.getAccessLevel().getName().equals(ADMIN_ACCESS_LEVEL))) {
                sendEmail.sendEmailNotificationAboutNewAdminAuthentication(userToEdit.getEmail(), clientIpAddress);
            }
        } catch (EJBTransactionRolledbackException e) {
            throw AppEJBTransactionRolledbackException.createAppEJBTransactionRolledbackException(e);
        }
    }

    /**
     * Metoda, która zapisuje informacje o niepoprawnym uwierzytelnianiu( adres ip użytkownika, data logowania).
     * Zwiększa ilość niepoprawnych logować o 1. Jeśli wartość niepoprawnych logowań osiągnie 3, blokuje konto i wysyła maila.
     *
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void saveFailureAuthenticate() throws AppBaseException {
        try {
            User userToEdit = userFacade.findByLogin();
            boolean sendBlockEmail = false;
            if (userToEdit.isActivated() && !userToEdit.isLocked()) {
                userToEdit.setLastInvalidLogin(new Date());
                int attempts = userToEdit.getInvalidLoginAttempts() + 1;
                if (attempts == 3) {
                    userToEdit.setInvalidLoginAttempts(0);
                    userToEdit.setLocked(true);
                    sendBlockEmail = true;
                } else {
                    userToEdit.setInvalidLoginAttempts(attempts);
                }
                userFacade.edit(userToEdit);

                if (sendBlockEmail) {
                    sendEmail.lockInfoEmail(userToEdit.getEmail());
                }
            }
        } catch (EJBTransactionRolledbackException e) {
            throw AppEJBTransactionRolledbackException.createAppEJBTransactionRolledbackException(e);
        }
    }

    /**
     * Metoda, która pobiera z bazy liczbę filtrowanych obiektów.
     *
     * @param filters para filtrowanych pól i ich wartości
     * @return liczba obiektów poddanych filtrowaniu
     */
    @RolesAllowed("getFilteredRowCount")
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public int getFilteredRowCount(Map<String, FilterMeta> filters) {
        return userFacade.getFilteredRowCount(filters);
    }

    /**
     * Metoda, która pobiera z bazy listę filtrowanych obiektów.
     *
     * @param first    numer pierwszego obiektu
     * @param pageSize rozmiar strony
     * @param filters  para filtrowanych pól i ich wartości
     * @return lista filtrowanych obiektów
     */
    @RolesAllowed("getResultList")
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List<User> getResultList(int first, int pageSize, Map<String, FilterMeta> filters) {
        return userFacade.getResultList(first, pageSize, filters);
    }

    /**
     * Metoda, która na podany email wysyła wiadomość z linkiem, pod którym można zresetować zapomniane hasło
     *
     * @param email adres email
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void sendResetPasswordEmail(String email) throws AppBaseException {
        try {
            if (!userFacade.existByEmail(email)) {
                throw AppNotFoundException.createEmailNotFoundException();
            }
            User userToEdit = userFacade.findByEmail(email);
            String resetPasswordCode = UUID.randomUUID().toString().replace("-", "");
            userToEdit.setResetPasswordCode(resetPasswordCode);
            userToEdit.setResetPasswordCodeAddDate(new Date());
            userFacade.edit(userToEdit);

            String url = propertyReader.getProperty("config", "link_to_reset_password");
            String link = "<a href=" + "\"" + url + resetPasswordCode + "\">Link</a>";

            sendEmail.sendResetPasswordEmail(email, link);
        } catch (EJBTransactionRolledbackException e) {
            throw AppEJBTransactionRolledbackException.createAppEJBTransactionRolledbackException(e);
        }
    }

    /**
     * Metoda, która zmienia zapomniane hasło
     *
     * @param resetPasswordDto dto z danymi użytkownika potrzebnymi do zresetowania hasła
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void resetPassword(ResetPasswordDto resetPasswordDto) throws AppBaseException {
        try {
            User userToEdit = userFacade.findByResetPasswordCode(resetPasswordDto.getResetPasswordCode());
            Date resetPasswordCodeAddDate = userToEdit.getResetPasswordCodeAddDate();
            Date now = new Date();
            long time = Long.parseLong(propertyReader.getProperty("config", "reset_password_key_valid_time"));
            long MAX_DURATION = MILLISECONDS.convert(time, MINUTES);
            long duration = now.getTime() - resetPasswordCodeAddDate.getTime();
            if (duration >= MAX_DURATION) {
                throw ResetPasswordCodeExpiredException.createPasswordExceptionWithCodeExpiredConstraint(userToEdit);
            }
            if (bCryptPasswordHash.verify(resetPasswordDto.getPassword().toCharArray(), userToEdit.getPassword())) {
                throw PasswordIdenticalException.createPasswordIdenticalException(userToEdit);
            }
            String passwordHash = bCryptPasswordHash.generate(resetPasswordDto.getPassword().toCharArray());
            userToEdit.setPassword(passwordHash);
            userFacade.edit(userToEdit);
        } catch (EJBTransactionRolledbackException e) {
            throw AppEJBTransactionRolledbackException.createAppEJBTransactionRolledbackException(e);
        }
    }

    @PermitAll
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void deleteInactiveUsers(){
        List<User> users = userFacade.findAll();
        for (User user : users) {
            if((!user.isActivated())&&(user.getCreated().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().isBefore(LocalDateTime.now().minusDays(1)))){
                userFacade.remove(user);
            }
        }
    }

}
