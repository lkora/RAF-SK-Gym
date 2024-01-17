package raf.sk.gym.userservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import raf.sk.gym.userservice.dto.kafka.UserDto;
import raf.sk.gym.userservice.dto.response.GeneralResponse;
import raf.sk.gym.userservice.model.User;
import raf.sk.gym.userservice.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserService service;

    public AdminController(UserService service) {this.service = service;}


    @GetMapping("/ban/{username}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<GeneralResponse> banUser(@PathVariable String username) {
        service.ban(username);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new GeneralResponse("Banned user: " + username + " successfully"));
    }

    @GetMapping("/unban/{username}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<GeneralResponse> unbanUser(@PathVariable String username) {
        service.unban(username);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new GeneralResponse("Unbanned user: " + username + " successfully"));
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<List<UserDto>> getUsers() {
        List<User> users = service.findAllUsers();
        List<UserDto> userDtos = users.stream()
                .map(user -> new UserDto(user.getEmail(), user.getUsername(), user.getFirstName(), user.getLastName()))
                .toList();
        return ResponseEntity.ok(userDtos);
    }
}
