package raf.sk.gym.schedulingservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AliveController {

    @GetMapping
    public ResponseEntity<HealthcheckResponse> healthcheck() {
        return new ResponseEntity<>(new HealthcheckResponse("The server is alive and well."),HttpStatus.OK);
    }
    public record HealthcheckResponse(String message) {}
}
