package raf.sk.gym.notificationservice.consumers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import raf.sk.gym.notificationservice.consumers.objects.TrainingCancelledDTO;
import raf.sk.gym.notificationservice.consumers.objects.models.ReceiverDTO;

@Service
public class TrainingCancelledConsumer {

    @Autowired
    private JavaMailSender mailSender;

    @KafkaListener(topics="training-cancelled")
    public void consumeTrainingCancelled(String message) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        TrainingCancelledDTO trainingCancelledDto = mapper.readValue(message, TrainingCancelledDTO.class);

        // Prepare an email for each receiver
        for (ReceiverDTO receiver : trainingCancelledDto.getReceivers()) {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(receiver.getEmail());
            mailMessage.setSubject("Training Cancelled");
            mailMessage.setText("Dear " + receiver.getFirstName() + " " + receiver.getLastName() + ",\n\n"
                    + "Your " + trainingCancelledDto.getTraining().getTrainingName() + " training has been cancelled.");

            // Send the email
            mailSender.send(mailMessage);
        }
    }
}
