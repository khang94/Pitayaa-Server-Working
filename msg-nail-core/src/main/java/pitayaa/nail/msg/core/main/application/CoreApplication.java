/**
 * 
 */
package pitayaa.nail.msg.core.main.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.multipart.support.MultipartFilter;

/**
 * @author kmai2
 *
 */

/*
 * @EnableJpaRepositories
 * 
 * @SpringBootApplication
 * 
 * @ComponentScan(basePackages = {"pitayaa.nail.domain.account"})
 * 
 * @EnableAutoConfiguration
 * 
 * @EntityScan(basePackages = {"pitayaa.nail.domain.account"})
 * 
 * @Configuration
 */
//@EnableResourceServer

//@EnableResourceServer
@EnableAutoConfiguration()
@SpringBootApplication
@ComponentScan(basePackages = { "pitayaa.nail" })
@EntityScan(basePackages = { "pitayaa.nail.domain" })
@EnableJpaRepositories(basePackages = { "pitayaa.nail" })
public class CoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoreApplication.class, args);
	}
 
    
    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        System.out.println("multipartResolver()");
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        return multipartResolver;
    }

    @Bean
    public FilterRegistrationBean multipartFilterRegistrationBean() {
        final MultipartFilter multipartFilter = new MultipartFilter();
        final FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(multipartFilter);
        filterRegistrationBean.addInitParameter("multipartResolverBeanName", "commonsMultipartResolver");
        return filterRegistrationBean;
    }

}
