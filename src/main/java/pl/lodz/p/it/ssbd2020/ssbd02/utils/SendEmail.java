package pl.lodz.p.it.ssbd2020.ssbd02.utils;


import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.EmailNotSentException;

import javax.annotation.security.PermitAll;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Klasa przeznaczona do wysyłania emaili
 */
public class SendEmail {

    /**
     * Metoda która wysyła maila do użytkownika z linkiem aktywacyjnym
     * @param activationlink link aktywacyjny użytkownika
     * @param userName nazwa użytkownika
     * @param userEmail email użytkownika
     * @throws AppBaseException bazowy wyjątek aplikacyjny
     */

    public void sendActivationEmail(String activationlink, String userName, String userEmail) throws AppBaseException {
        PropertyReader propertyReader = new PropertyReader();
        String emailSubject = propertyReader.getProperty("emailMessages", "activationSubject", userName);
        String emailText = propertyReader.getProperty("emailMessages", "activationText", activationlink);
        emailBody(userEmail, emailSubject, emailText);
    }

    /**
     * Metoda która wysyła maila do użytkownika i powiadamia go o aktywacji konta
     * @param userEmail email użytkownika
     * @throws AppBaseException bazowy wyjątek aplikacyjny
     */
    public void activationInfoEmail(String userEmail) throws AppBaseException {
        PropertyReader propertyReader = new PropertyReader();
        String emailSubject = propertyReader.getProperty("emailMessages", "afterActivationSubject");
        String emailText = propertyReader.getProperty("emailMessages", "afterActivationText");
        emailBody(userEmail, emailSubject, emailText);
    }
    /**
     * Metoda, która wysyła maila do użytkownika, powiadamiająca go o zablokowaniu konta.
     *
     * @param userEmail email użytkownika
     */
    public void lockInfoEmail(String userEmail) throws AppBaseException {
        PropertyReader propertyReader = new PropertyReader();
        String emailSubject = propertyReader.getProperty("emailMessages", "lockInfoSubject");
        String emailText = propertyReader.getProperty("emailMessages", "lockInfoText");
        emailBody(userEmail, emailSubject, emailText);
    }

    /**
     * Metoda, która wysyła maila do użytkownika, powiadamiająca go o odblokowaniu konta.
     *
     * @param userEmail email użytkownika
     */
    public void unlockInfoEmail(String userEmail) throws AppBaseException {
        PropertyReader propertyReader = new PropertyReader();
        String emailSubject = propertyReader.getProperty("emailMessages", "unlockInfoSubject");
        String emailText = propertyReader.getProperty("emailMessages", "unlockInfoText");
        emailBody(userEmail, emailSubject, emailText);
    }

    /**
     * Metoda, która wysyła maila do użytkownika z poziomem dostępu administrator, powiadamiająca go o zalogowaniu
     *
     * @param userEmail email użytkownika
     * @param clientIpAddress adres ip użytkownika
     */
    public void sendEmailNotificationAboutNewAdminAuthentication(String userEmail, String clientIpAddress) throws AppBaseException{
        PropertyReader propertyReader= new PropertyReader();
        String emailSubject= propertyReader.getProperty("emailMessages","adminAuthenticationSubject");
        String emailText= propertyReader.getProperty("emailMessages","adminAuthenticationText",clientIpAddress);
        emailBody(userEmail, emailSubject, emailText);
    }

    /**
     * Metoda, która wysyła maila do użytkownika z linkiem do resetowania hasła
     *
     * @param userEmail email użytkownika
     * @param resetPasswordCode kod do resetowania hasła
     */
    public void sendResetPasswordEmail(String userEmail, String resetPasswordCode) throws AppBaseException {
        PropertyReader propertyReader = new PropertyReader();
        String emailSubject = propertyReader.getProperty("emailMessages", "resetPasswordSubject");
        String emailText = propertyReader.getProperty("emailMessages", "resetPasswordText", resetPasswordCode);
        emailBody(userEmail, emailSubject, emailText);
    }

    /**
     * Metoda sluzaca do zainicjalizowania ustawien niezbędnych do łączenia sie serwerem smtp w danej sesji
     *
     * @return obiekt sesji
     */
    private Session emailSettings() {
        Properties properties = new Properties();
        PropertyReader propertyReader = new PropertyReader();
        String host = propertyReader.getProperty("config", "host");
        String username = propertyReader.getProperty("config", "username");
        String password = propertyReader.getProperty("config", "password");
        Integer port = Integer.parseInt(propertyReader.getProperty("config", "port"));
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.ssl.enable", true);
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);


        return Session.getInstance(properties, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

    }

    /**
     * Metoda, która wysyła przygotowane emaile
     *
     * @param userEmail email użytkownika
     * @param emailSubject temat wiadomości
     * @param emailText treść wiadomości
     */
    private void emailBody(String userEmail, String emailSubject, String emailText) throws AppBaseException {
        PropertyReader propertyReader = new PropertyReader();
        String from = propertyReader.getProperty("config", "from");
        try {
            MimeMessage message = new MimeMessage(emailSettings());
            message.setFrom(new InternetAddress(from));
            InternetAddress[] addresses = {new InternetAddress(userEmail)};
            message.setRecipients(Message.RecipientType.TO, addresses);
            message.setSubject(emailSubject);
            message.setText(emailText, "UTF-8", "html");
            Transport.send(message);

        } catch (MessagingException e) {
            throw EmailNotSentException.createEmailNotSentException(e);
        }
    }
}