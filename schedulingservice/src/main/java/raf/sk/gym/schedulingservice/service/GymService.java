package raf.sk.gym.schedulingservice.service;

import org.springframework.stereotype.Service;
import raf.sk.gym.schedulingservice.model.Appointment;
import raf.sk.gym.schedulingservice.model.Gym;
import raf.sk.gym.schedulingservice.model.UserAppointment;
import raf.sk.gym.schedulingservice.repository.AppointmentRepository;
import raf.sk.gym.schedulingservice.repository.GymRepository;

import java.util.List;
import java.util.Optional;

@Service
public class GymService {

    private final GymRepository gymRepository;

    private final AppointmentRepository appointmentRepository;

    public GymService(GymRepository gymRepository, AppointmentRepository appointmentRepository) {
        this.gymRepository = gymRepository;
        this.appointmentRepository = appointmentRepository;
    }

    public List<Appointment> getAvailableAppointments() {
        return gymRepository.findAll().stream()
        .flatMap(gym -> gym.getGymTrainings().stream())
        .flatMap(gymTraining -> appointmentRepository.findByGymTrainingId(gymTraining.getId()).stream())
        .filter(appointment -> appointment.getRegisteredUsers() < appointment.getParticipants())
        .toList();
    }

    public Optional<Gym> findGymByName(String name) {
        return gymRepository.findByName(name);
    }

    public void saveGym(Gym gym) {
        gymRepository.save(gym);
    }

    public Optional<Gym> findGymById(Integer id) {
        return gymRepository.findById(id);
    }

    public void updateGym(Gym gym, String name, String description, Integer numberOfTrainers) {
        gym.setNumberOfTrainers(numberOfTrainers);
        gym.setName(name);
        gym.setDescription(description);
        gymRepository.save(gym);
    }

    public void addUserAppointment(UserAppointment appointment) {

    }
}
