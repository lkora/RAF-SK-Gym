package raf.sk.gym.userservice.dto.kafka;

public record ActivationEmail (UserDto receiver, String link) {
    public ActivationEmail(String email, String username, String firstName, String lastName, String link) {
        this(new UserDto(email, username, firstName, lastName), link);
    }
}
