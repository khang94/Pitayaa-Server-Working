package pitayaa.nail.notification.email.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import pitayaa.nail.domain.notification.email.EmailModel;
import pitayaa.nail.domain.notification.email.elements.Attachment;
import pitayaa.nail.notification.common.NotificationHelper;
import pitayaa.nail.notification.email.config.EmailConstant;
import pitayaa.nail.notification.email.repository.EmailRepository;

@Service
public class EmailServiceImpl implements IEmailService {

	@Autowired
	NotificationHelper notificationHelper;

	@Autowired
	EmailRepository emailRepository;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(EmailServiceImpl.class);

	@Override
	public EmailModel initModelEmail() throws Exception {
		EmailModel emailModel = (EmailModel) notificationHelper
				.createModelStructure(new EmailModel());

		return emailModel;
	}

	@Override
	public EmailModel createEmail(EmailModel emailModel) throws Exception {

		// Set current date
		//Date currentDate = notificationHelper.getCurrentTimeGMT();
		//emailModel.getMeta().setCreatedDate(currentDate);
		//emailModel.getMeta().setUpdatedDate(currentDate);

		// Save POJO
		return emailRepository.save(emailModel);
	}



	@Override
	public EmailModel findOne(UUID uid) throws Exception {
		LOGGER.info("Find email with ID [" + uid + "]");
		
		return emailRepository.findOne(uid);
	}

	@Override
	public Properties getPropertiesMailConfig() {

		// Get config properties for Email
		String username = this.getMailProperties(EmailConstant.USERNAME);
		String password = this.getMailProperties(EmailConstant.PASSWORD);
		String auth = this.getMailProperties(EmailConstant.SMTP_AUTH);
		String enableTLS = this.getMailProperties(EmailConstant.STARTTLS);
		String host = this.getMailProperties(EmailConstant.SMTP_HOST);
		String port = this.getMailProperties(EmailConstant.SMTP_PORT);
		String debug = this.getMailProperties(EmailConstant.DEBUG);
		LOGGER.info("Getting properties config for mail service : host = ["
				+ host + "], \n" + "port = [" + port + "] with username = ["
				+ username + "]");

		// Assign value to properties
		Properties properties = new Properties();
		properties.setProperty(EmailConstant.SMTP_AUTH, auth);
		properties.setProperty(EmailConstant.STARTTLS, enableTLS);
		properties.setProperty(EmailConstant.DEBUG, debug);
		properties.setProperty(EmailConstant.SMTP_HOST, host);
		properties.setProperty(EmailConstant.SMTP_PORT, port);
		properties.setProperty(EmailConstant.USERNAME, username);
		properties.setProperty(EmailConstant.PASSWORD, password);
		LOGGER.info("Get properties config successfully !");

		return properties;

	}

