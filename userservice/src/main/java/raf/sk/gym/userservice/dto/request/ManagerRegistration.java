package raf.sk.gym.userservice.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public final class ManagerRegistration extends UserRegistration {


    @NotBlank
    private final String gymName;

    @NotNull
    private final LocalDate dateOfEmployment;

    public ManagerRegistration(@NotBlank String username, @NotBlank String password,
                               @Email String email, @NotNull LocalDate birthDate,
                               @NotBlank String firstName, @NotBlank String lastName,
                               @NotBlank String gymName, @NotNull LocalDate dateOfEmployment) {

        super(username, password, email, birthDate, firstName, lastName);
        this.gymName = gymName;
        this.dateOfEmployment = dateOfEmployment;
    }

    // getters and other methods...
}