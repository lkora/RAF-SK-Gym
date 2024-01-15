package raf.sk.gym.userservice.repository;

import org.springframework.data.repository.ListCrudRepository;
import raf.sk.gym.userservice.model.Client;

import java.util.Optional;

public interface ClientRepository extends ListCrudRepository<Client, Long> {

    Optional<Client> findByClientId(Long id);

}
