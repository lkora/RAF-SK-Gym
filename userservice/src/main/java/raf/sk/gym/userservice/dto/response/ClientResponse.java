package raf.sk.gym.userservice.dto.response;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
@ToString(callSuper = true)
@Getter
public class ClientResponse extends UserResponse {
    private final Long memberCardNumber;
    private final Long numberOfScheduledTrainings;

    public ClientResponse(String username, String email, String firstName, String lastName, LocalDate birthDate,
                          Long memberCardNumber, Long numberOfScheduledTrainings, String userType) {
        super(username, email, firstName, lastName, birthDate, userType);
        this.memberCardNumber = memberCardNumber;
        this.numberOfScheduledTrainings = numberOfScheduledTrainings;
    }
}
