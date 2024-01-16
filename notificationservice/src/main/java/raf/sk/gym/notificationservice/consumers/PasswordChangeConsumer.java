package raf.sk.gym.notificationservice.consumers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import raf.sk.gym.notificationservice.consumers.objects.PasswordChangeDTO;
import raf.sk.gym.notificationservice.logging.EmailLog;
import raf.sk.gym.notificationservice.repository.MailLogRepository;

@Service
public class PasswordChangeConsumer {

    @Autowired
    private JavaMailSender mailSender;
    private MailLogRepository mailLogRepository;
    private final String topicName = "password-change";

    @KafkaListener(topics=topicName)
    public void consumePasswordChange(String message) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        PasswordChangeDTO passwordChangeDto = mapper.readValue(message, PasswordChangeDTO.class);

        // Prepare an email
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(passwordChangeDto.getEmail());
        mailMessage.setSubject("Password Change Request");
        mailMessage.setText("Dear " + passwordChangeDto.getUsername() + ",\n\n"
                + "Please click the following link to reset your password:\n"
                + passwordChangeDto.getResetPasswordLink());

        // Send the email
        mailSender.send(mailMessage);

        // Log the email
        mailLogRepository.save(new EmailLog(topicName, passwordChangeDto.getEmail(), mailMessage.getSubject() + " --- " + mailMessage.getText()));
    }
}
