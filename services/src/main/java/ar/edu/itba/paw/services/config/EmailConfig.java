package ar.edu.itba.paw.services.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.Properties;

@Configuration
@EnableAsync
@PropertySource("classpath:/mail.properties")
public class EmailConfig {

    @Autowired
    private Environment environment;

    @Bean(name = "baseUrl")
    public String baseUrl(){
        return environment.getProperty("base_url");
    }

    private static final String SENDER_USERNAME = "poolit.noreply@gmail.com";

    private static final String SENDER_PASSWORD ="dpwfwbmwyuqguljk";

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername(SENDER_USERNAME);
        mailSender.setPassword(SENDER_PASSWORD);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        return mailSender;
    }
}


