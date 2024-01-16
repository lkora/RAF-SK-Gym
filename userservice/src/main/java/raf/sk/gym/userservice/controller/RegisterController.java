package raf.sk.gym.userservice.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import raf.sk.gym.userservice.dto.kafka.ActivationEmail;
import raf.sk.gym.userservice.dto.request.ClientRegistration;
import raf.sk.gym.userservice.dto.request.ManagerRegistration;
import raf.sk.gym.userservice.dto.request.UserRegistration;
import raf.sk.gym.userservice.dto.response.GeneralResponse;
import raf.sk.gym.userservice.model.ConfirmationToken;
import raf.sk.gym.userservice.model.User;
import raf.sk.gym.userservice.repository.ConfirmationTokenRepository;
import raf.sk.gym.userservice.service.UserService;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/auth/register")
@Slf4j
public class RegisterController {

    private static final String REGISTRATION_LINK = "http://localhost:8081/USERSERVICE/api/auth/register/confirm" +
            "-account?token=";
    private static final String TOPIC = "activation-email";
    private final KafkaTemplate<String, ActivationEmail> kafkaTemplate;
    private final UserService userService;

    private final ConfirmationTokenRepository tokenRepository;

    public RegisterController(KafkaTemplate<String, ActivationEmail> kafkaTemplate, UserService userService,
                              ConfirmationTokenRepository tokenRepository) {
        this.kafkaTemplate = kafkaTemplate;
        this.userService = userService;
        this.tokenRepository = tokenRepository;
    }

    @GetMapping("confirm-account")
    ResponseEntity<GeneralResponse> confirmAccount(@RequestParam("token") UUID confToken) {
        log.info("Account confirmation request received");
        var confirmationToken = tokenRepository.findByConfirmationToken(confToken);
        return confirmationToken.map(token -> {
                    if (!token.isValid()) {
                        log.info("Confirmation attempted with expired token");
                        return ResponseEntity.status(HttpStatus.GONE)
                                .body(new GeneralResponse("Token expired."));
                    }
                    User user = token.getUser();
                    user.setIsActivated(true);
                    userService.saveUser(user);
                    log.info("Account of user {} activated", user.getUsername());
                    return ResponseEntity.status(HttpStatus.OK)
                            .body(new GeneralResponse("Account confirmation successful."));
                })
                .orElseGet(() -> {
                    log.info("Confirmation attempted with invalid token");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new GeneralResponse("Invalid confirmation token."));
                });
    }

    @PostMapping("/manager")
    ResponseEntity<GeneralResponse> registerManager(@Valid @RequestBody ManagerRegistration dto) {
        return registerUser(dto,
                (username, password, email, birthDate, firstName, lastName) -> userService.createAndStoreManager(username, password, email, birthDate, firstName, lastName, dto.getGymName(), dto.getDateOfEmployment()));
    }

    @PostMapping("/client")
    ResponseEntity<GeneralResponse> registerClient(@Valid @RequestBody ClientRegistration dto) {
        return registerUser(dto, userService::createAndStoreClient);
    }

    private <T extends UserRegistration> ResponseEntity<GeneralResponse> registerUser(T dto, UserCreator userCreator) {
        try {
            userCreator.createAndStoreUser(dto.getUsername(), dto.getPassword(), dto.getEmail(), dto.getBirthDate(),
                    dto.getFirstName(), dto.getLastName());
        } catch (DataIntegrityViolationException ignored) {
            return new ResponseEntity<>(new GeneralResponse("Unable to create user due to conflict"),
                    HttpStatus.CONFLICT);
        }


        return userService.findUserByUsername(dto.getUsername())
                .map(user -> {
                    var confirmationToken = new ConfirmationToken(user);
                    log.debug("Confirmation token {} saved", confirmationToken);
                    tokenRepository.save(confirmationToken);
                    var confirmationLink = REGISTRATION_LINK + confirmationToken.getConfirmationToken();
                    kafkaTemplate.send(TOPIC, new ActivationEmail(user.getEmail(), user.getUsername(), user.getFirstName(),
                            user.getLastName(), confirmationLink));

                    return new ResponseEntity<>(new GeneralResponse("Activation email sent. Please check your email."), HttpStatus.CREATED);
                })
                .orElseGet(() -> new ResponseEntity<>(new GeneralResponse("User registration failed due to an " +
                        "unexpected error."), HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @FunctionalInterface
    private interface UserCreator {
        void createAndStoreUser(String username, String password, String email, LocalDate birthDate, String firstName
                , String lastName);
    }
}
