package com.csc.gdn.integralpos.api.test;

import java.util.Properties;

import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestEmailService1 {
	private static final Logger LOGGER = LoggerFactory.getLogger(TestEmailService1.class);
    /*public static void main(String args[]) {
        final String SMTP_HOST = "20.203.7.13";
        final String SMTP_PORT = "25";
        final String GMAIL_USERNAME = "mail_server@local.com";
        final String GMAIL_PASSWORD = "123456789";

        System.out.println("Process Started");
        

        Properties prop = System.getProperties();
        prop.setProperty("mail.smtp.starttls.enable", "true");
        prop.setProperty("mail.smtp.host", SMTP_HOST);
        prop.setProperty("mail.smtp.user", GMAIL_USERNAME);
        prop.setProperty("mail.smtp.password", GMAIL_PASSWORD);
        prop.setProperty("mail.smtp.port", SMTP_PORT);
        prop.setProperty("mail.smtp.auth", "true");
        System.out.println("Props : " + prop);

        Session session = Session.getInstance(prop, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(GMAIL_USERNAME,
                        GMAIL_PASSWORD);
            }
        });

        System.out.println("Got Session : " + session);

        MimeMessage message = new MimeMessage(session);
        try {
            System.out.println("before sending");
            message.setFrom(new InternetAddress(GMAIL_USERNAME));
            message.addRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(GMAIL_USERNAME));
            message.setSubject("My First Email Attempt from Java");
            message.setText("Hi, This mail came from Java Application.");
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(GMAIL_USERNAME));
            Transport transport = session.getTransport("smtp");
            System.out.println("Got Transport" + transport);
            transport.connect(SMTP_HOST, GMAIL_USERNAME, GMAIL_PASSWORD);
            transport.sendMessage(message, message.getAllRecipients());
            System.out.println("message Object : " + message);
            System.out.println("Email Sent Successfully");
        } catch (AddressException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MessagingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }*/
	
/*	public static void main(String[] args) throws IOException{
		InputStream file = new FileInputStream(new File("E:\\log_manager.txt"));
		byte[] byteArray = IOUtils.toByteArray(file);
		String base64 = Base64.getEncoder().encodeToString(byteArray);
		System.out.println("String base 64 = " + base64);
	}*/
	
	 public static void main( String[] args ) throws Exception {
		 
			/*
			 * Session session = Session.getInstance(properties, new
			 * javax.mail.Authenticator() { protected PasswordAuthentication
			 * getPasswordAuthentication() { return new
			 * PasswordAuthentication(username, password); } });
			 * session.setDebug(true);
			 */
		 
		 LOGGER.info("fdsaghfjkasdhgjkfdghjkdfghfkdjghdfkgjdfhkgjsdfhkgdfhkgjdfsdkfjhsdkgjhdfkgjhdfgkjdfhkjh");
		 
	        final String SMTP_HOST = "20.203.7.13";
	        final String SMTP_PORT = "25";
	        final String GMAIL_USERNAME = "mail_server@local.com";
	        final String GMAIL_PASSWORD = "123456789";

	        System.out.println("Process Started");
	        

	        Properties prop = System.getProperties();
	        prop.setProperty("mail.smtp.starttls.enable", "true");
	        prop.setProperty("mail.smtp.host", SMTP_HOST);
	        prop.setProperty("mail.smtp.user", GMAIL_USERNAME);
	        prop.setProperty("mail.smtp.password", GMAIL_PASSWORD);
	        prop.setProperty("mail.smtp.port", SMTP_PORT);
	        prop.setProperty("mail.smtp.auth", "true");
	        System.out.println("Props : " + prop);

		 
		    Session mailSession = Session.getInstance(prop);
		    Transport transport = mailSession.getTransport();

		    String text = "Hello, World";
		    String html = "<h1>" + text + "</h1>";

		    MimeMessage message = new MimeMessage( mailSession );
		    Multipart multipart = new MimeMultipart( "alternative" );

		    MimeBodyPart textPart = new MimeBodyPart();
		    textPart.setText( text, "utf-8" );

		    MimeBodyPart htmlPart = new MimeBodyPart();
		    htmlPart.setContent( html, "text/html; charset=utf-8" );

		    multipart.addBodyPart( textPart );
		    multipart.addBodyPart( htmlPart );
		    message.setContent( multipart );

		    // Unexpected output.
		    System.out.println( "HTML = text/html : " + htmlPart.isMimeType( "text/html" ) );
		    System.out.println( "HTML Content Type: " + htmlPart.getContentType() );

		    // Required magic (violates principle of least astonishment).
		    message.saveChanges();

		    // Output now correct.    
		    System.out.println( "TEXT = text/plain: " + textPart.isMimeType( "text/plain" ) );
		    System.out.println( "HTML = text/html : " + htmlPart.isMimeType( "text/html" ) );
		    System.out.println( "HTML Content Type: " + htmlPart.getContentType() );
		    System.out.println( "HTML Data Handler: " + htmlPart.getDataHandler().getContentType() );
		  }
}