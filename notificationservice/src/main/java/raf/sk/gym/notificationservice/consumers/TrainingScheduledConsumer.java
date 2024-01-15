package raf.sk.gym.notificationservice.consumers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import raf.sk.gym.notificationservice.consumers.objects.TrainingScheduledDTO;

@Service
public class TrainingScheduledConsumer {

    @Autowired
    private JavaMailSender mailSender;

    @KafkaListener(topics="training-scheduled")
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
    }
}
