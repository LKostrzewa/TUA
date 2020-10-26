package pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints;


import org.primefaces.model.FilterMeta;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.User;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppEJBTransactionRolledbackException;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.RepeatedRollBackException;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.*;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.managers.UserManager;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.ObjectMapperUtils;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.PropertyReader;

import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementacja UserEndpoint.
 */
@Stateful(name = "UserEndpointImpl")
@Named("UserEndpointImpl")
@Interceptors(LoggerInterceptor.class)
public class UserEndpointImpl implements Serializable, UserEndpoint {
    PropertyReader propertyReader = new PropertyReader();
    Integer METHOD_INVOCATION_LIMIT;
    Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    @Inject
    private UserManager userManager;
    private User userEditEntity;

    /**
     * Metoda inicjalizująca komponent.
     */
    @PostConstruct
    public void init() {
        METHOD_INVOCATION_LIMIT = Integer.parseInt(propertyReader.getProperty("config", "rollback.invocation.limit"));
    }

    /**
     * Metoda służąca do rejestracji użytkownika.
     *
     * @param userDTO obiekt DTO z danymi rejestrowanego użytkownika
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @PermitAll
    public void registerNewUser(AddUserDto userDTO) throws AppBaseException {
        int methodInvocationCounter = 0;
        boolean rollback;
        do {
            try {
                User user = new User(userDTO.getLogin(), userDTO.getPassword(),
                        userDTO.getEmail(), userDTO.getFirstName(), userDTO.getLastName(),
                        userDTO.getPhoneNumber());
                userManager.registerNewUser(user);
                rollback = userManager.isLastTransactionRollback();
            } catch (AppEJBTransactionRolledbackException ex) {
                logger.log(Level.WARNING, "Exception EJBTransactionRolledback");
                rollback = true;
            } finally {
                if (methodInvocationCounter > 0)
                    logger.log(Level.WARNING, "Transaction repeated " + methodInvocationCounter + " times");
                methodInvocationCounter++;
            }
        } while (rollback && methodInvocationCounter < METHOD_INVOCATION_LIMIT);

        if (methodInvocationCounter == METHOD_INVOCATION_LIMIT) {
            throw RepeatedRollBackException.createRepeatedRollBackException();
        }
    }

    /**
     * Metoda, służy do dodawania nowych użytkowników do bazy danych przez administratora.
     *
     * @param userDTO obiekt DTO z danymi nowego użytkownika
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed({"ADMINISTRATOR"})
    public void addNewUser(AddUserDto userDTO) throws AppBaseException {
        User user;
        int methodInvocationCounter = 0;
        boolean rollback;
        do {
            try {
                user = new User(userDTO.getLogin(), userDTO.getPassword(), userDTO.getEmail(), userDTO.getFirstName(), userDTO.getLastName(), userDTO.getPhoneNumber());
                userManager.addNewUser(user);
                rollback = userManager.isLastTransactionRollback();
            } catch (AppEJBTransactionRolledbackException ex) {
                logger.log(Level.WARNING, "Exception EJBTransactionRolledback");
                rollback = true;
            } finally {
                if (methodInvocationCounter > 0)
                    logger.log(Level.WARNING, "Transaction repeated " + methodInvocationCounter + " times");
                methodInvocationCounter++;
            }
        } while (rollback && methodInvocationCounter < METHOD_INVOCATION_LIMIT);

        if (methodInvocationCounter == METHOD_INVOCATION_LIMIT) {
            throw RepeatedRollBackException.createRepeatedRollBackException();
        }
    }

    /**
     * Metoda, która pobiera z bazy listę obiektów.
     *
     * @return lista obiektów UserReportDto
     */
    @RolesAllowed({"ADMINISTRATOR"})
    public List<UserReportDto> getUserReport() {
        return ObjectMapperUtils.mapAll(userManager.getAll(), UserReportDto.class);
    }

