package raf.sk.gym.schedulingservice.repository;

import org.springframework.data.repository.ListCrudRepository;
import raf.sk.gym.schedulingservice.model.Appointment;

import java.util.List;

public interface AppointmentRepository extends ListCrudRepository<Appointment, Integer> {
    List<Appointment> findByGymTrainingId(Integer gymTrainingId);
}