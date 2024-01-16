package raf.sk.gym.notificationservice.consumers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import raf.sk.gym.notificationservice.consumers.objects.TrainingScheduledDTO;
import raf.sk.gym.notificationservice.logging.EmailLog;
import raf.sk.gym.notificationservice.repository.MailLogRepository;

@Service
public class TrainingScheduledConsumer {

    @Autowired
    private JavaMailSender mailSender;
    private MailLogRepository mailLogRepository;
    private final String topicName = "training-scheduled";

    @KafkaListener(topics=topicName)
    public void consumeTrainingScheduled(String message) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        TrainingScheduledDTO trainingScheduledDto = mapper.readValue(message, TrainingScheduledDTO.class);

        // Prepare an email
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(trainingScheduledDto.getReceiver().getEmail());
        mailMessage.setSubject("Training Scheduled");
        mailMessage.setText("Dear " + trainingScheduledDto.getReceiver().getFirstName() + " " + trainingScheduledDto.getReceiver().getLastName() + ",\n\n"
                + "Your " + trainingScheduledDto.getTraining().getTrainingName() + " training has been scheduled for " + trainingScheduledDto.getTraining().getStartTime() + ".");

        // Send the email
        mailSender.send(mailMessage);

        // Log the email
        mailLogRepository.save(new EmailLog(topicName, trainingScheduledDto.getReceiver().getEmail(), mailMessage.getSubject() + " --- " + mailMessage.getText()));

    }
}
