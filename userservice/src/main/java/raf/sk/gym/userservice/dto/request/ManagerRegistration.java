package raf.sk.gym.userservice.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ManagerRegistration(
    @NotBlank String username,
    @NotBlank String password,
    @Email String email,
    @NotNull LocalDate birthDate,
    @NotBlank String firstName,
    @NotBlank String lastName,
    @NotBlank String gymName,
    @NotNull LocalDate dateOfEmployment) {
}