package io.vladprotchenko.ensstarteremail;

import io.vladprotchenko.ensstartercore.exception.custom.InternalServiceException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender emailSender;
    private final String emailUsername;

    /**
     * Sends an email message using the configured JavaMailSender.
     * <p>
     * This method creates a MIME message, sets the sender, recipient, subject, and content, then sends the email using the JavaMailSender. The
     * content can be either HTML or plain text, as determined by the {@code isHtml} flag.
     * </p>
     * @param to the recipient's email address
     * @param subject the subject of the email
     * @param content the content of the email message (can be HTML or plain text)
     * @param isHtml {@code true} if the content is in HTML format, {@code false} if plain text
     * @throws MessagingException if an error occurs during message creation or sending
     */
    public void sendEmail(String to, String subject, String content, boolean isHtml) throws MessagingException {
        log.debug("Sending email with subject '{}' to: {}", subject, to);

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(emailUsername);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, isHtml);

        emailSender.send(message);
        log.debug("Email was sent to: {}", to);
    }

    /**
     * Loads an email template from the classpath.
     * <p>
     * The method attempts to locate the template file under the "templates" directory in the classpath,
     * read its contents as a string, and return the resulting content.
     * </p>
     *
     * @param fileName the name of the template file to load (e.g., "verification-email.html")
     * @return the content of the email template as a String
     * @throws InternalServiceException if the template cannot be loaded due to an I/O error
     */
    public String loadEmailTemplate(String fileName) {
        try {
            Path path = new ClassPathResource("templates/" + fileName).getFile().toPath();
            return Files.readString(path);
        } catch (IOException e) {
            throw new InternalServiceException(
                String.format("Failed to load email template: %s. Details: %s", fileName, e.getMessage()));
        }
    }

    /**
     * Populates the given email template with the provided values.
     * <p>
     * This method replaces all occurrences of placeholders in the template with corresponding values.
     * Placeholders should be defined in the template using the format <code>{{key}}</code>,
     * where <code>key</code> corresponds to a key in the provided map.
     * </p>
     *
     * @param template the email template containing placeholders
     * @param values a map of placeholder keys and their replacement values
     * @return the template with all placeholders replaced by their respective values
     */
    public String populateTemplate(String template, Map<String, String> values) {
        for (Map.Entry<String, String> entry : values.entrySet()) {
            template = template.replace("{{" + entry.getKey() + "}}", entry.getValue());
        }
        return template;
    }
}
