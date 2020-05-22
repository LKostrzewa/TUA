package pl.lodz.p.it.ssbd2020.ssbd02.exceptions;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.User;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;

/**
 * Wyjątek aplikacyjny występujący w przypadku utraty ważności kodu do resetowania hasła
 */
public class ResetPasswordCodeExpiredException extends AppBaseException {

    public static final String RESET_CODE_EXPIRED_CONSTRAINT_KEY = "exception.codeExpired";

    private User user;

    public ResetPasswordCodeExpiredException(String message) {
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
    public static ResetPasswordCodeExpiredException createPasswordExceptionWithCodeExpiredConstraint(User user) {
        ResetPasswordCodeExpiredException rpce = new ResetPasswordCodeExpiredException(RESET_CODE_EXPIRED_CONSTRAINT_KEY);
        rpce.setUser(user);
        return rpce;
    }
}