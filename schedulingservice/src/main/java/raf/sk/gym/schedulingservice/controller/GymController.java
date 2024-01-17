package raf.sk.gym.schedulingservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import raf.sk.gym.schedulingservice.dto.kafka.TrainingScheduled;
import raf.sk.gym.schedulingservice.dto.request.GymEditRequest;
import raf.sk.gym.schedulingservice.dto.response.GeneralResponse;
import raf.sk.gym.schedulingservice.dto.response.GymDataResponse;
import raf.sk.gym.schedulingservice.service.AuthService;
import raf.sk.gym.schedulingservice.service.GymService;
import raf.sk.gym.schedulingservice.service.UserService;

@Slf4j
@Controller
@RequestMapping("/gym")
public class GymController {

    private static final String TOPIC = "training-scheduled";
    private final AuthService authService;
    private final GymService gymService;

    public GymController(AuthService authService, GymService gymService, UserService userService,
                         KafkaTemplate<String, TrainingScheduled> kafkaTemplate) {
        this.authService = authService;
        this.gymService = gymService;
    }

    @GetMapping("/{name}")
    public ResponseEntity<GymDataResponse> getGymData(@RequestHeader("Authorization") String token,
                                                      @PathVariable String name) {
        if (!authService.isManager(token)) {
            log.warn("Unauthorized user attempted to access Gym data. Consider revoking the following JWT token: {}",
                    token);
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .build();
        }
        if (!authService.attemptManagerVerification(token).gymName().equalsIgnoreCase(name)) {
            log.warn("Unauthorized manager attempted to access Gym data. Consider revoking the following JWT token: {}",
                    token);
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .build();
        }

        var gymOptional = gymService.findGymByName(name);
        if (gymOptional.isEmpty()) {
            return ResponseEntity.notFound()
                    .build();
        }
        var gym = gymOptional.get();
        GymDataResponse gymDataResponse = GymDataResponse.fromGym(gym);
        return ResponseEntity.ok(gymDataResponse);
    }

    @PostMapping("/edit")
    public ResponseEntity<GeneralResponse> editGymData(@RequestHeader("Authorization") String token,
                                                       @RequestBody GymEditRequest editRequest) {
        if (!authService.isManager(token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .build();
        }
        var optional = gymService.findGymById(editRequest.id());
        if (optional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new GeneralResponse("Gym not found"));
        }
        var gym = optional.get();
        gymService.updateGym(gym, editRequest.name(), editRequest.description(), editRequest.numberOfTrainers());
        return ResponseEntity.ok(new GeneralResponse("Successfully changed gym data."));
    }
}
