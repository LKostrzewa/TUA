package pl.lodz.p.it.ssbd2020.ssbd02.utils;


import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.EmailNotSentException;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


public class SendEmail {
    private final Integer port = 465;

    public void sendActivationEmail(String activationlink, String userName, String userEmail) throws AppBaseException {
        PropertyReader propertyReader = new PropertyReader();
        String emailSubject = propertyReader.getProperty("emailMessages", "activationSubject", userName);
        String emailText = propertyReader.getProperty("emailMessages", "activationText", activationlink);
        emailBody(userEmail, emailSubject, emailText);
    }


    public void activationInfoEmail(String userEmail) throws AppBaseException {
        PropertyReader propertyReader = new PropertyReader();
        String emailSubject = propertyReader.getProperty("emailMessages", "afterActivationSubject");
        String emailText = propertyReader.getProperty("emailMessages", "afterActivationText");
        emailBody(userEmail, emailSubject, emailText);
    }

    public void lockInfoEmail(String userEmail) throws AppBaseException {
        PropertyReader propertyReader = new PropertyReader();
        String emailSubject = propertyReader.getProperty("emailMessages", "lockInfoSubject");
        String emailText = propertyReader.getProperty("emailMessages", "lockInfoText");
        emailBody(userEmail, emailSubject, emailText);
    }


    public void unlockInfoEmail(String userEmail) throws AppBaseException {
        PropertyReader propertyReader = new PropertyReader();
        String emailSubject = propertyReader.getProperty("emailMessages", "unlockInfoSubject");
        String emailText = propertyReader.getProperty("emailMessages", "unlockInfoText");
        emailBody(userEmail, emailSubject, emailText);
    }

    public void sendEmailNotificationAboutNewAdminAuthentication(String userEmail, String clientIpAddress) throws AppBaseException {
        PropertyReader propertyReader = new PropertyReader();
        String emailSubject = propertyReader.getProperty("emailMessages", "adminAuthenticationSubject");
        String emailText = propertyReader.getProperty("emailMessages", "adminAuthenticationText", clientIpAddress);
        emailBody(userEmail, emailSubject, emailText);
    }

    public void sendResetPasswordEmail(String userEmail, String resetPasswordCode) throws AppBaseException {
        PropertyReader propertyReader = new PropertyReader();
        String emailSubject = propertyReader.getProperty("emailMessages", "resetPasswordSubject");
        String emailText = propertyReader.getProperty("emailMessages", "resetPasswordText", resetPasswordCode);
        emailBody(userEmail, emailSubject, emailText);
    }

    private Session emailSettings() {
        Properties properties = new Properties();
        PropertyReader propertyReader = new PropertyReader();
        String host = propertyReader.getProperty("config", "host");
        String username = propertyReader.getProperty("config", "username");
        String password = propertyReader.getProperty("config", "password");
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