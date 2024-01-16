package raf.sk.gym.notificationservice.logging;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "mailer_log")
@Getter
public class EmailLog {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @CreatedDate
    @Column(name = "timestamp")
    private LocalDateTime timestamp = LocalDateTime.now();

    @Column(name = "topic_name")
    private String topic;

    @Column(name = "receiver")
    private String receiver;

    @Column(name = "message", length = 10000)
    private String message;

    public EmailLog(String topic, String receiver, String message) {
        this.topic = topic;
        this.receiver = receiver;
        this.message = message;
    }
    public EmailLog() {}

    @Override
    public String toString() {
        return "EmailLog{" + "id=" + id + ", timestamp=" + timestamp + ", topic='" + topic + '\'' + ", receiver='" + receiver + '\'' + ", message='" + message + '\'' + '}';
    }
}
