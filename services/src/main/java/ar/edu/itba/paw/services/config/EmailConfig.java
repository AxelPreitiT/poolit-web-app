package ar.edu.itba.paw.services.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import java.util.Properties;

//@Configuration
public class EmailConfig {

//    @Value("${spring.mail.host}")
//    private String host;
//
//    @Value("${spring.mail.port}")
//    private String port;
//
//    @Value("${spring.mail.username}")
//    private String username;
//
//    @Value("${spring.mail.password}")
//    private String password;
//
//    @Value("${spring.mail.properties.mail.smtp.auth}")
//    private String smtpAuth;
//
//    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
//    private String starttlsEnable;

//    @Bean
//    public JavaMailSender javaMailSender() {
//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//        mailSender.setHost("smtp.gmail.com");
//        mailSender.setPort(587);
//        mailSender.setUsername("poolit.noreply@gmail.com");
//        mailSender.setPassword("dpwfwbmwyuqguljk");
//
//        Properties props = mailSender.getJavaMailProperties();
//        props.put("mail.smtp.auth", true);
//        props.put("mail.smtp.starttls.enable", true);
//
//        return mailSender;
//    }
}


