package pitayaa.nail.msg.business.util.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MailUtil {
	String username ;
	String password ;
	String host ;
	Map<String, String> props;
	public MailUtil(String props){
		this.props=new HashMap<String, String>();
		String[] ps=props.split("[,;]+");
		for(String p:ps){
			String[] p1=p.split("=");
			if(p1.length==2){
				if(p1[0].equals("username")){
					username=p1[1];
				}
				else if(p1[0].equals("password")){
					password=p1[1];
				}else{
					this.props.put(p1[0].trim(), p1[1].trim());
				}
			}
		}
		
	}

	public MailUtil(String username, String password, String host) {
		this.username = username;
		this.password = password;
		this.host = host;
	}
	
	public void sendMail(String lstToEmail, String lstCcEmail, List<String> lstFilePath, 
			String tittle, String content) {
		Properties props = new Properties();
		
		 if(this.props!=null)
		{
			props.putAll(this.props);
		}
		else if ("gmail".equals(host)) {
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.socketFactory.port", "465");
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", "465");
		}
		else if ("viettel".equals(host)) {
			props.put("mail.smtp.host", "smtp.viettel.com.vn");
			props.put("mail.smtp.socketFactory.port", "465");
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", "465");
		}
		

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(lstToEmail));

			message.setRecipients(Message.RecipientType.CC,
					InternetAddress.parse(lstCcEmail));
			
			message.setSubject(tittle);
			
			BodyPart messageBodyPart = new MimeBodyPart();

	         // Fill the message
	         messageBodyPart.setText(content);
			// Create a multipar message
	         Multipart multipart = new MimeMultipart();

	         // Set text message part
	         multipart.addBodyPart(messageBodyPart);

	         // Part two is attachment
	         if (lstFilePath != null) {
	        	 for (String filePath: lstFilePath) {
	        		 messageBodyPart = new MimeBodyPart();
	    	         DataSource source = new FileDataSource(filePath);
	    	         messageBodyPart.setDataHandler(new DataHandler(source));
	    	         messageBodyPart.setFileName(source.getName());
	    	         
	    	         multipart.addBodyPart(messageBodyPart);
	        	 }
	         }
	        

	         // Send the complete message parts
	         message.setContent(multipart);

			Transport.send(message);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	
	/*public static void main(String[] args) throws BusinessException {
		MailUtil mu = new MailUtil("hung.viettel.vnm@gmail.com",
				"viettelvodich", "gmail");
		mu.sendMail("nmhung89@gmail.com", "", null, "Test mail viettel",
				"Khong co gi hot het");

		MailUtil mu = new MailUtil(Configuration.fromMail,
				Configuration.fromMailPassword, Configuration.fromMailHost);
		mu.sendMail(lstMail.toString(), Configuration.lstCcMail, lstFilePath,
				Configuration.mailTitle, msg);
	}*/
}
