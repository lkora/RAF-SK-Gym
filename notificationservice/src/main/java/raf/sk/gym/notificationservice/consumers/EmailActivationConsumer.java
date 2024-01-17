package raf.sk.gym.notificationservice.consumers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import raf.sk.gym.notificationservice.consumers.objects.ActivationEmailDto;
import raf.sk.gym.notificationservice.logging.EmailLog;
import raf.sk.gym.notificationservice.repository.MailLogRepository;

@Service
@Slf4j
public class EmailActivationConsumer {

    private final JavaMailSender mailSender;
    private final MailLogRepository mailLogRepository;
    private final String topicName = "activation-email";

    public EmailActivationConsumer(MailLogRepository mailLogRepository, JavaMailSender mailSender) {
        this.mailLogRepository = mailLogRepository;
        this.mailSender = mailSender;
    }

    @KafkaListener(topics = topicName)
    public void consume(String message) throws JsonProcessingException {
        log.info("Consuming: {}", message);
        ObjectMapper mapper = new ObjectMapper();
        ActivationEmailDto activationEmailDto = mapper.readValue(message, ActivationEmailDto.class);
        System.out.println(activationEmailDto);
        // Prepare an email
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(activationEmailDto.receiver()
                .email());
        mailMessage.setSubject("Account Activation");
        mailMessage.setText("Dear " + activationEmailDto.receiver()
                .firstName() + " " + activationEmailDto.receiver()
                .lastName() + ",\n\n" + "Please click the following link to activate your account:\n" + activationEmailDto.link());

        // Send the email
        log.debug("Sending email");
        mailSender.send(mailMessage);
        // Log the email
        log.trace("Attempting to save message");
        mailLogRepository.save(new EmailLog(topicName, activationEmailDto.receiver()
                .email(), mailMessage.getSubject() + " --- " + mailMessage.getText()));
        log.trace("Saved message successfully");
    }
}
