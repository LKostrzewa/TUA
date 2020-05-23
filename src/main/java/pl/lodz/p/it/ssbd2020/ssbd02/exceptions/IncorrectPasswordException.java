package pl.lodz.p.it.ssbd2020.ssbd02.exceptions;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.User;

/**
 * Wyjątek aplikacyjny występujący w przypadku wprowadzenia niepoprawnego
 * starego hasła podczas resetowania hasła.
 */
public class IncorrectPasswordException extends AppBaseException {

    public static final String INCORRECT_CONSTRAINT_KEY = "exception.incorrectPassword";

    private User user;

    public IncorrectPasswordException(String message) {
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
    public static IncorrectPasswordException createIncorrectPasswordException(User user) {
        IncorrectPasswordException ipe = new IncorrectPasswordException(INCORRECT_CONSTRAINT_KEY);
        ipe.setUser(user);
        return ipe;
    }
}
