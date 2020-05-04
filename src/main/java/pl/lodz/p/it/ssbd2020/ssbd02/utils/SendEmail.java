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

    public void sendEmail(String activationlink, String userName, String userEmail) {
        PropertyReader propertyReader= new PropertyReader();
        String from = propertyReader.getProperty("config","from");
        String username = propertyReader.getProperty("config","username");
        String password = propertyReader.getProperty("config","password");
        String host = propertyReader.getProperty("config","host");

        String emailSubject= propertyReader.getProperty("emailMessages","activationSubject",userName);
        String emailText= propertyReader.getProperty("emailMessages","activationText",activationlink);

        Properties properties = new Properties();
        properties.put("mail.smtp.auth",true);
        properties.put("mail.smtp.ssl.enable", true);
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", 465);


        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username,password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
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


    public void sendEmailNotificationAboutNewAdminAuthentication(String email, String clientIpAddress) {
        PropertyReader propertyReader= new PropertyReader();
        String from = propertyReader.getProperty("config","from");
        String username = propertyReader.getProperty("config","username");
        String password = propertyReader.getProperty("config","password");
        String host = propertyReader.getProperty("config","host");

        String emailSubject= propertyReader.getProperty("emailMessages","adminAuthenticationSubject");
        String emailText= propertyReader.getProperty("emailMessages","adminAuthenticationText",clientIpAddress);

        Properties properties = new Properties();
        properties.put("mail.smtp.auth",true);
        properties.put("mail.smtp.ssl.enable", true);
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", 465);

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username,password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            InternetAddress[] addresses = {new InternetAddress(email)};
            message.setRecipients(Message.RecipientType.TO, addresses);
            message.setSubject(emailSubject);
            message.setText(emailText, "UTF-8", "html");
            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}