package raf.sk.gym.schedulingservice.repository;

import org.springframework.data.repository.ListCrudRepository;
import raf.sk.gym.schedulingservice.model.Appointment;
import raf.sk.gym.schedulingservice.model.UserAppointment;

import java.util.List;

public interface UserAppointmentRepository extends ListCrudRepository<UserAppointment, Integer> {

    List<UserAppointment> findUserAppointmentsByAppointment(Appointment appointment);
}