	@Override
	public boolean sendEmailAPI(EmailModel emailForm, Properties properties,
			String templateContentHTML) {

		// Flag to check whether API work or not
		boolean flag = false;

		// Set properties username , password for JavaMailSender
		JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
		senderImpl.setHost(properties.getProperty(EmailConstant.SMTP_HOST));
		senderImpl.setPort(Integer.parseInt(properties
				.getProperty(EmailConstant.SMTP_PORT)));
		senderImpl.setUsername(properties.getProperty(EmailConstant.USERNAME));
		senderImpl.setPassword(properties.getProperty(EmailConstant.PASSWORD));
		senderImpl.setJavaMailProperties(properties);
		LOGGER.info("Add properties to sender success !");

		// Get properties
		// final List<Attachment> attachments = emailForm.getAttachments();
		final List<Attachment> attachments = new ArrayList<Attachment>();
		final String subject = emailForm.getHeaderEmail().getSubject();

		final String fromName = emailForm.getHeaderEmail().getFrom().getKey();
		final String from = emailForm.getHeaderEmail().getFrom().getValue();

		final String replyToName = emailForm.getHeaderEmail().getReplyTo()
				.getKey();
		final String replyTo = emailForm.getHeaderEmail().getReplyTo()
				.getValue();

		final String[] to = notificationHelper.getData(emailForm
				.getHeaderEmail().getToReceiver());
		final String[] cc = notificationHelper.getData(emailForm
				.getHeaderEmail().getCc());
		final String[] bcc = notificationHelper.getData(emailForm
				.getHeaderEmail().getBcc());

		final String content = templateContentHTML;

		// Check is it text or template HTML
		/*
		 * if(templateContentHTML != null){ contentTemplate =
		 * templateContentHTML; }
		 */

		// Send mail
		MimeMessagePreparator preparator = new MimeMessagePreparator() {

			public void prepare(MimeMessage mimeMessage) throws Exception {
				// use the true flag to indicate you need a multipart message
				MimeMessageHelper message = null;
				if (attachments != null && !attachments.isEmpty()) {
					LOGGER.info("There are attachments at this email ");
					message = new MimeMessageHelper(mimeMessage, true);
					for (Attachment attachment : attachments) {
						if (attachment.getData() != null) {
							InputStream stream = new ByteArrayInputStream(
									attachment.getData());

							LOGGER.info("Attachment detail : Name = ["
									+ attachment.getFileName() + "] , \n"
									+ "Content type = ["
									+ attachment.getContentType() + "] , \n"
									+ "Content = [" + stream.toString());

							message.addAttachment(
									attachment.getFileName(),
									new ByteArrayDataSource(stream, attachment
											.getContentType()));

						}
					}
				} else {
					message = new MimeMessageHelper(mimeMessage);
				}

				// content

				message.setText(content, true);
				LOGGER.info("Set text = [" + content + "] success !");

				message.setSubject(subject);
				LOGGER.info("Set text = [" + subject + "] success !");

				message.setFrom(from, fromName);
				LOGGER.info("From : [" + from + "] with Name : [" + fromName
						+ "]");

				message.setReplyTo(replyTo, replyToName);
				LOGGER.info("Reply to : [" + replyTo + "] with Name : ["
						+ replyToName + "]");

				message.setTo(to);
				LOGGER.info("To email : [" + to + "]");

				if (cc != null && cc.length > 0) {
					message.setCc(cc);
				}
				if (bcc != null && bcc.length > 0) {
					message.setBcc(bcc);
				}

			}
		};
		senderImpl.send(preparator);
		flag = true;
		return flag;
	}

	@Override
	public EmailModel sendEmail(EmailModel emailModel) throws IOException {

		// Get Properties Config
		Properties properties = this.getPropertiesMailConfig();

		// Get path & content of template Folder
		String templateId = emailModel.getMeta().getTemplateId();
		String templateContentHTML = (templateId != null) ? this
				.findTemplateInClasspath(templateId,
						EmailConstant.TEMPLATE_EMAIL_PATH) : emailModel
				.getHeaderEmail().getBodyText();

		// Call API Send Email
		boolean resultSendmail = this.sendEmailAPI(emailModel, properties,
				templateContentHTML);

		// Update Status After Sending & setting date
		Date currentDate = notificationHelper.getCurrentTimeGMT();
		emailModel.getMeta().setUpdatedDate(currentDate);
		if (emailModel.getMeta().getCreatedDate() == null) {
			emailModel.getMeta().setCreatedDate(currentDate);
		}
		if (resultSendmail) {
			emailModel.getMeta().setDeliveredDate(currentDate);
			emailModel.getMeta()
					.setStatus(EmailConstant.STATUS_EMAIL_DELIVERED);
		} else {
			emailModel.getMeta().setStatus(
					EmailConstant.STATUS_EMAIL_FAILED_DELIVER);
		}

		// Update Email In Repository
		emailModel = emailRepository.save(emailModel);

		return emailModel;
	}

	@Override
	public String getMailProperties(String propertiesName) {

		Properties prop = new Properties();
		InputStream input = null;
		String propertiesValue = null;
		try {
			input = EmailServiceImpl.class.getClassLoader()
					.getResourceAsStream(EmailConstant.PATH_MAIL_CONFIG);
			prop.load(input);
			propertiesValue = prop.getProperty(propertiesName);

		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return propertiesValue;
	}

	@Override
	public String findTemplateInClasspath(String templateId,
			String pathFolderTemplate) throws IOException {

		String path = pathFolderTemplate + EmailConstant.SLASH + templateId
				+ EmailConstant.TEMPLATE_FILE_EXTENSION;
		InputStream input = EmailServiceImpl.class.getClassLoader()
				.getResourceAsStream(path);

		String content = IOUtils.toString(input, StandardCharsets.UTF_8);

		return content;
	}

	@Override
	public String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}

	@Override
	public String getValueProperties(String propertiesName) {
		// TODO Auto-generated method stub
		return null;
	}

}
