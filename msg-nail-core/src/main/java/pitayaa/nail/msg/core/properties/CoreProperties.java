package pitayaa.nail.msg.core.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@PropertySource("classpath:application.yml")
@ConfigurationProperties
@Data
public class CoreProperties {

	@Value("${services.notification.report}")
	String reportAPI;
}
