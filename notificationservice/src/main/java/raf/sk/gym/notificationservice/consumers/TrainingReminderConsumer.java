package raf.sk.gym.notificationservice.consumers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import raf.sk.gym.notificationservice.consumers.objects.TrainingReminderDTO;
import raf.sk.gym.notificationservice.logging.EmailLog;
import raf.sk.gym.notificationservice.repository.MailLogRepository;

@Service
public class TrainingReminderConsumer {

    @Autowired
    private JavaMailSender mailSender;
    private MailLogRepository mailLogRepository;
    private final String topicName = "training-reminder";


    @KafkaListener(topics=topicName)
    public void consumeTrainingReminder(String message) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        TrainingReminderDTO trainingReminderDto = mapper.readValue(message, TrainingReminderDTO.class);

        // Prepare an email
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(trainingReminderDto.getReceiver().getEmail());
        mailMessage.setSubject("Training Reminder");
        mailMessage.setText("Dear " + trainingReminderDto.getReceiver().getFirstName() + " " + trainingReminderDto.getReceiver().getLastName() + ",\n\n"
                + "This is a reminder for your " + trainingReminderDto.getTraining().getTrainingName() + " training at " + trainingReminderDto.getTraining().getStartTime() + ".");

        // Send the email
        mailSender.send(mailMessage);

        // Log the email
        mailLogRepository.save(new EmailLog(topicName, trainingReminderDto.getReceiver().getEmail(), mailMessage.getSubject() + " --- " + mailMessage.getText()));

    }
}
