package raf.sk.gym.userservice.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;
@Getter
public abstract class UserRegistration {
    @NotBlank
    private final String username;
    @NotBlank
    private final String password;
    @Email
    private final String email;
    @NotNull
    private final LocalDate birthDate;
    @NotBlank
    private final String firstName;
    @NotBlank
    private final String lastName;

    protected UserRegistration(String username, String password, String email, LocalDate birthDate, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.birthDate = birthDate;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}