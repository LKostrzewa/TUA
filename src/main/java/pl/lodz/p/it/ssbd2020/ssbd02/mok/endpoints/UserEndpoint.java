package pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints;

import org.primefaces.model.FilterMeta;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.User;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.*;

import javax.annotation.security.PermitAll;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Interfejs Endpoint do operacji związanych z użytkownikami
 */
public interface UserEndpoint {
    /**
     * Metoda służąca do rejestracji użytkownika
     * @param userDTO obiekt DTO z danymi rejestrowanego użytkownika
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    void registerNewUser(AddUserDto userDTO) throws AppBaseException;

    /**
     * Metoda, służy do dodawania nowych użytkowników do bazy danych przez administratora
     *
     * @param userDTO obiekt DTO z danymi nowego użytkownika.
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    void addNewUser(AddUserDto userDTO) throws AppBaseException;

    /**
     * Metoda, która pobiera z bazy listę obiektów.
     *
     * @return lista obiektów
     */
    List<UserReportDto> getUserReport();

    /**
     * Metoda, która pobiera użytkownika do edycji przez administratora po identyfikatorze użytkownika
     *
     * @param userId identyfikator użytkownika.
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    EditUserDto getEditUserDtoById(Long userId) throws AppBaseException;

    /**
     * Metoda, która pobiera użytkownika do edycji własnych danych po jego loginie
     *
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    EditUserDto getEditUserDtoByLogin() throws AppBaseException;

    /**
     * Metoda, która zwraca login dto o podanym loginie.
     *
     * @return user login dot
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    UserLoginDto getLoginDtoByLogin() throws AppBaseException;

    /**
     * Metoda, która zapisuje wprowadzone przez administratora zmiany w danych konta użytkownika
     *
     * @param editUserDto  obiekt przechowujący dane wprowadzone w formularzu
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    void editUser(EditUserDto editUserDto) throws AppBaseException;

    /**
     * Metoda, która zapisuje wprowadzone zmiany w danych swojego konta
     *
     * @param editUserDto  obiekt przechowujący dane wprowadzone w formularzu
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    void editOwnData(EditUserDto editUserDto) throws AppBaseException;

    /**
     * Metoda wykorzystywana do zmiany hasła innego użytkownika zgodnie z przekazanymi parametrami.
     *
     * @param changePasswordDto obiekt przechowujący dane wprowadzone w formularzu
     * @param userId            id użytkownika, którego hasło ulegnie modyfikacji
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    void changeUserPassword(ChangePasswordDto changePasswordDto, Long userId) throws AppBaseException;

    /**
     * Metoda wykorzystywana do zmiany własnego hasła zgodnie z przekazanymi parametrami.
     *
     * @param changeOwnPasswordDto obiekt przechowujący dane wprowadzone w formularzu
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    void changeOwnPassword(ChangeOwnPasswordDto changeOwnPasswordDto) throws AppBaseException;

    /**
     * Metoda, która blokuje konto o podanym id.
     *
     * @param userId id użytkownika.
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    void lockAccount(Long userId) throws AppBaseException;

    /**
     * Metoda, która odblokowywuje konto o podanym id.
     *
     * @param userId id użytkownika.
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    void unlockAccount(Long userId) throws AppBaseException;

    /**
     * Metoda która aktywuje dane konto po kliknięciu w link aktywacyjny
     * @param code kod aktywacyjny użytkownika
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    void activateAccount(String code) throws AppBaseException;

    /**
     * Metoda, która zapisuje informacje o poprawnym uwierzytelnianiu( adres ip użytkownika, data logowania).
     *
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
     void saveSuccessAuthenticate() throws AppBaseException;

    /**
     * Metoda, która zapisuje informacje o niepoprawnym uwierzytelnianiu( adres ip użytkownika, data logowania).
     *
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
     void saveFailureAuthenticate() throws AppBaseException;

    /**
     * Metoda, która pobiera z bazy liczbę filtrowanych obiektów.
     *
     * @param filters para filtrowanych pól i ich wartości
     * @return liczba obiektów poddanych filtrowaniu
     */
    int getFilteredRowCount(Map<String, FilterMeta> filters);

    /**
     * Metoda, która pobiera z bazy listę filtrowanych obiektów.
     *
     * @param first    numer pierwszego obiektu
     * @param pageSize rozmiar strony
     * @param filters  para filtrowanych pól i ich wartości
     * @return lista filtrowanych obiektów
     */
    List<ListUsersDto> getResultList(int first, int pageSize, Map<String, FilterMeta> filters);

    /**
     * Metoda, która na podany email wysyła wiadomość z linkiem, pod którym można zresetować zapomniane hasło
     *
     * @param email adres email
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    void sendResetPasswordEmail(String email) throws AppBaseException;

    /**
     * Metoda, która zmienia zapomniane hasło
     *
     * @param resetPasswordDto  obiekt przechowujący dane wprowadzone w formularzu
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    void resetPassword(ResetPasswordDto resetPasswordDto) throws AppBaseException;

}
