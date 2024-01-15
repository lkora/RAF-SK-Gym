package raf.sk.gym.userservice.dto.response;

import lombok.Getter;

import java.time.LocalDate;
@Getter
public class ManagerResponse extends UserResponse {

    private final String gymName;

    private final LocalDate hireDate;

    public ManagerResponse(String username, String email, String firstName, String lastName, LocalDate birthDate,
                           String gymName, LocalDate hireDate) {
        super(username, email, firstName, lastName, birthDate);
        this.gymName = gymName;
        this.hireDate = hireDate;
    }
}
