package raf.sk.gym.notificationservice.consumers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import raf.sk.gym.notificationservice.consumers.objects.TrainingReminderDto;
import raf.sk.gym.notificationservice.logging.EmailLog;
import raf.sk.gym.notificationservice.repository.MailLogRepository;

@Service
public class TrainingReminderConsumer {

    private final JavaMailSender mailSender;
    private final String topicName = "training-reminder";
    private final MailLogRepository mailLogRepository;

    public TrainingReminderConsumer(JavaMailSender mailSender, MailLogRepository mailLogRepository) {
        this.mailSender = mailSender;
        this.mailLogRepository = mailLogRepository;
    }


    @KafkaListener(topics = topicName)
    public void consumeTrainingReminder(String message) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        TrainingReminderDto trainingReminderDto = mapper.readValue(message, TrainingReminderDto.class);

        // Prepare an email
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(trainingReminderDto.receiver()
                .email());
        mailMessage.setSubject("Training Reminder");
        mailMessage.setText("Dear " + trainingReminderDto.receiver()
                .firstName() + " " + trainingReminderDto.receiver()
                .lastName() + ",\n\n" + "This is a reminder for your " + trainingReminderDto.training()
                .trainingName() + " training at " + trainingReminderDto.training()
                .startTime() + ".");

        // Send the email
        mailSender.send(mailMessage);

        // Log the email
        mailLogRepository.save(new EmailLog(topicName, trainingReminderDto.receiver()
                .email(), mailMessage.getSubject() + " --- " + mailMessage.getText()));

    }
}