    /**
     * Metoda, która pobiera użytkownika do edycji przez administratora po identyfikatorze użytkownika.
     *
     * @param userId identyfikator użytkownika
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed({"ADMINISTRATOR"})
    public EditUserDto getEditUserDtoById(Long userId) throws AppBaseException {
        this.userEditEntity = userManager.getUserById(userId);
        return ObjectMapperUtils.map(this.userEditEntity, EditUserDto.class);
    }

    /**
     * Metoda, która pobiera użytkownika do edycji własnych danych osobowych.
     *
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed({"ADMINISTRATOR", "MANAGER", "CLIENT"})
    public EditUserDto getEditUserDtoByLogin() throws AppBaseException {
        String username = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName();
        this.userEditEntity = userManager.getUserByLogin(username);
        return ObjectMapperUtils.map(this.userEditEntity, EditUserDto.class);
    }

    /**
     * Metoda, która zwraca login dto o aktualnie zalogowanego użytkownika.
     *
     * @return user login dto
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed({"ADMINISTRATOR", "MANAGER", "CLIENT"})
    public UserLoginDto getLoginDtoByLogin(String login) throws AppBaseException {
        return ObjectMapperUtils.map(userManager.getUserByLogin(login), UserLoginDto.class);
    }

    /**
     * Metoda, która zapisuje wprowadzone przez administratora zmiany w danych konta użytkownika.
     *
     * @param editUserDto obiekt przechowujący dane wprowadzone w formularzu
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed({"ADMINISTRATOR"})
    public void editUser(EditUserDto editUserDto) throws AppBaseException {
        int methodInvocationCounter = 0;
        boolean rollback;
        do {
            try {
                userEditEntity.setFirstName(editUserDto.getFirstName());
                userEditEntity.setLastName(editUserDto.getLastName());
                userEditEntity.setPhoneNumber(editUserDto.getPhoneNumber());
                userManager.editUser(this.userEditEntity);

                rollback = userManager.isLastTransactionRollback();
            } catch (AppEJBTransactionRolledbackException ex) {
                logger.log(Level.WARNING, "Exception EJBTransactionRolledback");
                rollback = true;
            } finally {
                if (methodInvocationCounter > 0)
                    logger.log(Level.WARNING, "Transaction repeated " + methodInvocationCounter + " times");
                methodInvocationCounter++;
            }
        } while (rollback && methodInvocationCounter < METHOD_INVOCATION_LIMIT);

        if (methodInvocationCounter == METHOD_INVOCATION_LIMIT) {
            throw RepeatedRollBackException.createRepeatedRollBackException();
        }
    }

    /**
     * Metoda, która zapisuje wprowadzone zmiany w danych swojego konta.
     *
     * @param editUserDto obiekt przechowujący dane wprowadzone w formularzu
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed({"ADMINISTRATOR", "MANAGER", "CLIENT"})
    public void editOwnData(EditUserDto editUserDto) throws AppBaseException {
        int methodInvocationCounter = 0;
        boolean rollback;
        do {
            try {
                userEditEntity.setFirstName(editUserDto.getFirstName());
                userEditEntity.setLastName(editUserDto.getLastName());
                userEditEntity.setPhoneNumber(editUserDto.getPhoneNumber());
                userManager.editUser(this.userEditEntity);
                rollback = userManager.isLastTransactionRollback();
            } catch (AppEJBTransactionRolledbackException ex) {
                logger.log(Level.WARNING, "Exception EJBTransactionRolledback");
                rollback = true;
            } finally {
                if (methodInvocationCounter > 0)
                    logger.log(Level.WARNING, "Transaction repeated " + methodInvocationCounter + " times");
                methodInvocationCounter++;
            }
        } while (rollback && methodInvocationCounter < METHOD_INVOCATION_LIMIT);

        if (methodInvocationCounter == METHOD_INVOCATION_LIMIT) {
            throw RepeatedRollBackException.createRepeatedRollBackException();
        }
    }

    /**
     * Metoda wykorzystywana do zmiany hasła innego użytkownika zgodnie z przekazanymi parametrami.
     *
     * @param changePasswordDto obiekt przechowujący dane wprowadzone w formularzu
     * @param userId            id użytkownika, którego hasło ulegnie modyfikacji
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed({"ADMINISTRATOR"})
    public void changeUserPassword(ChangePasswordDto changePasswordDto, Long userId) throws AppBaseException {
        int methodInvocationCounter = 0;
        boolean rollback;
        do {
            try {
                User user = ObjectMapperUtils.map(changePasswordDto, User.class);
                userManager.changeUserPassword(user, userId);
                rollback = userManager.isLastTransactionRollback();
            } catch (AppEJBTransactionRolledbackException ex) {
                logger.log(Level.WARNING, "Exception EJBTransactionRolledback");
                rollback = true;
            } finally {
                if (methodInvocationCounter > 0)
                    logger.log(Level.WARNING, "Transaction repeated " + methodInvocationCounter + " times");
                methodInvocationCounter++;
            }
        } while (rollback && methodInvocationCounter < METHOD_INVOCATION_LIMIT);

        if (methodInvocationCounter == METHOD_INVOCATION_LIMIT) {
            throw RepeatedRollBackException.createRepeatedRollBackException();
        }
    }

    /**
     * Metoda wykorzystywana do zmiany własnego hasła zgodnie z przekazanymi parametrami.
     *
     * @param changeOwnPasswordDto obiekt przechowujący dane wprowadzone w formularzu
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed({"ADMINISTRATOR", "MANAGER", "CLIENT"})
    public void changeOwnPassword(ChangeOwnPasswordDto changeOwnPasswordDto) throws AppBaseException {
        int methodInvocationCounter = 0;
        boolean rollback;
        do {
            try {
                User user = ObjectMapperUtils.map(changeOwnPasswordDto, User.class);
                userManager.changeOwnPassword(user, changeOwnPasswordDto.getOldPassword());
                rollback = userManager.isLastTransactionRollback();
            } catch (AppEJBTransactionRolledbackException ex) {
                logger.log(Level.WARNING, "Exception EJBTransactionRolledback");
                rollback = true;
            } finally {
                if (methodInvocationCounter > 0)
                    logger.log(Level.WARNING, "Transaction repeated " + methodInvocationCounter + " times");
                methodInvocationCounter++;
            }
        } while (rollback && methodInvocationCounter < METHOD_INVOCATION_LIMIT);

        if (methodInvocationCounter == METHOD_INVOCATION_LIMIT) {
            throw RepeatedRollBackException.createRepeatedRollBackException();
        }
    }

    /**
     * Metoda, która blokuje konto o podanym id.
     *
     * @param userId id użytkownika.
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed({"ADMINISTRATOR"})
    public void lockAccount(Long userId) throws AppBaseException {
        int methodInvocationCounter = 0;
        boolean rollback;
        do {
            try {
                userManager.lockAccount(userId);
                rollback = userManager.isLastTransactionRollback();
            } catch (AppEJBTransactionRolledbackException ex) {
                logger.log(Level.WARNING, "Exception EJBTransactionRolledback");
                rollback = true;
            } finally {
                if (methodInvocationCounter > 0)
                    logger.log(Level.WARNING, "Transaction repeated " + methodInvocationCounter + " times");
                methodInvocationCounter++;
            }
        } while (rollback && methodInvocationCounter < METHOD_INVOCATION_LIMIT);

        if (methodInvocationCounter == METHOD_INVOCATION_LIMIT) {
            throw RepeatedRollBackException.createRepeatedRollBackException();
        }
    }

    /**
     * Metoda, która odblokowywuje konto o podanym id.
     *
     * @param userId id użytkownika
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed({"ADMINISTRATOR"})
    public void unlockAccount(Long userId) throws AppBaseException {
        int methodInvocationCounter = 0;
        boolean rollback;
        do {
            try {
                userManager.unlockAccount(userId);
                rollback = userManager.isLastTransactionRollback();
            } catch (AppEJBTransactionRolledbackException ex) {
                logger.log(Level.WARNING, "Exception EJBTransactionRolledback");
                rollback = true;
            } finally {
                if (methodInvocationCounter > 0)
                    logger.log(Level.WARNING, "Transaction repeated " + methodInvocationCounter + " times");
                methodInvocationCounter++;
            }
        } while (rollback && methodInvocationCounter < METHOD_INVOCATION_LIMIT);

        if (methodInvocationCounter == METHOD_INVOCATION_LIMIT) {
            throw RepeatedRollBackException.createRepeatedRollBackException();
        }
    }

    /**
     * Metoda która aktywuje dane konto po kliknięciu w link aktywacyjny.
     *
     * @param code kod aktywacyjny użytkownika
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @PermitAll
    public Boolean activateAccount(String code) throws AppBaseException {
        int methodInvocationCounter = 0;
        boolean rollback;
        Boolean active = false;
        do {
            try {
                active = userManager.confirmActivationCode(code);
                rollback = userManager.isLastTransactionRollback();
            } catch (AppEJBTransactionRolledbackException ex) {
                logger.log(Level.WARNING, "Exception EJBTransactionRolledback");
                rollback = true;
            } finally {
                if (methodInvocationCounter > 0)
                    logger.log(Level.WARNING, "Transaction repeated " + methodInvocationCounter + " times");
                methodInvocationCounter++;
            }
        } while (rollback && methodInvocationCounter < METHOD_INVOCATION_LIMIT);

        if (methodInvocationCounter == METHOD_INVOCATION_LIMIT) {
            throw RepeatedRollBackException.createRepeatedRollBackException();
        }
        return active;
    }

    /**
     * Metoda, która zapisuje informacje o poprawnym uwierzytelnianiu (adres ip użytkownika, data logowania).
     *
     * @param login login uzytkownika
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed({"ADMINISTRATOR", "MANAGER", "CLIENT"})
    public void saveSuccessAuthenticate(String login) throws AppBaseException {
        int methodInvocationCounter = 0;
        boolean rollback;
        do {
            try {
                userManager.saveSuccessAuthenticate(login);
                rollback = userManager.isLastTransactionRollback();
            } catch (AppEJBTransactionRolledbackException ex) {
                logger.log(Level.WARNING, "Exception EJBTransactionRolledback");
                rollback = true;
            } finally {
                if (methodInvocationCounter > 0)
                    logger.log(Level.WARNING, "Transaction repeated " + methodInvocationCounter + " times");
                methodInvocationCounter++;
            }
        } while (rollback && methodInvocationCounter < METHOD_INVOCATION_LIMIT);

        if (methodInvocationCounter == METHOD_INVOCATION_LIMIT) {
            throw RepeatedRollBackException.createRepeatedRollBackException();
        }
    }

    /**
     * Metoda, która zapisuje informacje o niepoprawnym uwierzytelnianiu( adres ip użytkownika, data logowania).
     *
     * @param login login uzytkownika
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @PermitAll
    public void saveFailureAuthenticate(String login) throws AppBaseException {
        int methodInvocationCounter = 0;
        boolean rollback;
        do {
            try {
                userManager.saveFailureAuthenticate(login);
                rollback = userManager.isLastTransactionRollback();
            } catch (AppEJBTransactionRolledbackException ex) {
                logger.log(Level.WARNING, "Exception EJBTransactionRolledback");
                rollback = true;
            } finally {
                if (methodInvocationCounter > 0)
                    logger.log(Level.WARNING, "Transaction repeated " + methodInvocationCounter + " times");
                methodInvocationCounter++;
            }
        } while (rollback && methodInvocationCounter < METHOD_INVOCATION_LIMIT);

        if (methodInvocationCounter == METHOD_INVOCATION_LIMIT) {
            throw RepeatedRollBackException.createRepeatedRollBackException();
        }
    }


    /**
     * Metoda, która pobiera z bazy liczbę filtrowanych obiektów.
     *
     * @param filters para filtrowanych pól i ich wartości
     * @return liczba obiektów poddanych filtrowaniu
     */
    @RolesAllowed({"ADMINISTRATOR"})
    public int getFilteredRowCount(Map<String, FilterMeta> filters) {
        return userManager.getFilteredRowCount(filters);
    }

