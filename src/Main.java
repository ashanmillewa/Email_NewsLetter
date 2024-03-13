//import java.util.Properties;
//import javax.mail.*;
//import javax.mail.internet.*;
//import java.nio.file.*;
//
//public class Main {
//    public static void main(String[] args) {
//
//        // Recipient's email
//        String to = "ashanmillewa@gmail.com";
////        String to = "lahiru@cdl.lk";
//
//
//        // Sender's email
//        String from = "ashandts123@outlook.com";
//
//
//        String host = "smtp-mail.outlook.com";
//        Properties props = System.getProperties();
//
//        // Setup mail server
//        props.setProperty("mail.smtp.host", host);
//        props.setProperty("mail.smtp.port", "587");
//
//        // Enable authentication
//        props.setProperty("mail.smtp.auth", "true");
//
//        // Enable TLS
//        props.setProperty("mail.smtp.starttls.enable", "true");
//
//        // Specify TLS protocols individually
//        props.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
//        props.setProperty("mail.smtp.ssl.protocols", "TLSv1.3");
//
//        Session session = Session.getDefaultInstance(props, new Authenticator() {
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication("ashandts123@outlook.com", "ASHANtestDTS123");
//            }
//        });
//
//        try {
//            MimeMessage message = new MimeMessage(session);
//
//            message.setFrom(new InternetAddress(from));
//            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
//
//            // Set Subject
//            message.setSubject("Newsletter Subject");
//
//            // Read HTML file content
//            String htmlContent = new String(Files.readAllBytes(Paths.get("C:/Users/ashan/Downloads/cdl/images.html")));
//
//            // Set the actual HTML message
//            message.setContent(htmlContent, "text/html");
//
//            // Send the message
//            Transport.send(message);
//            System.out.println("Message sent successfully...");
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
//}

import java.sql.*;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import java.nio.file.*;

public class Main {
    public static void main(String[] args) {

        // Sender's email
        String from = "ashandts123@outlook.com";

        String host = "smtp-mail.outlook.com";
        Properties props = System.getProperties();

        // Setup mail server
        props.setProperty("mail.smtp.host", host);
        props.setProperty("mail.smtp.port", "587");

        // Enable authentication
        props.setProperty("mail.smtp.auth", "true");

        // Enable TLS
        props.setProperty("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
        props.setProperty("mail.smtp.ssl.protocols", "TLSv1.3");

        Session session = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                //Set the Sender's email and password
                return new PasswordAuthentication("ashandts123@outlook.com", "ASHANtestDTS123");
            }
        });

        // Database connection
        String jdbcUrl = "jdbc:mysql://localhost:3306/email_db";
        String dbUsername = "root";
        String dbPassword = "11AIM2000";

        try {
            // Establish database connection
            Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);

            // Query to retrieve email addresses from the database
            String query = "SELECT email FROM email_addresses";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                // Recipient's email
                String to = resultSet.getString("email");

                try {
                    MimeMessage message = new MimeMessage(session);

                    message.setFrom(new InternetAddress(from));
                    message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

                    // Set Subject
                    message.setSubject("Newsletter Subject");

                    // Read HTML file
                    String htmlContent = new String(Files.readAllBytes(Paths.get("D:/MyProjects/Email_NewsLetter/newsletter.html")));

                    // Set the actual HTML message
                    message.setContent(htmlContent, "text/html");


                    // Send the message
                    Transport.send(message);
                    System.out.println("Message sent successfully to " + to);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
