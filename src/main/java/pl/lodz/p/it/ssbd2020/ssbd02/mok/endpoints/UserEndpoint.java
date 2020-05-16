package pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints;


import org.primefaces.model.FilterMeta;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.User;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.*;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.managers.UserManager;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.ObjectMapperUtils;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Stateful
@LocalBean
@Interceptors(LoggerInterceptor.class)
public class UserEndpoint implements Serializable {
    Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    @Inject
    private UserManager userManager;
    private User userEditEntity;

    // tutaj permitAll
    public void registerNewUser(AddUserDto userDTO) throws AppBaseException {
        try {
            do {
                User user = new User(userDTO.getLogin(), userDTO.getPassword(),
                        userDTO.getEmail(), userDTO.getFirstName(), userDTO.getLastName(),
                        userDTO.getPhoneNumber());
                userManager.registerNewUser(user);
            } while (userManager.isLastTransactionRollback());
        } catch (EJBTransactionRolledbackException ex) {
            registerNewUser(userDTO);
        }
    }

    /**
     * Metoda, służy do dodawania nowych użytkowników do bazy danych przez administratora
     *
     * @param userDTO obiekt DTO z danymi nowego użytkownika.
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("addNewUser")
    public void addNewUser(AddUserDto userDTO) throws AppBaseException {
        User user = new User(userDTO.getLogin(), userDTO.getPassword(), userDTO.getEmail(), userDTO.getFirstName(), userDTO.getLastName(), userDTO.getPhoneNumber());
        userManager.addNewUser(user);
    }

    /**
     * Metoda, która pobiera z bazy listę obiektów.
     *
     * @return lista obiektów
     */
    @RolesAllowed("getUserReport")
    public List<UserReportDto> getUserReport() {
        return ObjectMapperUtils.mapAll(userManager.getAll(), UserReportDto.class);
    }

