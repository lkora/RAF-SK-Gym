package raf.sk.gym.notificationservice.consumers.objects;
public record PasswordChangeDto(String email, String username, String resetPasswordLink) {}
