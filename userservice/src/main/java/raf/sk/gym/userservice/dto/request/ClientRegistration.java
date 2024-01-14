package raf.sk.gym.userservice.dto.request;

import java.time.LocalDate;

public final class ClientRegistration extends UserRegistration {

    public ClientRegistration(String username, String password, String email, LocalDate birthDate, String firstName, String lastName) {
        super(username, password, email, birthDate, firstName, lastName);
    }
}
