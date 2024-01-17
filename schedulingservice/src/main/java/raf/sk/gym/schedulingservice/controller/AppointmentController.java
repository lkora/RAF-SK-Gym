package raf.sk.gym.schedulingservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import raf.sk.gym.schedulingservice.dto.kafka.TrainingDto;
import raf.sk.gym.schedulingservice.dto.kafka.TrainingScheduled;
import raf.sk.gym.schedulingservice.dto.request.AppointmentRequest;
import raf.sk.gym.schedulingservice.dto.request.CreateAppointmentRequest;
import raf.sk.gym.schedulingservice.dto.response.AppointmentResponse;
import raf.sk.gym.schedulingservice.dto.response.ErrorResponse;
import raf.sk.gym.schedulingservice.dto.response.GeneralResponse;
import raf.sk.gym.schedulingservice.model.*;
import raf.sk.gym.schedulingservice.repository.AppointmentRepository;
import raf.sk.gym.schedulingservice.repository.TrainingRepository;
import raf.sk.gym.schedulingservice.repository.UserAppointmentRepository;
import raf.sk.gym.schedulingservice.service.AuthService;
import raf.sk.gym.schedulingservice.service.GymService;
import raf.sk.gym.schedulingservice.service.UserService;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/schedule")
public class AppointmentController {
    private final AppointmentRepository appointmentRepository;
    private static final String TOPIC = "training-scheduled";
    private final GymService gymService;
    private final UserService userService;
    private final KafkaTemplate<String, TrainingScheduled> kafkaTemplate;
    private final UserAppointmentRepository userAppointmentRepository;
    private final AuthService authService;

    public AppointmentController(GymService gymService, UserService userService, KafkaTemplate<String,
            TrainingScheduled> kafkaTemplate, UserAppointmentRepository userAppointmentRepository,
                                 AuthService authService, AppointmentRepository appointmentRepository) {
        this.gymService = gymService;
        this.userService = userService;
        this.kafkaTemplate = kafkaTemplate;
        this.userAppointmentRepository = userAppointmentRepository;
        this.authService = authService;
        this.appointmentRepository = appointmentRepository;
    }

    @PostMapping("/create-appointment")
    public ResponseEntity<?> createAppointment(@RequestBody CreateAppointmentRequest request, @RequestHeader(
            "Authorization") String token, TrainingRepository repository) {
        if (!authService.isManager(token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ErrorResponse("Forbidden."));
        }
        Appointment appointment = new Appointment();
        var gymOptional = gymService.findGymByName(request.gymName());
        if (gymOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Couldn't fine selected gym."));
        }
        Optional<Training> trainingOptional = repository.findByName(request.trainingName());
        if (trainingOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Couldn't fine selected training type."));
        }
        appointment.setGymTraining(new GymTraining(gymOptional.get(), trainingOptional.get(), request.price()));
        appointment.setParticipants(request.participants());
        appointment.setDayOfWeek(request.dayOfWeek());
        appointmentRepository.save(appointment);
        return ResponseEntity.status(HttpStatus.OK).body(new GeneralResponse("Appointment creation successful."));
    }

    @GetMapping("/")
    public ResponseEntity<?> getSchedule() {
        log.info("Get schedule called.");
        return ResponseEntity.ok(gymService.getAvailableAppointments()
                .stream()
                .map(AppointmentResponse::fromAppointment)
                .toList());
    }

    @PostMapping("/make-appointment")
    public ResponseEntity<?> makeAppointment(@RequestBody AppointmentRequest request,
                                             @RequestHeader("Authorization") String token) {
        log.info("Got token: {}", token);
        var requestedAppointment = gymService.getAvailableAppointments()
                .stream()
                .filter(appointment -> appointment.getId()
                        .equals(request.id()))
                .findFirst();
        if (requestedAppointment.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("Selected appointment doesn't exist"));
        log.info("Found a matching appointment for id: {}", request.id());
        var app = requestedAppointment.get();
        var user = userService.findUser(token);
        UserAppointment ua = new UserAppointment(new UserAppointmentId(user.id(), app.getId()), app);
        userAppointmentRepository.save(ua);
        kafkaTemplate.send(TOPIC, new TrainingScheduled(user, new TrainingDto(app.getGymTraining()
                .getTraining()
                .getName(), app.getGymTraining()
                .getTraining()
                .getCategory(), app.getStartTime())));
        return ResponseEntity.status(HttpStatus.OK)
                .body(new GeneralResponse("Appointment scheduling successful"));
    }

    @PostMapping("/cancel-appointment")
    public ResponseEntity<?> cancelAppointment(@RequestBody AppointmentRequest request, @RequestHeader("Authorization"
    ) String token, UserAppointmentRepository repository) {
        if (authService.isClient(token)) {
            return clientCancellation(request, token, repository);
        }
        // admin or manager attempts to cancel an appointment
        return managerCancellation(request, token, repository);
    }

    private ResponseEntity<?> managerCancellation(AppointmentRequest request, String token,
                                                  UserAppointmentRepository repository) {
        var requestedAppointment = gymService.getAvailableAppointments()
                .stream()
                .filter(appointment -> appointment.getId()
                        .equals(request.id()))
                .findFirst();
        if (requestedAppointment.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("Selected appointment doesn't exist"));
        List<UserAppointment> uas = repository.findUserAppointmentsByAppointment(requestedAppointment.get());
        for (UserAppointment ua : uas) {
            ua.getAppointment()
                    .setCancelledBy(userService.findUser(token)
                            .id());
        }
        return null;
    }

    private ResponseEntity<?> clientCancellation(AppointmentRequest request, String token,
                                                 UserAppointmentRepository repository) {
        return null;
    }
}
