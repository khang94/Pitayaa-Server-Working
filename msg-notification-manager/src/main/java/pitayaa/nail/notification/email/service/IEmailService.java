package pitayaa.nail.notification.email.service;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Properties;
import java.util.UUID;

import pitayaa.nail.domain.notification.email.EmailModel;

public interface IEmailService {

	EmailModel initModelEmail() throws Exception;

	EmailModel createEmail(EmailModel emailModel) throws Exception;

	EmailModel findOne(UUID uid) throws Exception;

	boolean sendEmailAPI(EmailModel emailForm, Properties properties,
			String templateContentHTML);

	String getValueProperties(String propertiesName);

	EmailModel sendEmail(EmailModel emailModel) throws IOException;

	Properties getPropertiesMailConfig();

	String readFile(String path, Charset encoding) throws IOException;

	String findTemplateInClasspath(String templateId, String pathFolderTemplate)
			throws IOException;

	String getMailProperties(String propertiesName);

}
