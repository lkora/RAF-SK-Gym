package raf.sk.gym.notificationservice.consumers;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import raf.sk.gym.notificationservice.consumers.objects.ActivationEmailDTO;

@Service
public class EmailActivationConsumer {

	@Autowired
	private JavaMailSender mailSender;

	@KafkaListener(topics="activation-email")
	public void consume(String message) throws JsonProcessingException {
		System.out.println("Consumed " + message);

		ObjectMapper mapper = new ObjectMapper();
		ActivationEmailDTO activationEmailDto = mapper.readValue(message, ActivationEmailDTO.class);

		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(activationEmailDto.getReceiver().getEmail());
		mailMessage.setSubject("Account Activation");
		mailMessage.setText("Dear " + activationEmailDto.getReceiver().getFirstName() + " " + activationEmailDto.getReceiver().getLastName() + ",\n\n"
				+ "Please click the following link to activate your account:\n"
				+ activationEmailDto.getLink());

		mailSender.send(mailMessage);
	}
}
