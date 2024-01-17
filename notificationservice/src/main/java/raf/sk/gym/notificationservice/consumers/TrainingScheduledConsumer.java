package raf.sk.gym.notificationservice.consumers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import raf.sk.gym.notificationservice.consumers.objects.TrainingScheduledDto;
import raf.sk.gym.notificationservice.logging.EmailLog;
import raf.sk.gym.notificationservice.repository.MailLogRepository;

@Service
public class TrainingScheduledConsumer {

    private final JavaMailSender mailSender;
    private final MailLogRepository mailLogRepository;
    private final String topicName = "training-scheduled";

    public TrainingScheduledConsumer(JavaMailSender mailSender, MailLogRepository mailLogRepository) {this.mailSender = mailSender;
        this.mailLogRepository = mailLogRepository;
    }

    @KafkaListener(topics=topicName)
    public void consumeTrainingScheduled(String message) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        TrainingScheduledDto trainingScheduledDto = mapper.readValue(message, TrainingScheduledDto.class);

        // Prepare an email
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(trainingScheduledDto.receiver().email());
        mailMessage.setSubject("Training Scheduled");
        mailMessage.setText("Dear " + trainingScheduledDto.receiver().firstName() + " " + trainingScheduledDto.receiver().lastName() + ",\n\n"
                + "Your " + trainingScheduledDto.training().trainingName() + " training has been scheduled for " + trainingScheduledDto.training().startTime() + ".");

        // Send the email
        mailSender.send(mailMessage);

        // Log the email
        mailLogRepository.save(new EmailLog(topicName, trainingScheduledDto.receiver().email(), mailMessage.getSubject() + " --- " + mailMessage.getText()));

    }
}
