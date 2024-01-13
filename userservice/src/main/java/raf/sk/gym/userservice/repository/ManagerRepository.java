package raf.sk.gym.userservice.repository;

import org.springframework.data.repository.ListCrudRepository;
import raf.sk.gym.userservice.model.Manager;

public interface ManagerRepository extends ListCrudRepository<Manager, Long> {
}