    /**
     * Metoda, która pobiera użytkownika do edycji przez administratora po identyfikatorze użytkownika
     *
     * @param userId identyfikator użytkownika.
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("getEditUserDtoById")
    public EditUserDto getEditUserDtoById(Long userId) throws AppBaseException{
        this.userEditEntity = userManager.getUserById(userId);
        return ObjectMapperUtils.map(this.userEditEntity, EditUserDto.class);
    }

    /**
     * Metoda, która pobiera użytkownika do edycji własnych danych po jego loginie
     *
     * @param userLogin login użytkownika.
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("getEditUserDtoByLogin")
    public EditUserDto getEditUserDtoByLogin(String userLogin) throws AppBaseException {
        this.userEditEntity = userManager.getUserByLogin(userLogin);
        return ObjectMapperUtils.map(this.userEditEntity, EditUserDto.class);
    }

    public UserDetailsDto getUserDetailsDtoById(Long userId) throws AppBaseException{
        return ObjectMapperUtils.map(userManager.getUserById(userId), UserDetailsDto.class);
    }

    /**
     * Metoda, która zwraca login dto o podanym loginie.
     *
     * @param userLogin login użytkownika.
     * @return user login dot
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("getLoginDtoByLogin")
    public UserLoginDto getLoginDtoByLogin(String userLogin) throws AppBaseException {
        return ObjectMapperUtils.map(userManager.getUserByLogin(userLogin), UserLoginDto.class);
    }

    /**
     * Metoda, która zapisuje wprowadzone przez administratora zmiany w danych konta użytkownika
     *
     * @param editUserDto  obiekt przechowujący dane wprowadzone w formularzu
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("editUser")
    public void editUser(EditUserDto editUserDto) throws AppBaseException {
        userEditEntity.setFirstName(editUserDto.getFirstName());
        userEditEntity.setLastName(editUserDto.getLastName());
        userEditEntity.setPhoneNumber(editUserDto.getPhoneNumber());
        userManager.editUser(this.userEditEntity);
    }

    /**
     * Metoda, która zapisuje wprowadzone zmiany w danych swojego konta
     *
     * @param editUserDto  obiekt przechowujący dane wprowadzone w formularzu
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("editOwnData")
    public void editOwnData(EditUserDto editUserDto) throws AppBaseException {
        userEditEntity.setFirstName(editUserDto.getFirstName());
        userEditEntity.setLastName(editUserDto.getLastName());
        userEditEntity.setPhoneNumber(editUserDto.getPhoneNumber());
        userManager.editUser(this.userEditEntity);
    }

    /**
     * Metoda wykorzystywana do zmiany hasła innego użytkownika zgodnie z przekazanymi parametrami.
     *
     * @param changePasswordDto obiekt przechowujący dane wprowadzone w formularzu
     * @param userId            id użytkownika, którego hasło ulegnie modyfikacji
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("changeUserPassword")
    public void changeUserPassword(ChangePasswordDto changePasswordDto, Long userId) throws AppBaseException {
        User user = ObjectMapperUtils.map(changePasswordDto, User.class);
        userManager.changeUserPassword(user, userId);
    }

    /**
     * Metoda wykorzystywana do zmiany własnego hasła zgodnie z przekazanymi parametrami.
     *
     * @param changeOwnPasswordDto obiekt przechowujący dane wprowadzone w formularzu
     * @param userLogin            login użytkownika, którego hasło ulegnie modyfikacji
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("changeOwnPassword")
    public void changeOwnPassword(ChangeOwnPasswordDto changeOwnPasswordDto, String userLogin) throws AppBaseException {
        User user = ObjectMapperUtils.map(changeOwnPasswordDto, User.class);
        userManager.changeOwnPassword(user, userLogin, changeOwnPasswordDto.getOldPassword());
    }

    /**
     * Metoda, która blokuje konto o podanym id.
     *
     * @param userId id użytkownika.
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("lockAccount")
    public void lockAccount(Long userId) throws AppBaseException {
        userManager.lockAccount(userId);
    }

    public void unlockAccount(Long userId) throws AppBaseException {
        userManager.unlockAccount(userId);
    }

    //TODO wyrzucić jeżeli okaże się niepotrzebna na pewno
    public UserDetailsDto getOwnDetailsDtoByLogin(String userLogin) throws AppBaseException {
        return ObjectMapperUtils.map(userManager.getUserByLogin(userLogin), UserDetailsDto.class);
    }

    // permit all??, zmienic nazwe na activeAccount
    public void confirmActivationCode(String code) throws AppBaseException {
        userManager.confirmActivationCode(code);
    }

    /**
     * Metoda, która zapisuje informacje o poprawnym uwierzytelnianiu( adres ip użytkownika, data logowania).
     *
     * @param login           login użytkownika
     * @param clientIpAddress adres ip użytkownika
     * @param date            data zalogowania użytkownika
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("saveSuccessAuthenticate")
    public void saveSuccessAuthenticate(String login, String clientIpAddress, Date date) throws AppBaseException {
        userManager.saveSuccessAuthenticate(login, clientIpAddress, date);
    }

    /**
     * Metoda, która zapisuje informacje o niepoprawnym uwierzytelnianiu( adres ip użytkownika, data logowania).
     *
     * @param login login użytkownika
     * @param date  data zalogowania użytkownika
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @PermitAll
    public void saveFailureAuthenticate(String login, Date date) throws AppBaseException {
        userManager.saveFailureAuthenticate(login, date);
    }


    /**
     * Metoda, która pobiera z bazy liczbę filtrowanych obiektów.
     *
     * @param filters para filtrowanych pól i ich wartości
     * @return liczba obiektów poddanych filtrowaniu
     */
    @RolesAllowed("getFilteredRowCount")
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
    @RolesAllowed("getResultList")
    public List<ListUsersDto> getResultList(int first, int pageSize, Map<String, FilterMeta> filters) {
        return ObjectMapperUtils.mapAll(userManager.getResultList(first, pageSize, filters), ListUsersDto.class);
    }

    /**
     * Metoda, która na podany email wysyła wiadomość z linkiem, pod którym można zresetować zapomniane hasło
     *
     * @param email adres email
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @PermitAll
    public void sendResetPasswordEmail(String email) throws AppBaseException {
        userManager.sendResetPasswordEmail(email);
    }

    /**
     * Metoda, która zmienia zapomniane hasło
     *
     * @param resetPasswordCode kod do resetowania hasła wysłany na adres email
     * @param password nowo wprowadzone hasło
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @PermitAll
    public void resetPassword(String resetPasswordCode, String password) throws AppBaseException {
        userManager.resetPassword(resetPasswordCode, password);
    }
}