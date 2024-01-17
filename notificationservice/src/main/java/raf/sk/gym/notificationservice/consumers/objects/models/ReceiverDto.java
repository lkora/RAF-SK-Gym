package raf.sk.gym.notificationservice.consumers.objects.models;

import lombok.Getter;

@Getter
public record ReceiverDto(String email, String username, String firstName, String lastName) {}
