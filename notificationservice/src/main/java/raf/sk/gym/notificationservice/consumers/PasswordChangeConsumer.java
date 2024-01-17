package raf.sk.gym.notificationservice.consumers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import raf.sk.gym.notificationservice.consumers.objects.PasswordChangeDto;
import raf.sk.gym.notificationservice.logging.EmailLog;
import raf.sk.gym.notificationservice.repository.MailLogRepository;

@Service
public class PasswordChangeConsumer {

    private final JavaMailSender mailSender;
    private final String topicName = "password-change";
    private final MailLogRepository mailLogRepository;

    public PasswordChangeConsumer(JavaMailSender mailSender, MailLogRepository mailLogRepository) {
        this.mailSender = mailSender;
        this.mailLogRepository = mailLogRepository;
    }

    @KafkaListener(topics = topicName)
    public void consumePasswordChange(String message) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        PasswordChangeDto passwordChangeDto = mapper.readValue(message, PasswordChangeDto.class);

        // Prepare an email
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(passwordChangeDto.email());
        mailMessage.setSubject("Password Change Request");
        mailMessage.setText("Dear " + passwordChangeDto.username() + ",\n\n" + "Please click the following link to " +
                "reset your password:\n" + passwordChangeDto.resetPasswordLink());

        // Send the email
        mailSender.send(mailMessage);

        // Log the email
        mailLogRepository.save(new EmailLog(topicName, passwordChangeDto.email(),
                mailMessage.getSubject() + " --- " + mailMessage.getText()));
    }
}
