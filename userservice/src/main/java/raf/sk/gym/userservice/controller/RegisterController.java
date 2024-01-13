package raf.sk.gym.userservice.controller;

import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import raf.sk.gym.userservice.dto.request.ClientRegistration;
import raf.sk.gym.userservice.dto.request.ManagerRegistration;
import raf.sk.gym.userservice.dto.response.GeneralResponse;
import raf.sk.gym.userservice.service.UserService;

@RestController
@RequestMapping("/auth/register")
public class RegisterController {

    private final UserService service;

    public RegisterController(UserService service) {
        this.service = service;
    }

    @PostMapping("/manager")
    ResponseEntity<GeneralResponse> registerManager(@Valid @RequestBody ManagerRegistration dto) {
        try {
            service.createAndStoreManager(dto.username(), dto.password(), dto.email(), dto.birthDate(), dto.firstName(),
                dto.lastName(), dto.gymName(), dto.dateOfEmployment());
        return new ResponseEntity<>(new GeneralResponse("Activation email sent. Please check your email."),
                HttpStatus.CREATED);
        } catch (DataIntegrityViolationException ignored) {
            return new ResponseEntity<>(new GeneralResponse("Unable to create manager due to conflict"), HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/client")
    ResponseEntity<GeneralResponse> registerClient(@Valid @RequestBody ClientRegistration dto) {
        System.out.println("Client creation attempted");
        try {
            service.createAndStoreClient(dto.username(), dto.password(), dto.email(), dto.birthDate(), dto.firstName(),
                dto.lastName());
        return new ResponseEntity<>(new GeneralResponse("Activation email sent. Please check your email."),
                HttpStatus.CREATED);
        } catch (DataIntegrityViolationException ignored) {
            return new ResponseEntity<>(new GeneralResponse("Unable to create client due to conflict"), HttpStatus.CONFLICT);
        }
    }

}
