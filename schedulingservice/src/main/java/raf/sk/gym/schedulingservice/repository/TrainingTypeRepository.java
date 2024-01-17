package raf.sk.gym.schedulingservice.repository;

import org.springframework.data.repository.ListCrudRepository;
import raf.sk.gym.schedulingservice.model.TrainingType;

public interface TrainingTypeRepository extends ListCrudRepository<TrainingType, Integer> {}