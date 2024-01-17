package raf.sk.gym.schedulingservice.repository;

import org.springframework.data.repository.ListCrudRepository;
import raf.sk.gym.schedulingservice.model.Training;

import java.util.Optional;

public interface TrainingRepository extends ListCrudRepository<Training, Integer> {

    Optional<Training> findByName(String name);
}