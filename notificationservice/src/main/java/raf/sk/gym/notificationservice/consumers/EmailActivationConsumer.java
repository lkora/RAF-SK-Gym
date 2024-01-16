package raf.sk.gym.notificationservice.consumers;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import raf.sk.gym.notificationservice.consumers.objects.ActivationEmailDTO;
import raf.sk.gym.notificationservice.logging.EmailLog;
import raf.sk.gym.notificationservice.repository.MailLogRepository;

@Service
public class EmailActivationConsumer {

	@Autowired
	private JavaMailSender mailSender;
	private MailLogRepository mailLogRepository;
	private final String topicName = "activation-email";

	@KafkaListener(topics=topicName)
	public void consume(String message) throws JsonProcessingException {
		System.out.println("Consumed " + message);

		ObjectMapper mapper = new ObjectMapper();
		ActivationEmailDTO activationEmailDto = mapper.readValue(message, ActivationEmailDTO.class);

		// Prepare an email
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(activationEmailDto.getReceiver().getEmail());
		mailMessage.setSubject("Account Activation");
		mailMessage.setText("Dear " + activationEmailDto.getReceiver().getFirstName() + " " + activationEmailDto.getReceiver().getLastName() + ",\n\n"
				+ "Please click the following link to activate your account:\n"
				+ activationEmailDto.getLink());

		// Send the email
		mailSender.send(mailMessage);

		// Log the email
		mailLogRepository.save(new EmailLog(topicName, activationEmailDto.getReceiver().getEmail(), mailMessage.getSubject() + " --- " + mailMessage.getText()));
	}
}
