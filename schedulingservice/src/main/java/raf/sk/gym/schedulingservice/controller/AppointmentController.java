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
import raf.sk.gym.schedulingservice.dto.response.AppointmentResponse;
import raf.sk.gym.schedulingservice.dto.response.ErrorResponse;
import raf.sk.gym.schedulingservice.dto.response.GeneralResponse;
import raf.sk.gym.schedulingservice.service.AuthService;
import raf.sk.gym.schedulingservice.service.GymService;
import raf.sk.gym.schedulingservice.service.UserService;

@Slf4j
@Controller
@RequestMapping("/schedule")
public class AppointmentController {
    private static final String TOPIC = "training-scheduled";
    private final GymService gymService;
    private final UserService userService;
    private final KafkaTemplate<String, TrainingScheduled> kafkaTemplate;

    public AppointmentController(GymService gymService, UserService userService,
                                 KafkaTemplate<String, TrainingScheduled> kafkaTemplate) {
        this.gymService = gymService;
        this.userService = userService;
        this.kafkaTemplate = kafkaTemplate;
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
        kafkaTemplate.send(TOPIC, new TrainingScheduled(user, new TrainingDto(app.getGymTraining()
                .getTraining()
                .getName(), app.getGymTraining().getTraining()
                .getCategory(), app.getStartTime()))); return ResponseEntity.status(HttpStatus.OK)
                .body(new GeneralResponse("Appointment scheduling successful"));
    }
}
