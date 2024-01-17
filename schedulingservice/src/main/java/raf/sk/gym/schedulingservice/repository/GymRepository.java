package raf.sk.gym.schedulingservice.repository;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import raf.sk.gym.schedulingservice.model.Gym;

import java.util.Optional;

@Repository
public interface GymRepository extends ListCrudRepository<Gym, Integer> {
    Optional<Gym> findByName(String name);
}
