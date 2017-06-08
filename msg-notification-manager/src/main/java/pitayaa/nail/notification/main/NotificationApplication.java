package pitayaa.nail.notification.main;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import org.quartz.SchedulerException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import pitayaa.nail.notification.scheduler.QuartJob;

@EnableAutoConfiguration()
@SpringBootApplication
@ComponentScan(basePackages = { "pitayaa.nail" })
@EntityScan(basePackages = { "pitayaa.nail.domain.notification" })
@EnableJpaRepositories(basePackages = { "pitayaa.nail" })
public class NotificationApplication {

	public static void main(String[] args)
			throws FileNotFoundException, UnsupportedEncodingException, SchedulerException {

		SpringApplication.run(NotificationApplication.class, args);
		QuartJob.activeJob();

	}
}
