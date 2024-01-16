package raf.sk.gym.notificationservice.repository;

import org.springframework.data.repository.ListCrudRepository;
import raf.sk.gym.notificationservice.logging.EmailLog;

public interface MailLogRepository extends ListCrudRepository<EmailLog, Long> {
}
