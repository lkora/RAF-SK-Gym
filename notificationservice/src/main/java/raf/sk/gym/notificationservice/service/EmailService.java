package raf.sk.gym.notificationservice.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailService {

    public final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {this.mailSender = mailSender;}

    public void sendSimpleMessage(String to, String subject, String text) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
//        mailSender.send(message);
        System.out.println(message);
    }
}
