package raf.sk.gym.userservice.repository;

import org.springframework.data.repository.ListCrudRepository;
import raf.sk.gym.userservice.model.Admin;

public interface AdminRepository extends ListCrudRepository<Admin, Long> {
}
