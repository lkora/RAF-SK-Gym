package raf.sk.gym.userservice.repository;

import org.springframework.data.repository.ListCrudRepository;
import raf.sk.gym.userservice.model.ConfirmationToken;

import java.util.Optional;
import java.util.UUID;

public interface ConfirmationTokenRepository extends ListCrudRepository<ConfirmationToken, Integer> {
    Optional<ConfirmationToken> findByConfirmationToken(UUID confirmationToken);
}