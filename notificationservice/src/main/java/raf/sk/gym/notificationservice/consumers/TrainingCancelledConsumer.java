package raf.sk.gym.notificationservice.consumers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import raf.sk.gym.notificationservice.consumers.objects.TrainingCancelledDto;
import raf.sk.gym.notificationservice.consumers.objects.models.ReceiverDto;
import raf.sk.gym.notificationservice.logging.EmailLog;
import raf.sk.gym.notificationservice.repository.MailLogRepository;

@Service
public class TrainingCancelledConsumer {

    private final JavaMailSender mailSender;
    private final MailLogRepository mailLogRepository;
    private final String topicName = "training-cancelled";

    public TrainingCancelledConsumer(MailLogRepository mailLogRepository, JavaMailSender mailSender) {this.mailLogRepository = mailLogRepository;
        this.mailSender = mailSender;
    }

    @KafkaListener(topics=topicName)
    public void consumeTrainingCancelled(String message) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        TrainingCancelledDto trainingCancelledDto = mapper.readValue(message, TrainingCancelledDto.class);

        // Prepare an email for each receiver
        for (ReceiverDto receiver : trainingCancelledDto.receivers()) {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(receiver.email());
            mailMessage.setSubject("Training Cancelled");
            mailMessage.setText("Dear " + receiver.firstName() + " " + receiver.lastName() + ",\n\n"
                    + "Your " + trainingCancelledDto.training().trainingName() + " training has been cancelled.");

            // Send the email
            mailSender.send(mailMessage);

            // Log the email
            mailLogRepository.save(new EmailLog(topicName, receiver.email(), mailMessage.getSubject() + " --- " + mailMessage.getText()));

        }
    }
}
