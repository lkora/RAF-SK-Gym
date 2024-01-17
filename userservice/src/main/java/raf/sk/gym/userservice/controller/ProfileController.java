package raf.sk.gym.userservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.sk.gym.userservice.dto.request.EditProfile;
import raf.sk.gym.userservice.dto.response.ClientResponse;
import raf.sk.gym.userservice.dto.response.GeneralResponse;
import raf.sk.gym.userservice.dto.response.ManagerResponse;
import raf.sk.gym.userservice.dto.response.UserResponse;
import raf.sk.gym.userservice.security.JwtTokenProvider;
import raf.sk.gym.userservice.service.UserService;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    private final UserService service;

    private final JwtTokenProvider tokenProvider;

    public ProfileController(UserService service, JwtTokenProvider tokenProvider) {
        this.service = service;
        this.tokenProvider = tokenProvider;
    }


    @PutMapping("/edit")
    public ResponseEntity<GeneralResponse> edit(@RequestBody EditProfile dto, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String username = tokenProvider.getUsernameFromToken(token);
        if (!username.equalsIgnoreCase(dto.username())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new GeneralResponse("Unauthorized request"));
        }
        service.editProfile(dto.username(), dto.password(), dto.dateOfBirth(), dto.firstName(), dto.lastName());
        return ResponseEntity.status(HttpStatus.OK)
                .body(new GeneralResponse("Profile edited successfully."));
    }

    @GetMapping("/")
    public ResponseEntity<?> getProfile(@RequestHeader("Authorization") String token) {
        String username = tokenProvider.getUsernameFromToken(token);
        var usr = service.findUserByUsername(username);
        if (usr.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .build();
        }
        var user = usr.get();
        switch (user.getUserType()) {
            case "admin": {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new UserResponse(user.getUsername(), user.getEmail(), user.getFirstName(),
                                user.getLastName(), user.getBirthDate(), "admin"));
            }
            case "client": {
                var cl = service.findClient(user.getId());
                if (cl.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .build();
                }
                var client = cl.get();
                var response = new ClientResponse(user.getUsername(), user.getEmail(), user.getFirstName(),
                        user.getLastName(), user.getBirthDate(), client.getMemberCardNumber(),
                        client.getNumberOfScheduledTrainings(), "client");
                System.out.println(response);
                return ResponseEntity.status(HttpStatus.OK)
                        .body(response);
            }
            case "manager": {
                var mgr = service.findManager(user.getId());
                if (mgr.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .build();
                }
                var manager = mgr.get();
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ManagerResponse(user.getUsername(), user.getEmail(), user.getFirstName(),
                                user.getLastName(), user.getBirthDate(), manager.getGymName(), manager.getHireDate(),
                                "manager"));
            }
            default: {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new GeneralResponse("Invalid user type"));
            }
        }
    }

}
