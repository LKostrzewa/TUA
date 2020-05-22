package pl.lodz.p.it.ssbd2020.ssbd02.exceptions;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.User;

/**
 * Wyjątek aplikacyjny występujący w przypadku wprowadzenia takiego samego
 * nowego hasła jak stare hasło podczas resetowania hasła.
 */
public class PasswordIdenticalException extends AppBaseException {

    public static final String PASSWORD_IDENTICAL_KEY = "exception.passwordIdentical";

    private User user;

    public PasswordIdenticalException(String message) {
        super(message);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Statyczna metoda do tworzenia wyjątku
     *
     * @param user obiekt user, który spowodował wystąpienie wyjątku
     * @return obiekt wyjątku z odpowiednimi danymi
     */
    public static PasswordIdenticalException createPasswordIdenticalException(User user) {
        PasswordIdenticalException pie = new PasswordIdenticalException(PASSWORD_IDENTICAL_KEY);
        pie.setUser(user);
        return pie;
    }
}
