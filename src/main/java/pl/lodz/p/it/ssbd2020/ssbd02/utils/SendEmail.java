package pl.lodz.p.it.ssbd2020.ssbd02.utils;


import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Stateless
@LocalBean
public class SendEmail {


    String from = "ssbd202002@gmail.com";
    String username = "ssbd202002@gmail.com";
    String password = "SSBD202002";
    String host = "smtp.gmail.com";


    public void sendEmail(String activationlink, String to) {
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
            InternetAddress[] addresses = {new InternetAddress(to)};
            message.setRecipients(Message.RecipientType.TO, addresses);
            message.setSubject("Jachtpol");
            message.setText(activationlink, "UTF-8", "html");
            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }





}