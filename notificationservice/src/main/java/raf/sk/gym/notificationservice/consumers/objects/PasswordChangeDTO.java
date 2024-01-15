package raf.sk.gym.notificationservice.consumers.objects;

public class PasswordChangeDTO {
    private String email;
    private String username;
    private String resetPasswordLink;

    public String getEmail() {
        return email;
    }
    public String getResetPasswordLink() {
        return resetPasswordLink;
    }
    public String getUsername() {
        return username;
    }
}
