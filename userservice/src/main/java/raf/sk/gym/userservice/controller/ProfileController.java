package raf.sk.gym.userservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import raf.sk.gym.userservice.dto.request.EditProfile;
import raf.sk.gym.userservice.dto.response.GeneralResponse;
import raf.sk.gym.userservice.service.UserService;

@RestController
@RequestMapping("/profile/")
public class ProfileController {

    private final UserService service;

    public ProfileController(UserService service) {this.service = service;}


    @PutMapping("/edit")
    public ResponseEntity<GeneralResponse> edit(@RequestBody EditProfile dto) {
        service.editProfile(dto.username(), dto.password(), dto.dateOfBirth(), dto.firstName(), dto.lastName());
        return ResponseEntity.status(HttpStatus.OK)
                .body(new GeneralResponse("Profile edited successfully."));
    }
}
