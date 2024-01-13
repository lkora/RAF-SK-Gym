package raf.sk.gym.notificationservice;


import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
class ExampleKafkaConsumer {
	@KafkaListener(topics="myTopic")
	public void consume(String message) {
		System.out.println("Consumed " + message);
	}
}