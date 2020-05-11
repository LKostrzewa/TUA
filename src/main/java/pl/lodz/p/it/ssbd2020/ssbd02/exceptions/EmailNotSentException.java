package pl.lodz.p.it.ssbd2020.ssbd02.exceptions;

public class EmailNotSentException extends AppBaseException {
    public static final String EMAIL_NOT_SENT_KEY = "exception.emailNotSent";

    public EmailNotSentException(String message) {
        super(message);
    }
    public EmailNotSentException(String message, Throwable cause) {
        super(message, cause);
    }

    public static EmailNotSentException createEmailNotSentException(Throwable cause) {
        return new EmailNotSentException(EMAIL_NOT_SENT_KEY, cause);
    }
}
