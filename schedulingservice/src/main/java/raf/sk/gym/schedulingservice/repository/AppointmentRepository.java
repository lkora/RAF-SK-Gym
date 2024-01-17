package raf.sk.gym.schedulingservice.repository;

import org.springframework.data.repository.ListCrudRepository;
import raf.sk.gym.schedulingservice.model.Appointment;

public interface AppointmentRepository extends ListCrudRepository<Appointment, Integer> {}