package pitayaa.nail.notification.config;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import pitayaa.nail.notification.common.NotificationHelper;
import pitayaa.nail.notification.promotion.business.PromotionJobBus;
import pitayaa.nail.notification.promotion.business.PromotionJobBusImpl;
import pitayaa.nail.notification.scheduler.JobHelper;
import pitayaa.nail.notification.scheduler.RestTemplateHelper;
import pitayaa.nail.notification.sms.api.nexmo.SendSmsNexmo;
import pitayaa.nail.notification.sms.repository.SmsRepository;
import pitayaa.nail.notification.sms.service.ISmsService;
import pitayaa.nail.notification.sms.service.SmsServiceImpl;

@Component
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = { "pitayaa.nail" })
@PropertySource("classpath:application.yml")
public class BeanConfiguration {

	private static final String PROPERTY_NAME_DATABASE_DRIVER = "spring.datasource.driverClassName";
	private static final String PROPERTY_NAME_DATABASE_URL = "spring.datasource.url";
	private static final String PROPERTY_NAME_DATABASE_USERNAME = "spring.datasource.username";
	private static final String PROPERTY_NAME_DATABASE_PASSWORD = "spring.datasource.password";

	@Bean
	public NotificationHelper notificationHelper() {
		return new NotificationHelper();
	}

	@Bean
	public ISmsService smsService() {
		return new SmsServiceImpl();
	}

	@Bean
	public SmsRepository smsRepository() {
		return smsRepository();
	}

	@Bean
	public JobHelper jobHelper(){
		return new JobHelper();
	}
	
	@Bean
	public SendSmsNexmo sendSmsNexmo() {
		return new SendSmsNexmo();
	}

	@Bean
	public PromotionJobBus promotionJobBus() {
		return new PromotionJobBusImpl();
	}

	@Bean
	public RestTemplateHelper restTemplateHelper() {
		return new RestTemplateHelper();
	}

	@Resource
	private Environment environment;

	@Bean
	public HibernateJpaSessionFactoryBean sessionFactory(EntityManagerFactory emf) {
		HibernateJpaSessionFactoryBean factory = new HibernateJpaSessionFactoryBean();
		factory.setEntityManagerFactory(emf);
		return factory;
	}

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();

		dataSource.setDriverClassName("org.postgresql.Driver");
		dataSource.setUrl("jdbc:postgresql://localhost:5432/pitayaa_notification");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres");

		return dataSource;
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		EntityManagerFactory factory = entityManagerFactory();
		return new JpaTransactionManager(factory);
	}

	@Bean
	public EntityManagerFactory entityManagerFactory() {
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setGenerateDdl(Boolean.TRUE);
		vendorAdapter.setShowSql(Boolean.TRUE);
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setPackagesToScan("pitayaa.nail.domain.notification");
		factory.setDataSource(dataSource());
		factory.afterPropertiesSet();
		factory.setLoadTimeWeaver(new InstrumentationLoadTimeWeaver());
		return factory.getObject();
	}

	@Bean
	public HibernateExceptionTranslator hibernateExceptionTranslator() {
		return new HibernateExceptionTranslator();
	}
}
