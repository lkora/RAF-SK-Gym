package raf.sk.gym.userservice.dto.response;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
@ToString
@Getter
public class UserResponse {

    public final String email;
    private final String username;
    private final String firstName;
    private final String lastName;
    private final LocalDate birthDate;

    public UserResponse(String username, String email, String firstName, String lastName, LocalDate birthDate) {
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.birthDate = birthDate;
        this.lastName = lastName;
    }
}