    /**
     * Metoda, która pobiera z bazy listę filtrowanych obiektów.
     *
     * @param first    numer pierwszego obiektu
     * @param pageSize rozmiar strony
     * @param filters  para filtrowanych pól i ich wartości
     * @return lista filtrowanych obiektów
     */
    @RolesAllowed({"ADMINISTRATOR"})
    public List<ListUsersDto> getResultList(int first, int pageSize, Map<String, FilterMeta> filters) {
        return ObjectMapperUtils.mapAll(userManager.getResultList(first, pageSize, filters), ListUsersDto.class);
    }

    /**
     * Metoda, która na podany email wysyła wiadomość z linkiem, pod którym można zresetować zapomniane hasło.
     *
     * @param email adres email
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @PermitAll
    public void sendResetPasswordEmail(String email) throws AppBaseException {
        int methodInvocationCounter = 0;
        boolean rollback;
        do {
            try {
                userManager.sendResetPasswordEmail(email);
                rollback = userManager.isLastTransactionRollback();
            } catch (AppEJBTransactionRolledbackException ex) {
                logger.log(Level.WARNING, "Exception EJBTransactionRolledback");
                rollback = true;
            } finally {
                if (methodInvocationCounter > 0)
                    logger.log(Level.WARNING, "Transaction repeated " + methodInvocationCounter + " times");
                methodInvocationCounter++;
            }
        } while (rollback && methodInvocationCounter < METHOD_INVOCATION_LIMIT);

        if (methodInvocationCounter == METHOD_INVOCATION_LIMIT) {
            throw RepeatedRollBackException.createRepeatedRollBackException();
        }
    }

    /**
     * Metoda, która zmienia zapomniane hasło.
     *
     * @param resetPasswordDto obiekt przechowujący dane wprowadzone w formularzu
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @PermitAll
    public void resetPassword(ResetPasswordDto resetPasswordDto) throws AppBaseException {
        int methodInvocationCounter = 0;
        boolean rollback;
        do {
            try {
                userManager.resetPassword(resetPasswordDto);
                rollback = userManager.isLastTransactionRollback();
            } catch (AppEJBTransactionRolledbackException ex) {
                logger.log(Level.WARNING, "Exception EJBTransactionRolledback");
                rollback = true;
            } finally {
                if (methodInvocationCounter > 0)
                    logger.log(Level.WARNING, "Transaction repeated " + methodInvocationCounter + " times");
                methodInvocationCounter++;
            }
        } while (rollback && methodInvocationCounter < METHOD_INVOCATION_LIMIT);

        if (methodInvocationCounter == METHOD_INVOCATION_LIMIT) {
            throw RepeatedRollBackException.createRepeatedRollBackException();
        }
    }
}
