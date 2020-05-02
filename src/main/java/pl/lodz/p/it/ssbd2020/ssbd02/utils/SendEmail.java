package pl.lodz.p.it.ssbd2020.ssbd02.utils;


import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Stateless
@LocalBean
public class SendEmail {
    private String from = "ssbd202002@gmail.com";
    private String username = "ssbd202002@gmail.com";
    private String password = "SSBD202002";
    private String host = "smtp.gmail.com";
    private final Integer port = 465;

    public void sendActivationEmail(String activationlink, String userName, String userEmail) {
        PropertyReader propertyReader = new PropertyReader();
        String emailSubject = propertyReader.getProperty("emailMessages", "activationSubject", userName);
        String emailText = propertyReader.getProperty("emailMessages", "activationText", activationlink);
        emailBody(userEmail, emailSubject, emailText);
    }


    public void activationInfoEmail(String userEmail) {
        PropertyReader propertyReader = new PropertyReader();
        String emailSubject = propertyReader.getProperty("emailMessages", "afterActivationSubject");
        String emailText = propertyReader.getProperty("emailMessages", "afterActivationText");
        emailBody(userEmail, emailSubject, emailText);
    }

    public void lockInfoEmail(String userEmail) {
        PropertyReader propertyReader = new PropertyReader();
        String emailSubject = propertyReader.getProperty("emailMessages", "lockInfoSubject");
        String emailText = propertyReader.getProperty("emailMessages", "lockInfoText");
        emailBody(userEmail, emailSubject, emailText);
    }


    public void unlockInfoEmail(String userEmail) {
        PropertyReader propertyReader = new PropertyReader();
        String emailSubject = propertyReader.getProperty("emailMessages", "unlockInfoSubject");
        String emailText = propertyReader.getProperty("emailMessages", "unlockInfoText");
        emailBody(userEmail, emailSubject, emailText);
    }


    private Session emailSettings() {
        Properties properties = new Properties();
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


    private void emailBody(String userEmail, String emailSubject, String emailText) {
        try {
            MimeMessage message = new MimeMessage(emailSettings());
            message.setFrom(new InternetAddress(from));
            InternetAddress[] addresses = {new InternetAddress(userEmail)};
            message.setRecipients(Message.RecipientType.TO, addresses);
            message.setSubject(emailSubject);
            message.setText(emailText, "UTF-8", "html");
            Transport.send(message);


        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }



}