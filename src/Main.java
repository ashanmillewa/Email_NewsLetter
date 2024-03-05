import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import java.nio.file.*;

public class Main {
    public static void main(String[] args) {

        // Recipient's email
        String to = "ashanmillewa@gmail.com";

        // Sender's email
        String from = "ashantest132@outlook.com";

        String host = "smtp-mail.outlook.com";
        Properties props = System.getProperties();

        // Setup mail server
        props.setProperty("mail.smtp.host", host);
        props.setProperty("mail.smtp.port", "587");

        // Enable authentication
        props.setProperty("mail.smtp.auth", "true");

        // Enable TLS
        props.setProperty("mail.smtp.starttls.enable", "true");

        // Specify TLS protocols individually
        props.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
        props.setProperty("mail.smtp.ssl.protocols", "TLSv1.3");

        Session session = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("ashantest132@outlook.com", "DTS123ashan");
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject
            message.setSubject("Newsletter Subject");

            // Read HTML file content
            String htmlContent = new String(Files.readAllBytes(Paths.get("C:/Users/ashan/Downloads/cdl/newsletter.html")));

            // Set the actual HTML message
            message.setContent(htmlContent, "text/html");

            // Send the message
            Transport.send(message);
            System.out.println("Message sent successfully...");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
