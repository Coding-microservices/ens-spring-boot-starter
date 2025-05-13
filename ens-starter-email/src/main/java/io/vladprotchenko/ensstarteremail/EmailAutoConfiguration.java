package io.vladprotchenko.ensstarteremail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Slf4j
@Configuration
public class EmailAutoConfiguration {

    @Bean
    public JavaMailSender javaMailSender(
            @Value("${spring.mail.host}") String emailHost,
            @Value("${spring.mail.port}") int emailPort,
            @Value("${spring.mail.username}") String emailUsername,
            @Value("${spring.mail.password}") String emailPassword) {

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(emailHost);
        mailSender.setPort(emailPort);
        mailSender.setUsername(emailUsername);
        mailSender.setPassword(emailPassword);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        return mailSender;
    }

    @Bean
    public EmailService emailService(JavaMailSender javaMailSender,
                                     @Value("${spring.mail.username}") String emailUsername) {
        return new EmailService(javaMailSender, emailUsername);
    }
}
